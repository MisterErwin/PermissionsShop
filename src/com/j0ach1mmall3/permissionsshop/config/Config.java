package com.j0ach1mmall3.permissionsshop.config;

import com.j0ach1mmall3.jlib.storage.file.yaml.ConfigLoader;
import com.j0ach1mmall3.permissionsshop.Main;

public class Config extends ConfigLoader {
	private final boolean enableDiscounts;
	private final boolean enableSales;
	private final boolean purchaseConfirmation;
	private final String serverAccount;
	private final String returnItem_Name;
	private final String returnItem_Description;
	private final String returnItem_Item;
	private final String confirmItem_Name;
	private final String confirmItem_Description;
	private final String confirmItem_Item;
	private final String refuseItem_Name;
	private final String refuseItem_Description;
	private final String refuseItem_Item;
	public Config(Main plugin) {
        super("config.yml", plugin);
        enableDiscounts = config.getBoolean("EnableDiscounts");
        enableSales = config.getBoolean("EnableSales");
        purchaseConfirmation = config.getBoolean("PurchaseConfirmation");
        serverAccount = config.getString("ServerAccount");
        returnItem_Name = config.getString("ReturnItem.Name");
        returnItem_Description = config.getString("ReturnItem.Description");
        returnItem_Item = config.getString("ReturnItem.Item");
        confirmItem_Name = config.getString("ConfirmItem.Name");
        confirmItem_Description = config.getString("ConfirmItem.Description");
        confirmItem_Item = config.getString("ConfirmItem.Item");
        refuseItem_Name = config.getString("RefuseItem.Name");
        refuseItem_Description = config.getString("RefuseItem.Description");
        refuseItem_Item = config.getString("RefuseItem.Item");
	}

    public String getRefuseItemItem() {
        return refuseItem_Item;
    }

    public String getRefuseItemDescription() {
        return refuseItem_Description;
    }

    public String getRefuseItemName() {
        return refuseItem_Name;
    }

    public String getConfirmItemItem() {
        return confirmItem_Item;
    }

    public String getConfirmItemDescription() {
        return confirmItem_Description;
    }

    public String getConfirmItemName() {
        return confirmItem_Name;
    }

    public String getReturnItemDescription() {
        return returnItem_Description;
    }

    public String getReturnItemItem() {
        return returnItem_Item;
    }

    public String getServerAccount() {
        return serverAccount;
    }

    public String getReturnItemName() {
        return returnItem_Name;
    }

    public boolean isPurchaseConfirmation() {
        return purchaseConfirmation;
    }

    public boolean isEnableSales() {
        return enableSales;
    }

    public boolean isEnableDiscounts() {
        return enableDiscounts;
    }
}
