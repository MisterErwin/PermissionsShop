package com.j0ach1mmall3.permissionsshop;

import com.j0ach1mmall3.jlib.methods.General;
import com.j0ach1mmall3.jlib.objects.UpdateChecker;
import com.j0ach1mmall3.permissionsshop.api.API;
import com.j0ach1mmall3.permissionsshop.commands.Commands;
import com.j0ach1mmall3.permissionsshop.config.*;
import com.j0ach1mmall3.permissionsshop.listeners.PlayerListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
    private Economy economy = null;
    private Config config;
    private Discounts discounts;
    private Lang lang;
    private Sales sales;
    private Shops shops;
	public void onEnable(){
		General.sendColoredMessage(this, "Loading Configs...", ChatColor.GREEN);
        loadConfigs();
		General.sendColoredMessage(this, "Registering Command...", ChatColor.GREEN);
		getCommand("PermissionsShop").setExecutor(new Commands(this));
		new API(this);
		new PlayerListener(this);
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
        	General.sendColoredMessage(this, "Successfully hooked into Vault!", ChatColor.GREEN);
            economy = economyProvider.getProvider();
        } else {
        	General.sendColoredMessage(this, "Failed to hook into Vault!", ChatColor.RED);
        	Bukkit.getPluginManager().disablePlugin(this);
        }
        UpdateChecker checker = new UpdateChecker(5620, getDescription().getVersion());
		if(checker.checkUpdate()){
			General.sendColoredMessage(this, "A new update is available!" + checker.getVersion(), ChatColor.GOLD);
            General.sendColoredMessage(this, "Version " + checker.getVersion() + " (Current: " + getDescription().getVersion() + ")", ChatColor.GOLD);
        } else {
			General.sendColoredMessage(this, "You are up to date!", ChatColor.GREEN);
		}
		General.sendColoredMessage(this, "Done!", ChatColor.GREEN);
	}
	
	public void onDisable(){
		for(Player p : Bukkit.getOnlinePlayers()){
			p.closeInventory();
		}
	}
	
	public double getMoney(Player p){
		return economy.getBalance(p);
	}
	
	public void removeMoney(Player p, double amount){
		economy.withdrawPlayer(p, amount);
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

    public void loadConfigs() {
        config = new Config(this);
        lang = new Lang(this);
        shops = new Shops(this);
        if(config.isEnableDiscounts()) discounts = new Discounts(this);
        if(config.isEnableSales()) sales = new Sales(this);
    }
}
