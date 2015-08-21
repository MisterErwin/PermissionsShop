package com.j0ach1mmall3.permissionsshop.api;

import java.util.List;

public class Discount {
	private String identifier;
	private String permission;
	private List<String> shops;
	private double percentage;
	private double amount;
	public Discount(String identifier, String permission, List<String> shops, double percentage, double amount){
		this.identifier = identifier;
		this.permission = permission;
		this.shops = shops;
		this.percentage = percentage;
		this.amount = amount;
	}
	
	public String getIdentifier(){
		return identifier;
	}
	
	public String getPermission(){
		return permission;
	}
	
	public List<String> getShops(){
		return shops;
	}
	
	public double getPercentage(){
		return percentage;
	}
	
	public double getAmount(){
		return amount;
	}
}
