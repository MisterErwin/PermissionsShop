package com.j0ach1mmall3.permissionsshop;

import com.j0ach1mmall3.jlib.commands.Command;
import com.j0ach1mmall3.jlib.integration.updatechecker.AsyncUpdateChecker;
import com.j0ach1mmall3.jlib.integration.vault.EconomyHook;
import com.j0ach1mmall3.jlib.methods.General;
import com.j0ach1mmall3.permissionsshop.api.API;
import com.j0ach1mmall3.permissionsshop.commands.PSCommandHandler;
import com.j0ach1mmall3.permissionsshop.config.*;
import com.j0ach1mmall3.permissionsshop.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;

public class Main extends JavaPlugin{
    private Config config;
    private Discounts discounts;
    private Lang lang;
    private Sales sales;
    private Shops shops;
    public void onEnable(){
        if (new EconomyHook().isRegistered()) {
            General.sendColoredMessage(this, "Successfully hooked into Vault!", ChatColor.GREEN);
        } else {
            General.sendColoredMessage(this, "Failed to hook into Vault!", ChatColor.RED);
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        General.sendColoredMessage(this, "Loading Configs...", ChatColor.GREEN);
        config = new Config(this);
        lang = new Lang(this);
        shops = new Shops(this);
        if(config.isEnableDiscounts()) discounts = new Discounts(this);
        if(config.isEnableSales()) sales = new Sales(this);
        new API(this);
        new PlayerListener(this);
        new PSCommandHandler(this).registerCommand(new Command(this, "PermissionsShop", "ps.reload", Collections.singletonList("reload"), "/ps reload"));
        AsyncUpdateChecker checker = new AsyncUpdateChecker(this, 5620, getDescription().getVersion());
        checker.checkUpdate(updateCheckerResult -> {
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
        });
        General.sendColoredMessage(this, "Done!", ChatColor.GREEN);
    }

    public void onDisable(){
        Bukkit.getOnlinePlayers().forEach(org.bukkit.entity.Player::closeInventory);
    }

    public void reload() {
        Bukkit.getOnlinePlayers().forEach(org.bukkit.entity.Player::closeInventory);
        config = new Config(this);
        lang = new Lang(this);
        shops = new Shops(this);
        if(config.isEnableDiscounts()) discounts = new Discounts(this);
        if(config.isEnableSales()) sales = new Sales(this);
        new API(this);
        new PlayerListener(this);
    }

    public double getMoney(Player p){
        return new EconomyHook().getEconomy().getBalance(p);
	}

    public void removeMoney(Player p, double amount){
        new EconomyHook().getEconomy().withdrawPlayer(p, amount);
	}

    public Shops getShops() {
        return shops;
    }

    public Sales getSales() {
        return sales;
    }

    public Lang getLang() {
        return lang;
    }

    public Discounts getDiscounts() {
        return discounts;
    }

    public Config getBabies() {
        return config;
    }
}
