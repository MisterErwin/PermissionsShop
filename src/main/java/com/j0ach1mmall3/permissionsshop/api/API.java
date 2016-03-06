package com.j0ach1mmall3.permissionsshop.api;

import com.j0ach1mmall3.permissionsshop.Main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class API {
    private final List<Shop> shops;
    private final List<Discount> discounts;
    private final List<Sale> sales;
    public API(Main plugin) {
        this.shops = plugin.getShops().getShops();
        this.discounts = plugin.getDiscounts().getDiscounts();
        this.sales = plugin.getSales().getSales();
    }

    public List<Shop> getShops() {
        return this.shops;
    }

    public boolean isShop(Shop shop) {
        return this.shops.contains(shop);
    }

    public boolean isShop(String identifier) {
        return this.getShop(identifier) != null;
    }

    public Shop getShop(String identifier) {
        for(Shop shop : this.shops) {
            if(shop.getIdentifier().equals(identifier)) return shop;
        }
        return null;
    }

    public List<Sale> getSales() {
        return this.sales;
    }

    public List<Sale> getActiveSales(Shop shop) {
        List<Sale> sales = new ArrayList<>();
        Date date = new Date();
        for(Sale sale : this.sales) {
            if(date.after(sale.getStartDate()) && date.before(sale.getEndDate()) && sale.getShops().contains(shop.getIdentifier())) sales.add(sale);
        }
        return sales;
    }

    public boolean isSale(Sale sale) {
        return this.sales.contains(sale);
    }

    public boolean isSale(String identifier) {
        return this.getSale(identifier) != null;
    }

    public Sale getSale(String identifier) {
        for(Sale sale : this.sales) {
            if(sale.getIdentifier().equals(identifier)) return sale;
        }
        return null;
    }

    public List<Discount> getDiscounts() {
        return this.discounts;
    }

    public List<Discount> getActiveDiscounts(Shop shop) {
        List<Discount> discounts = new ArrayList<>();
        for(Discount discount : discounts) {
            if(discount.getShops().contains(shop.getIdentifier())) discounts.add(discount);
        }
        return discounts;
    }

    public boolean isDiscount(Discount discount) {
        return this.discounts.contains(discount);
    }

    public boolean isDiscount(String identifier) {
        return this.getDiscount(identifier) != null;
    }

    public Discount getDiscount(String identifier) {
        for(Discount discount : this.discounts) {
            if(discount.getIdentifier().equals(identifier)) return discount;
        }
        return null;
    }

    public PathItem getPathItemByPosition(int position, PathItem currentPathItem) {
        return ((CategoryPackageHolder) currentPathItem).getPathItem(position);
    }
}
