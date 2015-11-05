package com.j0ach1mmall3.permissionsshop.config;

import com.j0ach1mmall3.jlib.integration.Placeholders;
import com.j0ach1mmall3.jlib.inventory.CustomItem;
import com.j0ach1mmall3.jlib.methods.Parsing;
import com.j0ach1mmall3.jlib.storage.file.yaml.ConfigLoader;
import com.j0ach1mmall3.permissionsshop.Main;
import com.j0ach1mmall3.permissionsshop.api.Category;
import com.j0ach1mmall3.permissionsshop.api.Package;
import com.j0ach1mmall3.permissionsshop.api.Shop;

import java.util.List;
import java.util.stream.Collectors;

public class Shops extends ConfigLoader {
    private final List<Shop> shops;
	public Shops(Main plugin){
        super("shops.yml", plugin);
		shops = loadShops();
	}
	
	private List<Shop> loadShops(){
        return customConfig.getKeys("Shops").stream().map(this::getShopByName).collect(Collectors.toList());
	}
	
	private Shop getShopByName(String shop){
		String path = "Shops." + shop + ".";
		String command = config.getString(path + "Command");
		String permission = config.getString(path + "Permission");
		double price = config.getDouble(path + "Price");
		String guiName = config.getString(path + "GuiName");
		List<Category> categories = customConfig.getKeys(path + "Categories").stream().map(category -> getCategoryByName(shop, category)).collect(Collectors.toList());
        List<Package> packages = customConfig.getKeys(path + "Packages").stream().map(pckage -> getPackageByName(shop, pckage)).collect(Collectors.toList());
        return new Shop(shop, command, permission, price, guiName, categories, packages);
	}
	
	private Category getCategoryByName(String shop, String category){
		String path = "Shops." + shop + ".Categories." + category + ".";
		String permission = config.getString(path + "Permission");
		double price = config.getDouble(path + "Price");
		CustomItem item = new CustomItem(Parsing.parseMaterial(config.getString(path + "Item")), 1, Parsing.parseData(config.getString(path + "Item")), Placeholders.parse(config.getString(path + ".Name")), Placeholders.parse(config.getString(path + ".Description")));
		List<Package> packages = customConfig.getKeys(path + "Packages").stream().map(pckage -> getPackageByName(shop, category, pckage)).collect(Collectors.toList());
        return new Category(category, permission, price, item, packages);
	}
	
	private Package getPackageByName(String shop, String category, String pckage){
		String path = "Shops." + shop + ".Categories." + category + ".Packages." + pckage + ".";
		String permission = config.getString(path + "Permission");
		double price = config.getDouble(path + "Price");
		CustomItem item = new CustomItem(Parsing.parseMaterial(config.getString(path + "Item")), 1, Parsing.parseData(config.getString(path + "Item")), Placeholders.parse(config.getString(path + ".Name")), Placeholders.parse(config.getString(path + ".Description")));
		List<String> commands = config.getStringList(path + "Commands");
		return new Package(pckage, permission, price, item, commands);
	}
	
	private Package getPackageByName(String shop, String pckage){
		String path = "Shops." + shop + ".Packages." + pckage + ".";
		String permission = config.getString(path + "Permission");
		double price = config.getDouble(path + "Price");
		CustomItem item = new CustomItem(Parsing.parseMaterial(config.getString(path + "Item")), 1, Parsing.parseData(config.getString(path + "Item")), Placeholders.parse(config.getString(path + ".Name")), Placeholders.parse(config.getString(path + ".Description")));
		List<String> commands = config.getStringList(path + "Commands");
		return new Package(pckage, permission, price, item, commands);
	}

    public List<Shop> getShops() {
        return shops;
    }
}
