//package com.johnfreier.mypassword;
//
//import java.awt.Toolkit;
//import java.awt.datatransfer.Clipboard;
//import java.awt.datatransfer.StringSelection;
//import java.io.File;
//import java.util.List;
//
//import com.johnfreier.mypassword.builder.Builder;
//import com.johnfreier.mypassword.domain.Item;
//import com.johnfreier.mypassword.util.FileUtils;
//import com.johnfreier.mypassword.util.Unlock;
//
//import javafx.application.Application;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.ListView;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.GridPane;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//
//public class MainApplication extends Application {
//    
//    private ListView<Item> fxListPassword = new ListView<>();
//    
//    private Text fxTextTitle = new Text("");
//    private Text fxTextUsername = new Text("");
//    private Text fxTextNote = new Text("");
//    private Text fxTextURL = new Text("");
//    
//    /**
//     * Launch the application.
//     */
//    public static void main(String[] args) {
//        launch(args);
//    }
//    
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        primaryStage.setTitle("My Password");
//        
//        BorderPane border = new BorderPane();
//        border.setRight(createPasswordDetails());
//        border.setBottom(createCopyButton());
//        border.setCenter(createPasswordList());
//        
//        Scene scene = new Scene(border, 500, 300);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//    
//    /**
//     * Create the list of passwords.
//     * 
//     * @return
//     * @throws Exception
//     */
//    private ListView<Item> createPasswordList() throws Exception {
//
//        // Get the list of password items.
//        List<Item> createPasswordListModel = createPasswordListModel();
//
//        // Convert the list to an Observable List.
//        ObservableList<Item> items = FXCollections.observableArrayList(createPasswordListModel);
//        
//        // Set the passwords to the list.
//        fxListPassword.setItems(items);
//        fxListPassword.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Item>() {
//
//            @Override
//            public void changed(ObservableValue<? extends Item> observable, Item oldItem, Item newItem) {
//                fxTextTitle.setText(newItem.getTitle());
//                fxTextUsername.setText(newItem.getUsername());
//                fxTextNote.setText(newItem.getNote());
//                fxTextURL.setText(newItem.getUrl());
//            }
//        });
//        
//        return fxListPassword;
//    }
//
//    /**
//     * Create the Copy Password Button.
//     * 
//     * @return
//     */
//    private Button createCopyButton() {
//        
//        Button fxButtonCopyPassword = new Button("Copy Password");
//        
//        // Set the width to the size of the window.
//        fxButtonCopyPassword.setPrefWidth(Double.MAX_VALUE);
//        
//        // Copy password button has been clicked.
//        fxButtonCopyPassword.setOnAction(new EventHandler<ActionEvent>() {
//            
//            @Override
//            public void handle(ActionEvent event) {
//                Item item = fxListPassword.getSelectionModel().getSelectedItem();
//                copyToClipboard(Builder.toAscii(item.getPassword()));
//            }
//        });
//        
//        return fxButtonCopyPassword;
//    }
//    
//    /**
//     * Create the password detail display.
//     * 
//     * @return
//     */
//    private GridPane createPasswordDetails() {
//        
//        GridPane grid = new GridPane();
//        //grid.setStyle("-fx-background-color: #5a5a5a");
//        
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setPadding(new Insets(0, 10, 0, 10));
//        
//        // Title
//        grid.add(new Text("Title:"), 0, 0);
//        grid.add(fxTextTitle, 1, 0);
//        
//        // User Name
//        grid.add(new Text("User Name:"), 0, 1);
//        grid.add(fxTextUsername, 1, 1);
//        
//        // Note
//        grid.add(new Text("Note:"), 0, 2);
//        grid.add(fxTextNote, 1, 2);
//        
//        // URL
//        grid.add(new Text("URL:"), 0, 3);
//        grid.add(fxTextURL, 1, 3);
//        
//        return grid;
//        
//    }
//
//    
//    /**
//     *
//     * @return
//     * @throws Exception
//     */
//    private List<Item> createPasswordListModel() throws Exception {
//
//        // Read File - Add location to file.
//        File file = new File("");
//        String contentsEncrypted = FileUtils.readFromFile(file);
//       
//        // DeCrypt data - Add password
//        Unlock unlock = new Unlock("");
//        String contentsDecrypted = unlock.decrypt(contentsEncrypted);
//        
//        // Build Items
//        List<Item> buildItems = Builder.buildItems(contentsDecrypted); 
//        
//        return buildItems;
//    }
//    
//    /**
//     * Copy a {@link String} to the Clip Board.
//     * 
//     * @param text
//     */
//    private void copyToClipboard(String text) {
//        
//        StringSelection stringSelection = new StringSelection(text);
//        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//        clipboard.setContents(stringSelection, null);
//        
//    }
//
//}
