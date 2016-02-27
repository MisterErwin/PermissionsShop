package com.j0ach1mmall3.permissionsshop.config;

import com.j0ach1mmall3.jlib.integration.Placeholders;
import com.j0ach1mmall3.jlib.inventory.GUI;
import com.j0ach1mmall3.jlib.inventory.GuiItem;
import com.j0ach1mmall3.jlib.storage.file.yaml.ConfigLoader;
import com.j0ach1mmall3.permissionsshop.Main;
import org.bukkit.Sound;

public class Config extends ConfigLoader {
	private final boolean purchaseConfirmation;
	private final String serverAccount;
    private final int confirmGuiSize;
	private final GuiItem returnItem;
    private final GuiItem confirmItem;
    private final GuiItem refuseItem;
    private final Sound clickSound;
	public Config(Main plugin) {
        super("config.yml", plugin);
        this.purchaseConfirmation = this.config.getBoolean("PurchaseConfirmation");
        this.serverAccount = this.config.getString("ServerAccount");
        this.confirmGuiSize = this.config.getInt("ConfirmGuiSize");
        this.returnItem = this.customConfig.getGuiItemNew(this.config, "ReturnItem");
        this.confirmItem = this.customConfig.getGuiItemNew(this.config, "ConfirmItem");
        this.refuseItem = this.customConfig.getGuiItemNew(this.config, "RefuseItem");
        this.clickSound = Sound.valueOf(this.config.getString("ClickSound").toUpperCase());
	}

    public boolean isPurchaseConfirmation() {
        return this.purchaseConfirmation;
    }

    public String getServerAccount() {
        return this.serverAccount;
    }

    public GuiItem getReturnItem() {
        return this.returnItem;
    }

    public GuiItem getConfirmItem() {
        return this.confirmItem;
    }

    public GuiItem getRefuseItem() {
        return this.refuseItem;
    }

    public GUI getConfirmGui() {
        GUI gui = new GUI(Placeholders.parse(((Main) this.storage.getPlugin()).getLang().getConfirmGuiName()), this.confirmGuiSize);
        gui.setItems(this.confirmItem, this.refuseItem);
        return gui;
    }

    public Sound getClickSound() {
        return this.clickSound;
    }
}
