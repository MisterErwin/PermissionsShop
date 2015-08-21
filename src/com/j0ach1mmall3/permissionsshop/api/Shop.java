package com.j0ach1mmall3.permissionsshop.api;

import java.util.List;

public class Shop {
	private String identifier;
	private String command;
	private String permission;
	private double price;
	private String guiName;
	private List<Category> categories;
	private List<Package> packages;
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
