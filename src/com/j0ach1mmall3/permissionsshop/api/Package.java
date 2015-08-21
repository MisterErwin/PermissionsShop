package com.j0ach1mmall3.permissionsshop.api;

import java.util.List;

import com.j0ach1mmall3.jlib.objects.CustomItem;

public class Package {
	private String identifier;
	private String permission;
	private double price;
	private CustomItem item;
	private List<String> commands;
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
