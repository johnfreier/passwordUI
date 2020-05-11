package com.johnfreier.mypassword.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class PasswordController {

    private String password;
    
    @FXML
    private PasswordField pssPassword;

    @FXML
    public void handleOkClick(ActionEvent event) {
        
        // Set the password.
        this.password = pssPassword.getText();
        
        // Close window.
        handleCancelClick(event);
    }
    
    @FXML
    public void handleCancelClick(ActionEvent event) {
        // Close the dialog window.
        Node  source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPassword() {
        return password;
    }
    
}
