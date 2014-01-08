package com.codepath.apps.mytodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.codepath.apps.mytodo.utils.FileUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TodoActivity extends Activity {

	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	TextView txView;
	private final int REQUEST_CODE = 200;
	private final int REQUEST_CODE_INDEX = 201;
	private String selectedFileName;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        selectList();
        lvItems = (ListView) findViewById(R.id.lvItems);
        txView = (TextView) findViewById(R.id.txlsName);
        txView.setText(selectedFileName);
        items = new ArrayList<String>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    /**
     * This method will call the index view for available lists
     */

    private void selectList() {
    	//Create an intent
		//Intent intent = new Intent(TodoActivity.this, IndexActivity.class);
    	 selectedFileName = getIntent().getStringExtra("listName");
    	
		//Launch other activity, here Edit activity.
		//startActivityForResult(intent, REQUEST_CODE_INDEX);
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
        return true;
    }
    
    /**
     * This method will add the new entry to arraylist.
     * @param v
     */
    public void addToDoItem(View v){
    	EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    	String newStr = etNewItem.getText().toString().trim();
    	itemsAdapter.add(newStr);
    	saveItems(newStr);
    	etNewItem.setText("");
    }
   

    /**
     * This method is listener to the long click event on the list item.
     * It will remove the item from the list.
     */
	private void setupListViewListener(){
    	//Long click listener for the list item
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
    		@Override
    		public boolean onItemLongClick(AdapterView<?> aView, View item, int pos, long id){
    			items.remove(pos);
    			itemsAdapter.notifyDataSetInvalidated();
    			//Update the file after removing the item.
    			updateFinalList();
    			Toast toast = Toast.makeText(getApplicationContext(), "Item Deleted Successfully", Toast.LENGTH_SHORT);
    		    toast.show();
    			return true;
    		}	
    	}
      );
    	
    //Onclick listener for the list item to make it editable
	lvItems.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View item, int pos,
				long id) {
			//Create an intent
			Intent intent = new Intent(TodoActivity.this, EditItemListActivity.class);
			
			//open Edit activity and pass on the the data of the item			
			intent.putExtra("editText", items.get(pos));
			intent.putExtra("itemPos", pos);
			//Launch other activity, here Edit activity.
			startActivityForResult(intent, REQUEST_CODE);
		}
		
	}
	);
    	
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // REQUEST_CODE is defined above
	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
	     // Extract name value from result extras
	     String etText = data.getExtras().getString("editText");
	     int itemPos = data.getIntExtra("itemPos", 0);
	     items.remove(itemPos);
	     items.add(itemPos, etText);
	     itemsAdapter.notifyDataSetInvalidated();
	     updateFinalList();
	     Toast toast = Toast.makeText(getApplicationContext(), "List Updated Successfully", Toast.LENGTH_SHORT);
		 toast.show();
	     
	  } else if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_INDEX){
		  //get the list file name
		  selectedFileName = data.getExtras().getString("listName").trim();
		  readItems();
	  }
	} 
    
	/**
	 * This method reads the last saved data from the disk, to be displayed to user.
	 */
    
    private void readItems(){
    	File filesDir = getFilesDir();
    	
    	File todoFile = new File(filesDir, selectedFileName+".txt");
    	System.out.println("Reading file :: " + todoFile.getName());
    	try{
    		
    		ArrayList<String> itemList = FileUtils.readLines(todoFile);
    		
    		if (itemList != null && itemList.size() > 0){
    			items.addAll(itemList);
    		}
    		
    		
    	}catch(IOException ex){
    		
    	}
    	
    }
    
    /**
     * This method saves the new entry added to the list on the disk.
     * @param newText
     */
    
    private void saveItems(String newText){
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir,selectedFileName+".txt");
    	try{
    		FileUtils.writeLines(todoFile, newText);
    	} catch(Exception ex){
    		System.out.println(ex.getMessage());
    	}
    }
    
    /**
     * Rewrite list in persistent store
     */
     private void updateFinalList(){
    	//Delete the file
     	deleteList();
     	for (String lsText: items) {
 			saveItems(lsText);
 		}
     	
     }
    /**
     * This method will delete the list entries from disk
     */
    private void deleteList() {
		// call delete file function
    	File filesDir = getFilesDir();
    	File todoFile = new File (filesDir,selectedFileName.trim()+".txt");
    	try{
    		
    		 FileUtils.deleteLines(todoFile);
    		
    	}catch(Exception ex){
    		
    	}
		
	}
    
}
