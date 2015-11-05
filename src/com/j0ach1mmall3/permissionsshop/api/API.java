package com.j0ach1mmall3.permissionsshop.api;

import com.j0ach1mmall3.permissionsshop.Main;
import com.j0ach1mmall3.permissionsshop.config.Discounts;
import com.j0ach1mmall3.permissionsshop.config.Sales;
import com.j0ach1mmall3.permissionsshop.config.Shops;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class API {
	private static Shops shops;
    private static Discounts discounts;
    private static Sales sales;
    public API(Main plugin) {
        this.shops = plugin.getShops();
        this.discounts = plugin.getDiscounts();
        this.sales = plugin.getSales();
    }

    public static List<Shop> getShops(){
		return shops.getShops();
	}
	
	public static boolean isShop(Shop shop){
		return shops.getShops().contains(shop);
	}
	
	public static boolean isShop(String identifier){
		for(Shop shop : shops.getShops()){
			if(shop.getIdentifier().equals(identifier)){
				return true;
			}
		}
		return false;
	}
	
	public static Shop getShop(String identifier){
		if(isShop(identifier)){
			for(Shop shop : shops.getShops()){
				if(shop.getIdentifier().equals(identifier)){
					return shop;
				}
			}
		}
		return null;
	}
	
	public static List<Sale> getSales(){
		return sales.getSales();
	}
	
	@SuppressWarnings("deprecation")
	public static List<Sale> getActiveSales(Shop shop){
		List<Sale> sales = new ArrayList<>();
		Date date = new Date();
        sales.addAll(getSales().stream().filter(s -> date.after(s.getStartDate()) && date.before(s.getEndDate()) && s.getShops().contains(shop.getIdentifier())).collect(Collectors.toList()));
		return sales;
	}
	
	public static boolean isSale(Sale sale){
		return sales.getSales().contains(sale);
	}
	
	public static boolean isSale(String identifier){
		for(Sale sale : sales.getSales()){
			if(sale.getIdentifier().equals(identifier)){
				return true;
			}
		}
		return false;
	}
	
	public static Sale getSale(String identifier){
		if(isSale(identifier)){
			for(Sale sale : sales.getSales()){
				if(sale.getIdentifier().equals(identifier)){
					return sale;
				}
			}
		}
		return null;
	}
	
	public static List<Discount> getDiscounts(){
		return discounts.getDiscounts();
	}
	
	public static List<Discount> getActiveDiscounts(Shop shop){
        return getDiscounts().stream().filter(d -> d.getShops().contains(shop.getIdentifier())).collect(Collectors.toList());
	}
	
	public static boolean isDiscount(Discount discount){
		return discounts.getDiscounts().contains(discount);
	}
	
	public static boolean isDiscount(String identifier){
		for(Discount discount : discounts.getDiscounts()){
			if(discount.getIdentifier().equals(identifier)){
				return true;
			}
		}
		return false;
	}
	
	public static Discount getDiscount(String identifier){
		if(isDiscount(identifier)){
			for(Discount discount : discounts.getDiscounts()){
				if(discount.getIdentifier().equals(identifier)){
					return discount;
				}
			}
		}
		return null;
	}
}
