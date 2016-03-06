package com.j0ach1mmall3.permissionsshop.api;

public class Shop extends CategoryPackageHolder {
	private final String command;
	private final String permission;
	private final double price;

	public Shop(String identifier, String command, String permission, double price, String guiName, int guiSize) {
        super(identifier, guiName, guiSize);
		this.command = command;
		this.permission = permission;
		this.price = price;
	}

    public String getCommand() {
        return this.command;
    }

    public String getPermission() {
        return this.permission;
    }

    public double getPrice() {
        return this.price;
    }
}
