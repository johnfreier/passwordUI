package com.johnfreier.mypassword;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

import com.johnfreier.mypassword.domain.Item;

public class CopyPasswordActionListener implements ActionListener {
    
    private JList<Item> passwords;
    
    public CopyPasswordActionListener(JList<Item> passwords) {
        this.passwords = passwords;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        Item selectedItem = (Item) passwords.getSelectedValue();

        copyToClipboard(selectedItem.getPassword());
        
    }
    
    /**
     * Copy a string to the system clipboard.
     * 
     * @param str
     */
    private void copyToClipboard(String str) {
        
        StringSelection stringSelection = new StringSelection(str);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        
    }

}
