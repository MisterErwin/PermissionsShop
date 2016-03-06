package com.j0ach1mmall3.permissionsshop.api;

import java.util.List;

public class Discount {
    private final String identifier;
    private final String permission;
    private final List<String> shops;
    private final double percentage;
    private final double amount;
    public Discount(String identifier, String permission, List<String> shops, double percentage, double amount) {
        this.identifier = identifier;
        this.permission = permission;
        this.shops = shops;
        this.percentage = percentage;
        this.amount = amount;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getPermission() {
        return this.permission;
    }

    public List<String> getShops() {
        return this.shops;
    }

    public double getPercentage() {
        return this.percentage;
    }

    public double getAmount() {
        return this.amount;
    }
}
