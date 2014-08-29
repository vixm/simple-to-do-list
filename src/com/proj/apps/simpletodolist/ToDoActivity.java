package com.proj.apps.simpletodolist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.Toast;

public class ToDoActivity extends Activity {
	private ListView lvItems;
	private EditText addedItem;
	
	private ArrayList<String> toDoItems;
	private ArrayAdapter<String> itemsAdapter;
	
	private final int REQUEST_CODE = 50;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        
        // Get the handles to the To Do item list and the new item text box
        lvItems = (ListView)findViewById(R.id.lvListItems);
        addedItem = (EditText)findViewById(R.id.etNewItem);
        
        // Populate To Do list in local array with entries from file
        readItems();
        
        // Create adapter between the To Do item list and the array list 
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, toDoItems);
        lvItems.setAdapter(itemsAdapter);
        
        // Set up listener for Long click(remove) and single click(edit item) in To Do item list   
        setupListViewListener();
               
    }

    
    private void setupListViewListener() {
    	
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
		
			// Method executed on a long click on a to-do item entry -> results in removal of the item from the list
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				// Remove item entry on long click and notify Adapter			
				toDoItems.remove(position);
				itemsAdapter.notifyDataSetChanged();
				
				// Update the todo items list in the file
				writeItems();
				
				return true;
			}
			
		});
		
		lvItems.setOnItemClickListener(new OnItemClickListener() {
		
			// Method executed on a single click on a to-do item entry -> results in the edit item screen being shown
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				// Bring up the edit form activity using an "Intent"  To properly load the edit form data, pass along the 
				// text and position of that item to the edit activity using "extras" 
				
				Intent edit_item_intent = new Intent(ToDoActivity.this, EditItemActivity.class);
				
				// put "extras", namely position and the text content
				// into the bundle for access in the Edit Item activity
				edit_item_intent.putExtra("position", position);
				edit_item_intent.putExtra("edit_item_text", toDoItems.get(position)); 
				
				// Bring up the Edit Item activity
				startActivityForResult(edit_item_intent, REQUEST_CODE); 
			}
			
		});
			
	}

    // Method to handle the result of the Edit item sub-activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      
      if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
    	  
         // Extract position and edited to do item from result extras
         int position = data.getExtras().getInt("position");
         String edited_item = data.getExtras().getString("edited_item");
         
         // Toast the edited item name to display temporarily on screen
         Toast.makeText(this, edited_item, Toast.LENGTH_SHORT).show();
         
         // Update the todo items list with the edited item at the correct position
         toDoItems.set(position, edited_item);
         itemsAdapter.notifyDataSetChanged();
         
         // Update the todo items list in the file
         writeItems();
      }
    } 
    
    
	// Method executed on clicking the Add button
    public void onAddItem(View v) {
    	
    	// Obtain the entered new item and update the To Do item list
    	String addedItemString = addedItem.getText().toString();
    	itemsAdapter.add(addedItemString);
    	
    	writeItems();
    	
    	// Clear the new item entry box
    	addedItem.setText("");
    }
    
    // Method to read all to-do item entries from file into local array
    private void readItems() {
    	
    	File filesDir = getFilesDir();
    	File toDoFile = new File(filesDir,"todo.txt");
    	
    	try{
    		toDoItems = new ArrayList<String>(FileUtils.readLines(toDoFile));
    	}
    	catch(IOException e){
    		toDoItems = new ArrayList<String>();
    	}
    	
    }
    
    // Method to write all to-do item entries from local array to file
    private void writeItems(){
    	File filesDir = getFilesDir();
    	File toDoFile = new File(filesDir,"todo.txt");
    	
    	try{
    		FileUtils.writeLines(toDoFile, toDoItems);
   
    	}
    	catch(IOException e){
    		e.printStackTrace();
    	}
    	
    }
    
}
