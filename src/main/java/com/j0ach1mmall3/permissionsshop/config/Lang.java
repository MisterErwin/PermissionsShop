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
        this.notEnoughMoney = this.config.getString("NotEnoughMoney");
        this.commandNoPermission = this.config.getString("CommandNoPermission");
        this.shopNoPermission = this.config.getString("ShopNoPermission");
        this.categoryNoPermission = this.config.getString("CategoryNoPermission");
        this.packageNoPermission = this.config.getString("PackageNoPermission");
        this.confirmGuiName = this.config.getString("ConfirmGuiName");
        this.refusedPurchase = this.config.getString("RefusedPurchase");
        this.successfulPurchase = this.config.getString("SuccessfulPurchase");
	}

    public String getSuccessfulPurchase() {
        return this.successfulPurchase;
    }

    public String getRefusedPurchase() {
        return this.refusedPurchase;
    }

    public String getConfirmGuiName() {
        return this.confirmGuiName;
    }

    public String getCategoryNoPermission() {
        return this.categoryNoPermission;
    }

    public String getPackageNoPermission() {
        return this.packageNoPermission;
    }

    public String getShopNoPermission() {
        return this.shopNoPermission;
    }

    public String getCommandNoPermission() {
        return this.commandNoPermission;
    }

    public String getNotEnoughMoney() {
        return this.notEnoughMoney;
    }
}
