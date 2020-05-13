package com.johnfreier.mypassword.service;

import java.io.File;
import java.util.List;
import java.util.Random;

import com.johnfreier.mypassword.builder.Builder;
import com.johnfreier.mypassword.config.PasswordConfig;
import com.johnfreier.mypassword.domain.Item;
import com.johnfreier.mypassword.util.FileUtils;
import com.johnfreier.mypassword.util.Unlock;

public class PasswordService {

    /**
     * Save a list of passwords in the correct fashion.
     * 
     * @param items
     * @param passwordConfig
     * @throws Exception
     */
    public void savePasswordList(List<Item> items, PasswordConfig passwordConfig) throws Exception {

        // Construct the file.
        String fileContent = Builder.buildContent(items, passwordConfig.password);

        // Encrypt the file.
        Unlock unlock = new Unlock(passwordConfig.password);
        fileContent = unlock.encrypt(fileContent);

        // Save the file.
        File file = new File(passwordConfig.passwordFilePath);
        FileUtils.writeToFile(file, fileContent);

    }

    public boolean verifyPassword(PasswordConfig passwordConfig) {

        try {

            File file = new File(passwordConfig.passwordFilePath);

            // Read File
            String contentsEncrypted = FileUtils.readFromFile(file);

            // DeCrypt data
            Unlock unlock = new Unlock(passwordConfig.password);
            String contentsDecrypted = unlock.decrypt(contentsEncrypted);
            
            int passwordMarkStart = contentsDecrypted.indexOf("{") + 1;
            int passwordMarkEnd = contentsDecrypted.indexOf("}");
            
            String password = contentsDecrypted.substring(passwordMarkStart, passwordMarkEnd);
            
            if (password.equals(passwordConfig.password)) {
                
                return true;
            }
            

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }

    /**
     * Generate a String to be used for a password.
     * 
     * @return
     */
    public String generatePassword() {

        String password = "";
        Random randomGenerator = new Random();
        for (int x = 0; x < 10; x++) {
            int randomInt = randomGenerator.nextInt(74) + 48;
            if (((randomInt > 96) || (randomInt < 91)) && ((randomInt > 64) || (randomInt < 58))) {
                password += Character.toString((char) randomInt);
            }
        }

        return password;

    }

}
