package com.proj.apps.simpletodolist;


import java.util.ArrayList;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// This class implements a To do list items database using the Android SQLite interfaces

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "to_do_items_database";
 
    // To do items table name
    private static final String TABLE_NAME = "to_do_items_table";
 
    // To do items db Table Columns names
    private static final String KEY_NAME = "db_item_name";
    private static final String KEY_PRIORITY = "db_item_priority";
    private static final String KEY_DUE_DATE = "db_due_date";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }	
    
    // Create the To do list item SQL table (only created if it does not exist)
    
    @Override
    public void onCreate(SQLiteDatabase db) {
    	
    	// Construct SQL command string for to do items table creation
        String CREATE_TO_DO_ITEMS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_NAME + " TEXT PRIMARY KEY," 
                + KEY_PRIORITY + " INTEGER," + KEY_DUE_DATE + " TEXT" + ")";
        
        // Create the table 
        db.execSQL(CREATE_TO_DO_ITEMS_TABLE);
    }; 

    
    // Obtain all To do items from the SQL table
    public ArrayList<ToDoItem> getAllToDoItems (){
    
        ArrayList<ToDoItem> itemList = new ArrayList<ToDoItem>();
        
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and add to the return item list
        if (cursor.moveToFirst()) {
            do {
            	ToDoItem item = new ToDoItem();
                
                item.setItemName(cursor.getString(0));
                item.setItemPriority(Integer.parseInt(cursor.getString(1)));
                item.setItemDueDate(cursor.getString(2));
                
                // Adding contact to list
                itemList.add(item);
            } while (cursor.moveToNext());
        }
     
        db.close();
        
        // return to do item list
        return itemList;  	
    	  	
    	
    };
   
    // Write all To do items to the SQL table
    public void addAllToDoItems (ArrayList<ToDoItem> item_list){
        
    	SQLiteDatabase db = this.getWritableDatabase();
        
    	// Clear the entire table and then populate each row based on the current to-do item list
    	// TBD - Better method is needed
    	
    	db.delete(TABLE_NAME, null, null);
    	
        for (int index=0; index < item_list.size(); index++)
        {
        	ContentValues values = new ContentValues();
        	
        	ToDoItem item = item_list.get(index);
        	
        	values.put(KEY_NAME, item.getItemName()); 
        	values.put(KEY_PRIORITY, item.getItemPriority()); 
        	values.put(KEY_DUE_DATE,  item.getItemDueDate());
        	
        	// Insert one Row of a to-do item entry
        	db.insert(TABLE_NAME, null, values);
        }
        
        db.close(); // Closing database connection
    	
    };

    
    // Add To do item to the SQL table
    public void addToDoItem(ToDoItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getItemName()); // Contact Name
        values.put(KEY_PRIORITY, item.getItemPriority()); // Contact Phone Number
        values.put(KEY_DUE_DATE, item.getItemPriority());
        
        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    	
    };

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
       
        // Create tables again
        onCreate(db);
	
	};
    
  
    
    
    
}
