package com.codepath.apps.mytodo;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class EditItemListActivity extends Activity {

	private int itemPos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item_list);
		//Get the text to be edited
		String editText = getIntent().getStringExtra("editText");
		itemPos = getIntent().getIntExtra("itemPos", 0);
		//Get the edit text view
		EditText etEditView = (EditText) findViewById(R.id.editOldItem);
		etEditView.setText(editText);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item_list, menu);
		return true;
	}
	
	/**
	 * This method will be called on onClick event of the button, Save.
	 * This new value in the text box will be sent back to be saved in the list.
	 * @param v
	 */
	public void onSubmit(View v){
		EditText etText = (EditText) findViewById (R.id.editOldItem);
		String text = etText.getText().toString();
		//create an Intent
		Intent data = new Intent();
		data.putExtra("editText", text);
		data.putExtra("itemPos", itemPos);
		
		//set the result code and intent
		setResult(RESULT_OK,data);
		
		//Hide the Keyboard
		InputMethodManager imm = (InputMethodManager)getSystemService(
			      getApplicationContext().INPUT_METHOD_SERVICE);
	    imm.hideSoftInputFromWindow(etText.getWindowToken(), 0);
		//Finish the activity to return to calling activity
		finish();
		
	}
	
	

}
