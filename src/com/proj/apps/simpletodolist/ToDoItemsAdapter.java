package com.proj.apps.simpletodolist;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


// This class is the custom adapter for rendering each entry in the to-do item list
public class ToDoItemsAdapter extends ArrayAdapter<ToDoItem> {
	
	public ToDoItemsAdapter(Context context, ArrayList<ToDoItem> to_do_items) {
	       super(context, R.layout.item_to_do, to_do_items);
	}

	
	//Method translates to-do item list entry to its display view
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
    	// Get the data item for this position
       ToDoItem item = getItem(position);    
       
       // Check if an existing view is being reused, otherwise inflate the view
       if (convertView == null) {
          convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_to_do, parent, false);
       }
       
       // Lookup view for data population
       TextView tvName = (TextView) convertView.findViewById(R.id.tvItemName);
       TextView tvPriority = (TextView) convertView.findViewById(R.id.tvItemPriority);
       TextView tvDueDate = (TextView) convertView.findViewById(R.id.tvItemDueDate);
       
       // Populate the data into the template view using the data object
       tvName.setText("ToDo: " + item.itemName);
       tvPriority.setText("Priority: " + Integer.toString(item.itemPriority));
       tvDueDate.setText("Due Date: " + item.itemDueDate);
       
       // Return the completed view to render on screen
       return convertView;
   }	
}
