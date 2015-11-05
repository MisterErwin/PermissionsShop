package com.j0ach1mmall3.permissionsshop.api;

import java.util.List;

public class Shop {
	private final String identifier;
	private final String command;
	private final String permission;
	private final double price;
	private final String guiName;
	private final List<Category> categories;
	private final List<Package> packages;
	public Shop(String identifier, String command, String permission, double price, String guiName, List<Category> categories, List<Package> packages){
		this.identifier = identifier;
		this.command = command;
		this.permission = permission;
		this.price = price;
		this.guiName = guiName;
		this.categories = categories;
		this.packages = packages;
	}
	
	public String getIdentifier(){
		return identifier;
	}
	
	public String getCommand(){
		return command;
	}
	
	public String getPermission(){
		return permission;
	}
	
	public double getPrice(){
		return price;
	}
	
	public String getGuiName(){
		return guiName;
	}
	
	public List<Category> getCategories(){
		return categories;
	}
	
	public List<Package> getPackages(){
		return packages;
	}
}
