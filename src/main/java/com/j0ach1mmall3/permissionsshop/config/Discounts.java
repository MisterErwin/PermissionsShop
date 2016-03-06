package com.j0ach1mmall3.permissionsshop.config;

import com.j0ach1mmall3.jlib.storage.file.yaml.ConfigLoader;
import com.j0ach1mmall3.permissionsshop.Main;
import com.j0ach1mmall3.permissionsshop.api.Discount;

import java.util.ArrayList;
import java.util.List;

public class Discounts extends ConfigLoader {
    private final List<Discount> discounts;
    public Discounts(Main plugin) {
        super("discounts.yml", plugin);
        this.discounts = this.loadDiscounts();
    }

    private List<Discount> loadDiscounts() {
        List<Discount> discounts = new ArrayList<>();
        for(String s : this.customConfig.getKeys("Discounts")) {
            discounts.add(this.getDiscount(s));
        }
        return discounts;
    }

    private Discount getDiscount(String discount) {
        String path = "Discounts." + discount + '.';
        String permission = this.config.getString(path + "Permission");
        List<String> shops = this.config.getStringList(path + "Shops");
        double percentage = this.config.getDouble(path + "Percentage");
        double amount = this.config.getDouble(path + "Amount");
        return new Discount(discount, permission, shops, percentage, amount);
    }

    public List<Discount> getDiscounts() {
        return this.discounts;
    }
}
