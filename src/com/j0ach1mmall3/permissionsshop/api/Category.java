package com.j0ach1mmall3.permissionsshop.api;

import com.j0ach1mmall3.jlib.inventory.CustomItem;

import java.util.List;

public class Category {
	private final String identifier;
	private final String permission;
	private final double price;
	private final CustomItem item;
	private final List<Package> packages;
	public Category(String identifier, String permission, double price, CustomItem item, List<Package> packages){
		this.identifier = identifier;
		this.permission = permission;
		this.price = price;
		this.item = item;
		this.packages = packages;
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
	
	public List<Package> getPackages(){
		return packages;
	}
}
