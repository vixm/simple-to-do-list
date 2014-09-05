package com.proj.apps.simpletodolist;

public class ToDoItem{
	String itemName;
	int itemPriority;
	String itemDueDate;
	
	public ToDoItem(){
		
	}
	
	public ToDoItem (String name, int priority, String due_date){
		itemName = name;
		itemPriority = priority;
		itemDueDate = due_date;
	}

	public String getItemName(){
		return this.itemName;
	}
	
	public int getItemPriority(){
		return this.itemPriority;
	}
	
	public String getItemDueDate(){
		return this.itemDueDate;
	}
	
	public void setItemName(String name){
		this.itemName = name;
	}
	
	public void setItemPriority(int priority){
		this.itemPriority = priority;
	}
	
	public void setItemDueDate(String due_date){
		this.itemDueDate = due_date;
	}
	
}
