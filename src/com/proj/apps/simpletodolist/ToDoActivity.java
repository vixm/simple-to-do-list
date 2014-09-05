package com.proj.apps.simpletodolist;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import android.widget.EditText;
import android.widget.ListView;

public class ToDoActivity extends Activity {
	
	// List of items displayed in the Main Activity 
	private ListView lvItems;
	
	// New Item view in the Main activity
	private EditText etItemName, etItemPriority, etItemDueDate;
	
	// Interface to the SQL db for the to do items
	private DatabaseHandler db;
	
	// Local run-time storage for the to-do items which can be added, modified or deleted during app operation 
	private ArrayList<ToDoItem> toDoItems;
	
	// Adapter between the ListView and the ArrayList
	private ToDoItemsAdapter itemsAdapter;
	
	private final int REQUEST_CODE = 50;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        
        // Create handle for accessing the SQL db
        db = new DatabaseHandler(this);
        
        // Get the handles to the To Do item list view on the main activity screen
        lvItems = (ListView)findViewById(R.id.lvItemList);
        
        // Get the handles to the Add Item fields on the main activity screen
        etItemName = (EditText)findViewById(R.id.etItemName);
        etItemPriority = (EditText)findViewById(R.id.etItemPriority);
        etItemDueDate = (EditText)findViewById(R.id.etItemDueDate);
        
        toDoItems = new ArrayList<ToDoItem>();
        
        // Populate To Do list in local array with entries from SQL db
        readItems();
        
        // Create custom adapter between the To Do item list and the array list 
        itemsAdapter = new ToDoItemsAdapter(this, toDoItems);
        
        // Attach adapter to the list view
        lvItems.setAdapter(itemsAdapter);
        
        // Set up listener for Long click(remove) and single click(edit item) in To Do item list   
        setupListViewListener();
               
    }

	// Method executed on clicking the Add button
    public void onAddItem(View v) {
    	
    	// Obtain the entered new item and update the To Do item list
    	String addedItemName = etItemName.getText().toString();
    	
    	String addedItemPriorityString = (etItemPriority.getText().toString());
    	
    	int addedItemPriority;
    	
		if (addedItemPriorityString.isEmpty())
    		addedItemPriority = 1;
    	else
    		addedItemPriority = Integer.parseInt(addedItemPriorityString);
    	
    	String addedItemDueDate = etItemDueDate.getText().toString();
    	
    	ToDoItem newItem = new ToDoItem(addedItemName, addedItemPriority, addedItemDueDate);
    	
    	itemsAdapter.add(newItem);
    	
    	writeItems();
    	
    	// Clear the new item entry box
    	etItemName.setText("");
    	etItemPriority.setText("");
    	etItemDueDate.setText("");
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
				// TBD - Needs better method to remove just one row from Sql db
				writeItems();
				
				return true;
			}
			
		});
		
		lvItems.setOnItemClickListener(new OnItemClickListener() {
		
			// Method executed on a single click on a to-do item entry -> results in the edit item screen being shown
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				// Bring up the edit form activity using an "Intent"  To properly load the edit form data, pass along the 
				// todo item fields and position of that item to the edit activity using "extras" 
				
				Intent edit_item_intent = new Intent(ToDoActivity.this, EditItemActivity.class);
				
				// put "extras", namely position and the to do item details
				// into the bundle for access in the Edit Item activity
				edit_item_intent.putExtra("position", position);
				
				edit_item_intent.putExtra("edit_item_name", toDoItems.get(position).getItemName()); 
				edit_item_intent.putExtra("edit_item_priority", toDoItems.get(position).getItemPriority());
				edit_item_intent.putExtra("edit_item_due_date", toDoItems.get(position).getItemDueDate());
				
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
         
         String edited_item_name = data.getExtras().getString("edited_item_name");
         int edited_item_priority = data.getExtras().getInt("edited_item_priority");
         String edited_item_due_date = data.getExtras().getString("edited_item_due_date");
         
         ToDoItem edited_item = new ToDoItem();
         
         edited_item.setItemName(edited_item_name);
         edited_item.setItemPriority(edited_item_priority);
         edited_item.setItemDueDate(edited_item_due_date);
         
         
         // Update the todo items list with the edited item at the correct position
         toDoItems.set(position, edited_item);
         
         itemsAdapter.notifyDataSetChanged();
         
         // Update the todo items list in the SQL db
         // TBD - Needs better method to update just one row in SQL db
         writeItems();
      }
    } 
    
    

    
    // Method to read all to-do item entries from SQL DB into local array
    private void readItems() {    	
    		
    	toDoItems = db.getAllToDoItems();
    
    }
    
    // Method to write all to-do item entries from local array to file
    private void writeItems(){
 
    	db.addAllToDoItems(toDoItems);
    	
    }  
}
