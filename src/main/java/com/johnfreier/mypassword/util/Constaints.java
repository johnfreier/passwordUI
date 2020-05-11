package com.johnfreier.mypassword.util;

public class Constaints {

	public static String FILENAME = "/mnt/sdcard/mypasswords/passwords"; 
	public static String MYPASSWORDSACTIVITIY_BUNDELKEYWORD = "content_key";
	
	public final static String CHANGEPASSWORD_KEY = "CHANGE_PASSWORD";
	public final static String CHANGEPASSWORD_VALUE_CREATE = "CREATE";
	public final static String CHANGEPASSWORD_VALUE_CHANGE = "CHANGE";
	
	public final static String CHANGEPASSWORD_RETURN_KEY = "RETURN_KEY";
	public final static String CHANGEPASSWORD_CURRENT_KEY = "CURRENT_KEY";
	
	public final static String INTENT_KEY = "key";
	
	public final static String PREFRENCE_NAME = "MyPasswordPrefs";
	
	/* These are used to insure the activities are being open within the app and not outside */
	public final static String REFRENCE_MY_PASSWORD_ACTIVITY = "MYPASSWORDACTIVITY";
	public final static String REFRENCE_ITEM_ACTIVITY = "ITEMACTIVITY";
	public final static Long REFRENCE_COMING = 1L;
	public final static Long REFRENCE_DONE = 0L;
	
	
}
