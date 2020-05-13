package com.johnfreier.mypassword.config;

public class PasswordConfig {
    
    public static final String DEFAULT_PASSWORD_FILENAME = "mypasswords";
    
    public static final String CONFIG_FILE_NAME = "config.properties";
    
    public static final String CONFIG_KEY_PASSWORD_FILE = "file";

    /**
     * Master password for the encrypted file.
     */
    public String password = new String();
    
    /**
     * Path to the encrypted file.
     */
    public String passwordFilePath = new String();
    
}
