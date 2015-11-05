package com.j0ach1mmall3.permissionsshop.api;

import com.j0ach1mmall3.jlib.inventory.CustomItem;

import java.util.List;

public class Package {
	private final String identifier;
	private final String permission;
	private final double price;
	private final CustomItem item;
	private final List<String> commands;
	public Package(String identifier, String permission, double price, CustomItem item, List<String> commands){
		this.identifier = identifier;
		this.permission = permission;
		this.price = price;
		this.item = item;
		this.commands = commands;
	}
	
	public String getIdentifier(){
		return identifier;
	}
	
	public String getPermission(){
		return permission;
	}
	
	public double getPrice(){
		return price;
	}
	
	public CustomItem getItem(){
		return item;
	}
	
	public List<String> getCommands(){
		return commands;
	}
}
