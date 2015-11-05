package com.j0ach1mmall3.permissionsshop.config;

import com.j0ach1mmall3.jlib.storage.file.yaml.ConfigLoader;
import com.j0ach1mmall3.permissionsshop.Main;
import com.j0ach1mmall3.permissionsshop.api.Discount;

import java.util.List;
import java.util.stream.Collectors;

public class Discounts extends ConfigLoader {
    private final List<Discount> discounts;
	public Discounts(Main plugin) {
        super("discounts.yml", plugin);
        discounts = loadDiscounts();
	}
	
	private List<Discount> loadDiscounts(){
        return customConfig.getKeys("Discounts").stream().map(this::getDiscountByName).collect(Collectors.toList());
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
