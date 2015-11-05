package com.j0ach1mmall3.permissionsshop.config;

import com.j0ach1mmall3.jlib.storage.file.yaml.ConfigLoader;
import com.j0ach1mmall3.permissionsshop.Main;

public class Lang extends ConfigLoader {
	private final String notEnoughMoney;
    private final String commandNoPermission;
    private final String shopNoPermission;
    private final String categoryNoPermission;
    private final String packageNoPermission;
    private final String confirmGuiName;
    private final String refusedPurchase;
    private final String successfulPurchase;
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
