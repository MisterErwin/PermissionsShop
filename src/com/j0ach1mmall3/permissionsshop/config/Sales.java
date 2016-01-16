package com.j0ach1mmall3.permissionsshop.config;

import com.j0ach1mmall3.jlib.methods.Parsing;
import com.j0ach1mmall3.jlib.storage.file.yaml.ConfigLoader;
import com.j0ach1mmall3.permissionsshop.Main;
import com.j0ach1mmall3.permissionsshop.api.Sale;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Sales extends ConfigLoader {
    private final boolean saleGlow;
    private final String salePrefix;
    private final List<Sale> sales;
	public Sales(Main plugin) {
        super("sales.yml", plugin);
		this.saleGlow = this.config.getBoolean("SaleGlow");
		this.salePrefix = this.config.getString("SalePrefix");
		this.sales = this.loadSales();
	}
	
	private List<Sale> loadSales() {
		List<Sale> sales = new ArrayList<>();
		for(String s : this.customConfig.getKeys("Sales")) {
			sales.add(this.getSaleByName(s));
		}
		return sales;
	}
	
	private Sale getSaleByName(String sale) {
		String path = "Sales." + sale + ".";
		String permission = this.config.getString(path + "Permission");
		List<String> shops = this.config.getStringList(path + "Shops");
		Date startDate = this.parseDate(this.config.getString(path + "StartDate"));
		Date endDate = this.parseDate(this.config.getString(path + "EndDate"));
		double percentage = this.config.getDouble(path + "Percentage");
		double amount = this.config.getDouble(path + "Amount");
		return new Sale(sale, permission, shops, startDate, endDate, percentage, amount);
	}
	
	private Date parseDate(String s) {
		String[] splitted = s.split("-");
		Calendar cal = Calendar.getInstance();
		cal.set(Parsing.parseInt(splitted[2]), Parsing.parseInt(splitted[1])-1, Parsing.parseInt(splitted[0])-1);
		return cal.getTime();
	}

    public List<Sale> getSales() {
        return this.sales;
    }

    public String getSalePrefix() {
        return this.salePrefix;
    }

    public boolean isSaleGlow() {
        return this.saleGlow;
    }
}
