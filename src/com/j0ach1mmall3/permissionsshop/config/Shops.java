package com.j0ach1mmall3.permissionsshop.config;

import com.j0ach1mmall3.jlib.storage.file.yaml.ConfigLoader;
import com.j0ach1mmall3.permissionsshop.Main;
import com.j0ach1mmall3.permissionsshop.api.Category;
import com.j0ach1mmall3.permissionsshop.api.CategoryPackageHolder;
import com.j0ach1mmall3.permissionsshop.api.Package;
import com.j0ach1mmall3.permissionsshop.api.Shop;

import java.util.ArrayList;
import java.util.List;

public class Shops extends ConfigLoader {
    private final List<Shop> shops;
	public Shops(Main plugin) {
        super("shops.yml", plugin);
		this.shops = this.loadShops();
	}
	
	private List<Shop> loadShops() {
        List<Shop> shops = new ArrayList<>();
		for(String s : this.customConfig.getKeys("Shops")) {
            shops.add(this.getShop("Shops." + s, s));
		}
        return shops;
	}

    private Shop getShop(String path, String identifier) {
        Shop shop = new Shop(
                identifier,
                this.config.getString(path + ".Command"),
                this.config.getString(path + ".Permission"),
                this.config.getDouble(path + ".Price"),
                this.config.getString(path + ".GuiName"),
                this.config.getInt(path + ".GuiSize")
        );
        shop.setParent(null);
        shop.setCategories(this.getCategories(path, shop));
        shop.setPackages(this.getPackages(path, shop));
        return shop;
    }

    private List<Category> getCategories(String path, CategoryPackageHolder parent) {
        List<Category> categories = new ArrayList<>();
        for(String s : this.customConfig.getKeys(path + ".Categories")) {
            categories.add(this.getCategory(path + ".Categories." + s, s, parent));
        }
        return categories;
    }

    private Category getCategory(String path, String identifier, CategoryPackageHolder parent) {
        Category category = new Category(
                identifier,
                this.config.getString(path + ".Permission"),
                this.config.getDouble(path + ".Price"),
                this.customConfig.getGuiItemNew(this.config, path),
                this.config.getInt(path + ".GuiSize")
        );
        category.setParent(parent);
        category.setCategories(this.getCategories(path, category));
        category.setPackages(this.getPackages(path, category));
        return category;
    }

    private List<Package> getPackages(String path, CategoryPackageHolder parent) {
        List<Package> packages = new ArrayList<>();
        for(String s : this.customConfig.getKeys(path + ".Packages")) {
            packages.add(this.getPackage(path + ".Packages." + s, s, parent));
        }
        return packages;
    }

    private Package getPackage(String path, String identifier, CategoryPackageHolder parent) {
        Package pckage = new Package(
                identifier,
                this.config.getString(path + ".Permission"),
                this.config.getDouble(path + ".Price"),
                this.customConfig.getGuiItemNew(this.config, path),
                this.config.getStringList(path + ".Commands")
        );
        pckage.setParent(parent);
        return pckage;
    }

    public List<Shop> getShops() {
        return this.shops;
    }
}