package com.j0ach1mmall3.permissionsshop.config;

import com.j0ach1mmall3.jlib.storage.yaml.ConfigLoader;
import com.j0ach1mmall3.permissionsshop.Main;

public class Lang extends ConfigLoader {
	private String notEnoughMoney;
    private String commandNoPermission;
    private String shopNoPermission;
    private String categoryNoPermission;
    private String packageNoPermission;
    private String confirmGuiName;
    private String refusedPurchase;
    private String successfulPurchase;
	public Lang(Main plugin) {
        super("lang.yml", plugin);
        notEnoughMoney = config.getString("NotEnoughMoney");
        commandNoPermission = config.getString("CommandNoPermission");
        shopNoPermission = config.getString("ShopNoPermission");
        categoryNoPermission = config.getString("CategoryNoPermission");
        packageNoPermission = config.getString("PackageNoPermission");
        confirmGuiName = config.getString("ConfirmGuiName");
        refusedPurchase = config.getString("RefusedPurchase");
        successfulPurchase = config.getString("SuccessfulPurchase");
	}

    public String getSuccessfulPurchase() {
        return successfulPurchase;
    }

    public String getRefusedPurchase() {
        return refusedPurchase;
    }

    public String getConfirmGuiName() {
        return confirmGuiName;
    }

    public String getCategoryNoPermission() {
        return categoryNoPermission;
    }

    public String getPackageNoPermission() {
        return packageNoPermission;
    }

    public String getShopNoPermission() {
        return shopNoPermission;
    }

    public String getCommandNoPermission() {
        return commandNoPermission;
    }

    public String getNotEnoughMoney() {
        return notEnoughMoney;
    }
}
