package com.j0ach1mmall3.permissionsshop.config;

import com.j0ach1mmall3.jlib.methods.Parsing;
import com.j0ach1mmall3.jlib.methods.Placeholders;
import com.j0ach1mmall3.jlib.objects.CustomItem;
import com.j0ach1mmall3.jlib.storage.yaml.Config;
import com.j0ach1mmall3.jlib.storage.yaml.ConfigMethods;
import com.j0ach1mmall3.permissionsshop.Main;
import com.j0ach1mmall3.permissionsshop.api.Category;
import com.j0ach1mmall3.permissionsshop.api.Package;
import com.j0ach1mmall3.permissionsshop.api.Shop;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Shops {
	private Main plugin;
    private Config customConfig;
	private FileConfiguration config;
    private List<Shop> shops;
	public Shops(Main plugin){
        this.plugin = plugin;
        this.customConfig = new Config("shops.yml", plugin);
		customConfig.saveDefaultConfig();
		config = customConfig.getConfig();
		shops = loadShops();
	}
	
	private List<Shop> loadShops(){
		List<Shop> shops = new ArrayList<>();
		for(String shop : ConfigMethods.getKeys(config, "Shops")){
			shops.add(getShopByName(shop));
		}
		return shops;
	}
	
	private Shop getShopByName(String shop){
		String path = "Shops." + shop + ".";
		String command = config.getString(path + "Command");
		String permission = config.getString(path + "Permission");
		double price = config.getDouble(path + "Price");
		String guiName = config.getString(path + "GuiName");
		List<Category> categories = new ArrayList<>();
		for(String category : ConfigMethods.getKeys(config, path + "Categories")){
			categories.add(getCategoryByName(shop, category));
		}
		List<Package> packages = new ArrayList<>();
		for(String pckage : ConfigMethods.getKeys(config, path + "Packages")){
			packages.add(getPackageByName(shop, pckage));
		}
		return new Shop(shop, command, permission, price, guiName, categories, packages);
	}
	
	private Category getCategoryByName(String shop, String category){
		String path = "Shops." + shop + ".Categories." + category + ".";
		String permission = config.getString(path + "Permission");
		double price = config.getDouble(path + "Price");
		CustomItem item = new CustomItem(Parsing.parseMaterial(config.getString(path + "Item")), 1, Parsing.parseData(config.getString(path + "Item")), Placeholders.parse(config.getString(path + ".Name")), Placeholders.parse(config.getString(path + ".Description")));
		List<Package> packages = new ArrayList<>();
		for(String pckage : ConfigMethods.getKeys(config, path + "Packages")){
			packages.add(getPackageByName(shop, category, pckage));
		}
		return new Category(category, permission, price, item, packages);
	}
	
	private Package getPackageByName(String shop, String category, String pckage){
		String path = "Shops." + shop + ".Categories." + category + ".Packages." + pckage + ".";
		String permission = config.getString(path + "Permission");
		double price = config.getDouble(path + "Price");
		CustomItem item = new CustomItem(Parsing.parseMaterial(config.getString(path + "Item")), 1, Parsing.parseData(config.getString(path + "Item")), Placeholders.parse(config.getString(path + ".Name")), Placeholders.parse(config.getString(path + ".Description")));
        List<Package> packages = new ArrayList<>();
		List<String> commands = config.getStringList(path + "Commands");
		return new Package(pckage, permission, price, item, commands);
	}
	
	private Package getPackageByName(String shop, String pckage){
		String path = "Shops." + shop + ".Packages." + pckage + ".";
		String permission = config.getString(path + "Permission");
		double price = config.getDouble(path + "Price");
		CustomItem item = new CustomItem(Parsing.parseMaterial(config.getString(path + "Item")), 1, Parsing.parseData(config.getString(path + "Item")), Placeholders.parse(config.getString(path + ".Name")), Placeholders.parse(config.getString(path + ".Description")));
        List<Package> packages = new ArrayList<>();
		List<String> commands = config.getStringList(path + "Commands");
		return new Package(pckage, permission, price, item, commands);
	}

    public List<Shop> getShops() {
        return shops;
    }
}
