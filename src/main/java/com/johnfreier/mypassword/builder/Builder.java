package com.johnfreier.mypassword.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.DatatypeConverter;

import com.johnfreier.mypassword.domain.Item;

public class Builder {

	public static List<Item> buildItems(String text) throws Exception {
		
		Set<Item> items = new TreeSet<Item>();
		
		
		String[] strItems = text.split("[}]");
		
		// start with 1 to skip password bracket
		for(int x = 1; x < strItems.length; x++) {
			
			String strItem = strItems[x];
			strItem = strItem.substring(1, strItem.length());
			
			Item item = new Item();
			
			String[] strSubItems = strItem.split("[\"][,][\"]");
			
			String title = "";
			String username = "";
			String password = "";			
			String note = "";
			String url = "";
			
			try {
				title = strSubItems[0];
				username = strSubItems[1];
				password = strSubItems[2];				
				note = strSubItems[3];
				
				// new added items 
				url = strSubItems[4];
				url= url.substring(0, (url.length()-1)); // end "
				
			}catch(Exception e) {
				
				// old version end "
				note= note.substring(0, (note.length()-1));	
			}
			
			// start "
			title = title.substring(1, (title.length()));
			
			
			item.setTitle(toAscii(title));
			item.setUsername(toAscii(username));
			item.setPassword(toAscii(password));			
			item.setNote(toAscii(note));
			item.setUrl(toAscii(url));
			
			try {
			
			items.add(item);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}	
		
		
		List<Item> itemsList = new ArrayList<Item>(items);
		return itemsList;
		
	}
	
	public static String buildPassword(String text) {
		
		String result = "";
		
		try {
			
			String[] strItems = text.split("[}]");
			String strPassword = strItems[0];
			result = strPassword.substring(1, strPassword.length());
			
		} catch (Exception e) {
			result = "WrongPassword:" + e.getMessage();
		}
		
		return result;
	}
	
	public static String buildContent(List<Item> items, String password) {
		
		String result = "";
		
		result = "{" + password + "}";
		
		for(Item item : items) {
			result = result + "{" 
							+ "\"" + toHex(item.getTitle()) + "\","
							+ "\"" + toHex(item.getUsername()) + "\","
							+ "\"" + toHex(item.getPassword()) + "\","							
							+ "\"" + toHex(item.getNote()) + "\","
							+ "\"" + toHex(item.getUrl()) + "\""
							+ "}";
		}
		
		return result;
	}
	
	public static String buildNewPassword(String text, String password) {
		String result = "";
		int finish = text.indexOf("}");
		String end = text.substring(finish, text.length());
		result = "{" + password + end;		
		
		return result;
	}
	
	/**
	 * Convert hex to ascii.
	 * 
	 * @param hex
	 * @return
	 */
	public static String toAscii(String hex) {
	    
	    byte[] s = DatatypeConverter.parseHexBinary(hex);
	    
	    return new String(s);
	    
	}
	
	/**
	 * Convert ascii to hex.
	 * 
	 * @param ascii
	 * @return
	 */
	public static String toHex(String ascii) {
	    
	    byte[] bytes = ascii.getBytes();
	    
	    return DatatypeConverter.printHexBinary(bytes);
	    
	}
		
}
