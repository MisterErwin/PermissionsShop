package com.j0ach1mmall3.permissionsshop.api;

import com.j0ach1mmall3.jlib.inventory.GuiItem;

import java.util.List;

public class Package extends PathItem {
	private final String permission;
	private final double price;
	private final GuiItem item;
	private final List<String> commands;

	public Package(String identifier, String permission, double price, GuiItem item, List<String> commands) {
		super(identifier);
		this.permission = permission;
		this.price = price;
		this.item = item;
		this.commands = commands;
	}

	public String getPermission() {
		return this.permission;
	}

	public double getPrice() {
		return this.price;
	}

	public GuiItem getItem() {
		return this.item;
	}

	public List<String> getCommands() {
		return this.commands;
	}
}
