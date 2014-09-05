package com.proj.apps.simpletodolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {

	private EditText EditItemName, EditItemPriority, EditItemDueDate;
	private int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		
        // Get the handles to the Edit Item box
        EditItemName = (EditText)findViewById(R.id.etEditItemName);
        EditItemPriority = (EditText)findViewById(R.id.etEditItemPriority);
        EditItemDueDate = (EditText)findViewById(R.id.etEditItemDueDate);
        
        // Extract the position and to do item fields sent from Main activity for the to do item
        position = getIntent().getIntExtra("position", 0);
        
        String edit_item_name = getIntent().getStringExtra("edit_item_name");
        int edit_item_priority = getIntent().getIntExtra("edit_item_priority", 0);
        String edit_item_due_date = getIntent().getStringExtra("edit_item_due_date");
        
        // Populate the extracted fields in the Edit Form 
    
    	EditItemName.setText(edit_item_name);
    	EditItemPriority.setText(Integer.toString(edit_item_priority));
    	EditItemDueDate.setText(edit_item_due_date);
    	
        // Set cursor to the end of the text in each field
    	EditItemName.setSelection(EditItemName.length());
    	EditItemPriority.setSelection(EditItemPriority.length());
    	EditItemDueDate.setSelection(EditItemDueDate.length());
             
	}

	// Method executed on clicking the Save button for the edit item
    public void onSaveItem(View v) {
    	
    	// Obtain the modified new item 
    	String edited_item_name = EditItemName.getText().toString();
    	String edited_item_priority_string = EditItemPriority.getText().toString();
    	String edited_item_due_date = EditItemDueDate.getText().toString();
    	
    	int edited_item_priority;
    	
    	// Pass the modified new item and the original position of the to-do item to the main activity screen
    	Intent data = new Intent();
    	
    	data.putExtra("position", position);
    	
    	data.putExtra("edited_item_name", edited_item_name);
    	
    	if (edited_item_priority_string.isEmpty())
    		edited_item_priority = 1;
    	else
    		edited_item_priority = Integer.parseInt(edited_item_priority_string);
    	
    	data.putExtra("edited_item_priority", edited_item_priority);
    	data.putExtra("edited_item_due_date", edited_item_due_date);
    	
    	// set result code and bundle data for response
    	setResult(RESULT_OK, data); 
    	
    	// Return back to the main activity screen
    	finish();
    }
}
