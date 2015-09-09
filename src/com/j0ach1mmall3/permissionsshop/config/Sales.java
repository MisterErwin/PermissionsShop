package com.j0ach1mmall3.permissionsshop.config;

import com.j0ach1mmall3.jlib.methods.Parsing;
import com.j0ach1mmall3.jlib.storage.yaml.ConfigLoader;
import com.j0ach1mmall3.jlib.storage.yaml.ConfigMethods;
import com.j0ach1mmall3.permissionsshop.Main;
import com.j0ach1mmall3.permissionsshop.api.Sale;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Sales extends ConfigLoader {
    private boolean saleGlow;
    private String salePrefix;
    private List<Sale> sales;
	public Sales(Main plugin){
        super("sales.yml", plugin);
		saleGlow = config.getBoolean("SaleGlow");
		salePrefix = config.getString("SalePrefix");
		sales = loadSales();
	}
	
	private List<Sale> loadSales(){
		List<Sale> sales = new ArrayList<Sale>();
		for(String sale : ConfigMethods.getKeys(config, "Sales")){
			sales.add(getSaleByName(sale));
		}
		return sales;
	}
	
	private Sale getSaleByName(String sale){
		String path = "Sales." + sale + ".";
		String permission = config.getString(path + "Permission");
		List<String> shops = config.getStringList(path + "Shops");
		Date startDate = parseDate(config.getString(path + "StartDate"));
		Date endDate = parseDate(config.getString(path + "EndDate"));
		double percentage = config.getDouble(path + "Percentage");
		double amount = config.getDouble(path + "Amount");
		return new Sale(sale, permission, shops, startDate, endDate, percentage, amount);
	}
	
	@SuppressWarnings("deprecation")
	private Date parseDate(String s){
		String[] splitted = s.split("-");
		Calendar cal = Calendar.getInstance();
		cal.set(Parsing.parseString(splitted[2]), Parsing.parseString(splitted[1])-1, Parsing.parseString(splitted[0])-1);
		return cal.getTime();
	}

    public List<Sale> getSales() {
        return sales;
    }

    public String getSalePrefix() {
        return salePrefix;
    }

    public boolean isSaleGlow() {
        return saleGlow;
    }
}
