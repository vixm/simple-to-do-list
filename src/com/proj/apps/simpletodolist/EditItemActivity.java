package com.proj.apps.simpletodolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {

	private EditText EditItem;
	private int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		
        // Get the handles to the Edit Item box
        EditItem = (EditText)findViewById(R.id.etEditToDoItem);
		
		// Extract the position and text field sent from Main activity for the to do item
        position = getIntent().getIntExtra("position", 0);
        String EditItemString = getIntent().getStringExtra("edit_item_text");
        
        // Populate the extracted text field value in the Edit Form 
    	// Obtain the entered new item and update the To Do item list
    	EditItem.setText(EditItemString);
    	
        // Set cursor to the end of the text
    	EditItem.setSelection(EditItemString.length());
             
	}

	// Method executed on clicking the Save button for the edite item
    public void onSaveItem(View v) {
    	
    	// Obtain the modified new item 
    	String EditItemString = EditItem.getText().toString();
    	
    	// Pass the modified new item and the original position of the to-do item to the main activity screen
    	Intent data = new Intent();
    	
    	data.putExtra("position", position);
    	data.putExtra("edited_item", EditItemString);
    	
    	// set result code and bundle data for response
    	setResult(RESULT_OK, data); 
    	
    	// Return back to the main activity screen
    	finish();
    }
}
