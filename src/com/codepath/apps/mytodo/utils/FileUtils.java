package com.codepath.apps.mytodo.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FileUtils {

	
	private static final boolean MODE_APPEND = true;
	private static final String fileName = "todo.txt";

	public static ArrayList<String> readLines(File fileName){
	  
		
		ArrayList<String> lvItems = new ArrayList<String>();
	
		if(fileName.exists()){
			FileInputStream fis = null;
			
			try{
				 fis = new FileInputStream(fileName);
				 // open the file for reading we have to surround it with a try
			       
		            InputStream instream = new FileInputStream(fileName);//open the text file for reading
		           
		            // if file the available for reading
		            if (instream != null) {               
		               
		            	// prepare the file for reading
		            	InputStreamReader inputreader = new InputStreamReader(instream);
		            	BufferedReader buffreader = new BufferedReader(inputreader);
		              
		            	String line=null;
		            	//We initialize a string "line"
		             
			            while (( line = buffreader.readLine()) != null) {
			                //buffered reader reads only one line at a time, hence we give a while loop to read all till the text is null
			                                       
			                            lvItems.add(line);   
			            }
		          }
		                                     
				} catch(IOException ioex){
					System.out.println("File Does not Exist :: " + ioex.getMessage());
				  }
			
			
			}
		return lvItems;
	  }
	
	public static void writeLines(File todo, String text) {
		// TODO Auto-generated method stub
		
		//File todoFile = new File(fileName);
		if(text != null){
			try{
				System.out.println("If file exist :: " + todo.exists());
			    if(!todo.exists()){
			    	todo.createNewFile();
			    	System.out.println("After creating, file exist :: " + todo.exists());
			    	todo.setWritable(true);
			    	
			    }
					
				// open myfilename.txt for writing
		          OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(todo,MODE_APPEND));
		          
		          // write the contents to the file
		          //System.out.println("Size of the Array :: " + items.size());
		          
		          //for(String str: items){
		           out.write(text);
		           out.write('\n');
		           System.out.println("Added :: " + text + " to the file.");
		          //}
		          // close the file
		          out.close();
			}
			catch(FileNotFoundException fex){
				System.out.println("File not found ::" + fex.getMessage());
			}
			
			catch(IOException ioe){
				System.out.println("Exception in File Utils :: " + ioe.getMessage());
			}
		}
	}
	
}
