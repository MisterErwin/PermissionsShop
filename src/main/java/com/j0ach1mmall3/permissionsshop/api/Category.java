package com.j0ach1mmall3.permissionsshop.api;

import com.j0ach1mmall3.jlib.inventory.GuiItem;

public class Category extends CategoryPackageHolder {
    private final String permission;
    private final double price;
    private final GuiItem item;

    public Category(String identifier, String permission, double price, GuiItem item, int guiSize) {
        super(identifier, item.getItem().getItemMeta().getDisplayName(), guiSize);
        this.permission = permission;
        this.price = price;
        this.item = item;
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
}
