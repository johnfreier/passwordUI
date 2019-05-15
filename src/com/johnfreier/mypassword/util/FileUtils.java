package com.johnfreier.mypassword.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class FileUtils {

	public static void writeToFile(File filename, String text) throws FileNotFoundException, Exception {
		
		//File dirFile = new File(filename.getPath());
    	//dirFile.mkdirs();
    	
    	FileOutputStream outStream = new FileOutputStream(filename, false);
    	
    	PrintStream printStream = new PrintStream(outStream);
    	printStream.println (text);
    	printStream.close();

    	outStream.flush();
        outStream.close();

	}
	
	public static String readFromFile(File filename) throws Exception {
		
		StringBuilder results = new StringBuilder();
		FileInputStream fStream = new FileInputStream(filename);
        BufferedReader in = new BufferedReader(new InputStreamReader(fStream));
        while (in.ready()) {
            results.append(in.readLine());
        }
        in.close();
        
        return results.toString();
	}
	
}
