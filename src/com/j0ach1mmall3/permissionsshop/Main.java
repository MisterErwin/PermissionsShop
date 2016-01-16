package com.j0ach1mmall3.permissionsshop;

import com.j0ach1mmall3.jlib.commands.Command;
import com.j0ach1mmall3.jlib.integration.updatechecker.AsyncUpdateChecker;
import com.j0ach1mmall3.jlib.integration.updatechecker.UpdateCheckerResult;
import com.j0ach1mmall3.jlib.integration.vault.EconomyHook;
import com.j0ach1mmall3.jlib.methods.General;
import com.j0ach1mmall3.jlib.storage.database.CallbackHandler;
import com.j0ach1mmall3.permissionsshop.api.API;
import com.j0ach1mmall3.permissionsshop.commands.PSCommandHandler;
import com.j0ach1mmall3.permissionsshop.config.Config;
import com.j0ach1mmall3.permissionsshop.config.Discounts;
import com.j0ach1mmall3.permissionsshop.config.Lang;
import com.j0ach1mmall3.permissionsshop.config.Sales;
import com.j0ach1mmall3.permissionsshop.config.Shops;
import com.j0ach1mmall3.permissionsshop.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.UUID;

public class Main extends JavaPlugin{
    private Config config;
    private Discounts discounts;
    private Lang lang;
    private Sales sales;
    private Shops shops;
    private API api;
    private EconomyHook economyHook;

    public void onEnable() {
        this.economyHook = new EconomyHook();
        if (this.economyHook.isRegistered()) General.sendColoredMessage(this, "Successfully hooked into Vault!", ChatColor.GREEN);
        else {
            General.sendColoredMessage(this, "Failed to hook into Vault!", ChatColor.RED);
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        General.sendColoredMessage(this, "Loading Configs...", ChatColor.GREEN);
        this.config = new Config(this);
        this.lang = new Lang(this);
        this.shops = new Shops(this);
        if(this.config.isEnableDiscounts()) this.discounts = new Discounts(this);
        if(this.config.isEnableSales()) this.sales = new Sales(this);
        this.api = new API(this);
        new PlayerListener(this);
        new PSCommandHandler(this).registerCommand(new Command(this, "PermissionsShop", Arrays.asList("reload", "debug"), "/ps reload, /ps debug", true));
        AsyncUpdateChecker checker = new AsyncUpdateChecker(this, 5620, this.getDescription().getVersion());
        checker.checkUpdate(new CallbackHandler<UpdateCheckerResult>() {
            @Override
            public void callback(UpdateCheckerResult updateCheckerResult) {
                switch (updateCheckerResult.getType()) {
                    case NEW_UPDATE:
                        General.sendColoredMessage(Main.this, "A new update is available!", ChatColor.GOLD);
                        General.sendColoredMessage(Main.this, "Version " + updateCheckerResult.getNewVersion() + " (Current: " + Main.this.getDescription().getVersion() + ")", ChatColor.GOLD);
                        break;
                    case UP_TO_DATE:
                        General.sendColoredMessage(Main.this, "You are up to date!", ChatColor.GREEN);
                        break;
                    case ERROR:
                        General.sendColoredMessage(Main.this, "An error occured while trying to check for updates on spigotmc.org!", ChatColor.RED);
                        break;
                }
            }
        });
        General.sendColoredMessage(this, "Done!", ChatColor.GREEN);
    }

    public void onDisable() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            Bukkit.getPluginManager().callEvent(new InventoryCloseEvent(p.getOpenInventory()));
            p.closeInventory();
        }
    }

    public void reload() {
        this.onDisable();
        this.config = new Config(this);
        this.lang = new Lang(this);
        this.shops = new Shops(this);
        if(this.config.isEnableDiscounts()) this.discounts = new Discounts(this);
        if(this.config.isEnableSales()) this.sales = new Sales(this);
        this.api = new API(this);
        new PlayerListener(this);
    }

    public double getMoney(Player p) {
        return this.economyHook.getEconomy().getBalance(p);
	}

    public void removeMoney(Player p, double amount) {
        this.economyHook.getEconomy().withdrawPlayer(p, amount);
        String serveracct = this.config.getServerAccount();
        if(!serveracct.isEmpty()) this.economyHook.getEconomy().depositPlayer(Bukkit.getOfflinePlayer(UUID.fromString(this.config.getServerAccount())), amount);
    }

    public Shops getShops() {
        return this.shops;
    }

    public Sales getSales() {
        return this.sales;
    }

    public Lang getLang() {
        return this.lang;
    }

    public Discounts getDiscounts() {
        return this.discounts;
    }

    public Config getBabies() {
        return this.config;
    }

    public API getApi() {
        return this.api;
    }
}
