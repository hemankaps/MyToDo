package com.codepath.apps.mytodo;

import java.io.File;
import java.util.ArrayList;

import com.codepath.apps.mytodo.utils.FileUtils;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {

	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	private static final String fileName = "todo.txt";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        //readItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
        return true;
    }
    
    public void addToDoItem(View v){
    	EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    	String newStr = etNewItem.getText().toString().trim();
    	itemsAdapter.add(newStr);
    	saveItems(newStr);
    	etNewItem.setText("");
    }
    
    private void setupListViewListener(){
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
    		@Override
    		public boolean onItemLongClick(AdapterView<?> aView, View item, int pos, long id){
    			items.remove(pos);
    			itemsAdapter.notifyDataSetInvalidated();
    			return true;
    		}	
    	}
      );
    	
    }
    
    
    private void readItems(){
    	File filesDir = getFilesDir();
    	System.out.println("FilesDir :: " + filesDir);
        //Check the file in desired location
    	
    	
    	//Create new file only if it does not exist
    	File todoFile = new File(filesDir, fileName);
    	try{
    		
    		ArrayList<String> itemList = FileUtils.readLines(todoFile);
    		
    		if (itemList != null && itemList.size() > 0){
    			items.addAll(itemList);
    		}
    		
    		
    	}catch(Exception ex){
    		
    	}
    	
    }
    
    
    private void saveItems(String newText){
    	File filesDir = getFilesDir();
    	System.out.println("FilesDir :: " + filesDir);
    	File todoFile = new File(filesDir,fileName);
    	System.out.println("File to be created :: " + todoFile.toString());
    	try{
    		FileUtils.writeLines(todoFile, newText);
    	}catch(Exception ex){
    		
    	}
    }
}
