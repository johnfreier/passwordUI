package com.johnfreier.mypassword.controller;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.johnfreier.mypassword.builder.Builder;
import com.johnfreier.mypassword.config.PasswordConfig;
import com.johnfreier.mypassword.domain.Item;
import com.johnfreier.mypassword.service.PasswordService;
import com.johnfreier.mypassword.util.FileUtils;
import com.johnfreier.mypassword.util.Unlock;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private ListView<Item> lstItems;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextArea txtNote;

    @FXML
    private TextField txtURL;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCopy;

    @FXML
    private Button btnNew;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnGenerate;
    
    private Item selectedItem = new Item();

    private ObservableList<Item> observableItems;

    private PasswordConfig passwordConfig = new PasswordConfig();

    @FXML
    public void initialize() {


    }

    @FXML
    public void handleBtnCopyPasswordAction(ActionEvent event) {
        copyToClipboard(selectedItem.getPassword());
    }

    @FXML
    public void handleTxtURLClick() {

        if (selectedItem.getUrl().isEmpty()) {
            return;
        }

        try {
            Desktop.getDesktop().browse(new URI(selectedItem.getUrl()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleMnuFileOpen(ActionEvent event) throws IOException {

        // Open file chooser.
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
        if (file == null)
            return;

        passwordConfig.passwordFilePath = file.getAbsolutePath();

        // Open password enter dialog.
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/password.fxml"));
        Parent parent = fxmlLoader.load();
        PasswordController passwordController = fxmlLoader.getController();

        Scene scene = new Scene(parent, 300, 140);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();

        // Get the password that was entered.
        passwordConfig.password = passwordController.getPassword();
        passwordController.setPassword(""); // Clear out the password just incase of caching.
        if (passwordConfig.password == null || passwordConfig.password.isEmpty())
            return;

        // Draw the list of password items.
        redrawPasswordList(file, passwordConfig.password);

    }

    @FXML
    public void handleMnuExit(ActionEvent event) {
        Stage stage = (Stage) lstItems.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleMnuNew(ActionEvent event) {

        selectedItem = null;

        disableAllButtons(true);
        btnSave.setDisable(false);
        btnGenerate.setDisable(false);

        enableAllTextBoxes(true);

        clearAllTextBoxes();

        lstItems.getSelectionModel().clearSelection();
        
        txtTitle.requestFocus();

    }

    @FXML
    public void handleMnuSave(ActionEvent event) {
        
        Item item = selectedItem;
        
        boolean isNew = (item == null);
        
        if (isNew) {
            item = new Item();
        }

        item.setTitle(txtTitle.getText());
        item.setUsername(txtUsername.getText());
        item.setPassword(txtPassword.getText());
        item.setNote(txtNote.getText());
        item.setUrl(txtURL.getText());

        if (isNew) {
            observableItems.add(item);
        }
        
        FXCollections.sort(observableItems);

        try {

            PasswordService.savePasswordList(observableItems, passwordConfig);

        } catch (Exception e) {

            e.printStackTrace();
            error("There was a problem saving the password.");
            observableItems.remove(item);

        }

        btnNew.setDisable(false);
        btnSave.setDisable(true);

    }

    @FXML
    public void handleMenuDelete(ActionEvent event) {

        if (selectedItem == null) {
            return;
        }

        String title = selectedItem.getTitle();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Password");
        alert.setHeaderText("You are about to delete \"" + title + "\"");
        alert.setContentText("Are you sure you want to do this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            try {

                observableItems.remove(selectedItem);
                PasswordService.savePasswordList(observableItems, passwordConfig);
                selectedItem = null;

                lstItems.getSelectionModel().clearSelection();

                disableAllButtons(true);
                btnNew.setDisable(false);
                clearAllTextBoxes();

            } catch (Exception e) {

                observableItems.add(selectedItem);
                FXCollections.sort(observableItems);

                e.printStackTrace();

            }

        }

    }

    @FXML
    public void handleMenuEdit(ActionEvent event) {
        
        disableAllButtons(true);
        btnSave.setDisable(false);
        btnGenerate.setDisable(false);

        enableAllTextBoxes(true);

        txtTitle.requestFocus();

    }
    
    @FXML
    public void handleMenuGenerate(ActionEvent event) {
        
        PasswordService.generatePassword();
        
    }

    /**
     * Redraw the list of passwords.
     * 
     * @param file
     */
    private void redrawPasswordList(File file, String password) {

        // Get the list of passwords.
        try {

            List<Item> items = createPasswordListModel(file, password);

            // Convert the list to an Observable List.
            observableItems = FXCollections.observableArrayList(items);

            // Set the passwords to the list.
            lstItems.setItems(observableItems);

            lstItems.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Item>() {

                @Override
                public void changed(ObservableValue<? extends Item> observable, Item oldValue, Item newValue) {

                    if (newValue == null) {
                        selectedItem = null;
                    } else {

                        disableAllButtons(true);
                        enableAllTextBoxes(false);
                        
                        btnCopy.setDisable(false);
                        btnEdit.setDisable(false);
                        btnDelete.setDisable(false);
                        btnNew.setDisable(false);

                        selectedItem = newValue;
                        redrawItemDetail();
                    }
                }

            });

            btnNew.setDisable(false);

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Password Issue");
            alert.setContentText("Warning: There is a problem with your password.");
            alert.showAndWait();
        }
    }

    /**
     * Update the Item detail panel with the details from the selected item.
     */
    private void redrawItemDetail() {
        if (selectedItem != null) {
            txtTitle.setText(selectedItem.getTitle());
            txtUsername.setText(selectedItem.getUsername());
            txtPassword.setText(selectedItem.getPassword());
            txtNote.setText(selectedItem.getNote());
            txtURL.setText(selectedItem.getUrl());
        }
    }

    /**
     *
     * @return
     * @throws Exception
     */
    private List<Item> createPasswordListModel(File file, String password) throws Exception {

        // Read File
        String contentsEncrypted = FileUtils.readFromFile(file);

        // DeCrypt data
        Unlock unlock = new Unlock(password);
        String contentsDecrypted = unlock.decrypt(contentsEncrypted);

        // Build Items
        List<Item> buildItems = Builder.buildItems(contentsDecrypted);

        return buildItems;
    }

    /**
     * Copy a {@link String} to the Clip Board.
     * 
     * @param text
     */
    private void copyToClipboard(String text) {

        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

    }

    private void enableAllTextBoxes(boolean editable) {
        txtTitle.setEditable(editable);
        txtUsername.setEditable(editable);
        txtPassword.setEditable(editable);
        txtNote.setEditable(editable);
        txtURL.setEditable(editable);
    }

    private void error(String text) {

        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("There was an error.");
        alert.setContentText(text);

    }

    private void disableAllButtons(boolean disable) {
        btnSave.setDisable(disable);
        btnCopy.setDisable(disable);
        btnEdit.setDisable(disable);
        btnNew.setDisable(disable);
        btnDelete.setDisable(disable);
        btnGenerate.setDisable(disable);
    }

    private void clearAllTextBoxes() {
        txtTitle.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        txtNote.setText("");
        txtURL.setText("");
    }

}
