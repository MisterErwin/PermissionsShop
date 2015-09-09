package com.j0ach1mmall3.permissionsshop.config;

import com.j0ach1mmall3.jlib.storage.yaml.ConfigLoader;
import com.j0ach1mmall3.jlib.storage.yaml.ConfigMethods;
import com.j0ach1mmall3.permissionsshop.Main;
import com.j0ach1mmall3.permissionsshop.api.Discount;

import java.util.ArrayList;
import java.util.List;

public class Discounts extends ConfigLoader {
    private List<Discount> discounts;
	public Discounts(Main plugin) {
        super("discounts.yml", plugin);
        discounts = loadDiscounts();
	}
	
	private List<Discount> loadDiscounts(){
		List<Discount> discounts = new ArrayList<Discount>();
		for(String discount : ConfigMethods.getKeys(config, "Discounts")){
			discounts.add(getDiscountByName(discount));
		}
		return discounts;
	}
	
	private Discount getDiscountByName(String discount){
		String path = "Discounts." + discount + ".";
		String permission = config.getString(path + "Permission");
		List<String> shops = config.getStringList(path + "Shops");
		double percentage = config.getDouble(path + "Percentage");
		double amount = config.getDouble(path + "Amount");
		return new Discount(discount, permission, shops, percentage, amount);
	}

    public List<Discount> getDiscounts() {
        return discounts;
    }
}
