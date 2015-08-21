package com.j0ach1mmall3.permissionsshop.listeners;

import com.j0ach1mmall3.jlib.methods.General;
import com.j0ach1mmall3.jlib.methods.Parsing;
import com.j0ach1mmall3.jlib.methods.Placeholders;
import com.j0ach1mmall3.jlib.objects.CustomEnchantment;
import com.j0ach1mmall3.jlib.objects.CustomItem;
import com.j0ach1mmall3.jlib.objects.GUI;
import com.j0ach1mmall3.permissionsshop.Main;
import com.j0ach1mmall3.permissionsshop.api.*;
import com.j0ach1mmall3.permissionsshop.api.Package;
import com.j0ach1mmall3.permissionsshop.config.Config;
import com.j0ach1mmall3.permissionsshop.config.Lang;
import com.j0ach1mmall3.permissionsshop.config.Sales;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerListener implements Listener {
	private Main plugin;
    private Config config;
    private Lang lang;
    private Sales sales;
    private GUI shopGui;
    private GUI categoryGui;
    private GUI confirmGui;
    private Enchantment glow;
	private HashMap<Player, Shop> shopMap = new HashMap<Player, Shop>();
	private HashMap<Player, Category> categoryMap = new HashMap<Player, Category>();
	private HashMap<Player, Package> packageMap = new HashMap<Player, Package>();
	public PlayerListener(Main plugin){
		this.plugin = plugin;
        this.config = plugin.getBabies();
        this.lang = plugin.getLang();
        this.sales = plugin.getSales();
        CustomEnchantment ce = new CustomEnchantment("GLOW", null, null, 1, 10);
        ce.register();
        this.glow = ce.getEnchantment();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(ignoreCancelled=true)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e){
		String command = e.getMessage();
		Shop shop = null;
		for(Shop shopp : API.getShops()){
			if(shopp.getCommand().equalsIgnoreCase(command)){
				shop = shopp;
			}
		}
		if(shop != null){
			Player p = e.getPlayer();
			e.setCancelled(true);
			if(p.hasPermission(shop.getPermission())){
				openGUI(p, shop);
			} else {
				p.sendMessage(Placeholders.parse(lang.getShopNoPermission(), p));
			}
		}
	}
	
	private void openGUI(Player p, Shop shop){
        shopGui = new GUI(Placeholders.parse(shop.getGuiName(), p), getShopContents(p, shop));
        shopGui.open(p);
		shopMap.put(p, shop);
	}
	
	private ItemStack[] getShopContents(Player p, Shop shop){
		List<ItemStack> categories = new ArrayList<ItemStack>();
		for(Category c : shop.getCategories()){
			categories.add(c.getItem());
		}
		List<ItemStack> packages = new ArrayList<ItemStack>();
		for(Package pckage : shop.getPackages()){
			packages.add(getItem(p, shop, pckage));
		}
		List<ItemStack> contents = new ArrayList<ItemStack>(categories);
		contents.addAll(packages);
		ItemStack[] finalContents = new ItemStack[contents.size()];
		for(int a=0;a<contents.size();a++){
			finalContents[a] = contents.get(a);
		}
		return finalContents;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
        if(confirmGui != null) {
            if(confirmGui.hasClicked(e)){
                e.setCancelled(true);
                if(e.getSlot() == 10) checkout(p, packageMap.get(p));
                if(e.getSlot() == 16){
                    General.playSound(p, Sound.LAVA_POP);
                    p.sendMessage(Placeholders.parse(lang.getRefusedPurchase(), p));
                    p.closeInventory();
                }
                return;
            }
        }
		if(shopMap.containsKey(p)){
			Shop shop = shopMap.get(p);
            if(shopGui != null) {
                if(shopGui.hasClicked(e)){
                    e.setCancelled(true);
                    if(categoryMap.containsKey(p)){
                        Category category = categoryMap.get(p);
                        if(e.getSlot() == category.getPackages().size()){
                            General.playSound(p, Sound.CLICK);
                            categoryMap.remove(p);
                            openGUI(p, shop);
                        } else {
                            General.playSound(p, Sound.CLICK);
                            handlePurchase(p, category.getPackages().get(e.getSlot()));
                        }
                    } else {
                        if(e.getSlot() < shop.getCategories().size()){
                            Category c = shop.getCategories().get(e.getSlot());
                            if(p.hasPermission(c.getPermission())) {
                                General.playSound(p, Sound.CLICK);
                                openCategory(p, shop, c);
                            } else {
                                p.sendMessage(Placeholders.parse(lang.getCategoryNoPermission(), p));
                            }
                        } else {
                            General.playSound(p, Sound.CLICK);
                            handlePurchase(p, shop.getPackages().get(e.getSlot() - shop.getCategories().size()));
                        }
                    }
                }
            }
		}
	}
	
	private void openCategory(Player p, Shop shop, Category category){
        categoryGui = new GUI(Placeholders.parse(shop.getGuiName(), p), getCategoryContents(p, shop, category));
		categoryGui.open(p);
		categoryMap.put(p, category);
	}
	
	private ItemStack[] getCategoryContents(Player p, Shop shop, Category category){
		ItemStack[] packages = new ItemStack[category.getPackages().size()+1];
		for(int a=0;a<category.getPackages().size();a++){
			packages[a] = getItem(p, shop, category.getPackages().get(a));
		}
		packages[packages.length-1] = new CustomItem(Parsing.parseMaterial(config.getReturnItemItem()), 1, Parsing.parseData(config.getReturnItemItem()), Placeholders.parse(config.getReturnItemName(), p), Placeholders.parse(config.getReturnItemDescription(), p));
		return packages;
	}
	
	private void handlePurchase(Player p, Package pckage){
		p.closeInventory();
		if(!p.hasPermission(pckage.getPermission())){
			p.sendMessage(Placeholders.parse(lang.getPackageNoPermission(), p));
			return;
		}
		if(plugin.getMoney(p) < pckage.getPrice()){
			p.sendMessage(Placeholders.parse(lang.getNotEnoughMoney(), p));
			return;
		}
		if(config.isPurchaseConfirmation()){
			ItemStack confirmItem = new CustomItem(Parsing.parseMaterial(config.getConfirmItemItem()), 1, Parsing.parseData(config.getConfirmItemItem()), Placeholders.parse(config.getConfirmItemName(), p), Placeholders.parse(config.getConfirmItemDescription(), p));
			ItemStack refuseItem = new CustomItem(Parsing.parseMaterial(config.getRefuseItemItem()), 1, Parsing.parseData(config.getRefuseItemItem()), Placeholders.parse(config.getRefuseItemName(), p), Placeholders.parse(config.getRefuseItemDescription(), p));
			ItemStack[] contents = new ItemStack[]{
					null, null, null, null, null, null, null, null, null, 
					null, confirmItem, null, null, pckage.getItem(), null, null, refuseItem, null, 
					null, null, null, null, null, null, null, null, null};
			confirmGui = new GUI(Placeholders.parse(lang.getConfirmGuiName(), p), contents);
            confirmGui.open(p);
			packageMap.put(p, pckage);
		} else {
			checkout(p, pckage);
		}
	}
	
	private void checkout(Player p, Package pckage){
        General.playSound(p, Sound.ORB_PICKUP);
		p.closeInventory();
		plugin.removeMoney(p, calculatePrice(p, shopMap.get(p), pckage));
		shopMap.remove(p);
		categoryMap.remove(p);
		packageMap.remove(p);
		for(String command : pckage.getCommands()){
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), Placeholders.parse(command, p));
		}
        General.playSound(p, Sound.LEVEL_UP);
        p.sendMessage(Placeholders.parse(lang.getSuccessfulPurchase(), p));
	}
	
	private CustomItem getItem(Player p, Shop shop, Package pckage){
		CustomItem ci = pckage.getItem();
		List<String> lore = ci.getItemMeta().getLore();
		if(!lore.get(lore.size()-1).contains("Price:")){
			lore.add(ChatColor.GRAY + "Price: " + calculatePrice(p, shop, pckage));
			ci.setLore(lore);
		}
		if(!API.getActiveSales(shop).isEmpty()){
			if(sales.isSaleGlow()) ci.addEnchantment(glow, 1);
			String name = ci.getItemMeta().getDisplayName();
			if(!name.startsWith(Placeholders.parse(sales.getSalePrefix(), p))) ci.setName(sales.getSalePrefix() + name);
		}
		return ci;
	}
	
	private double calculatePrice(Player p, Shop shop, Package pckage){
		double price = pckage.getPrice();
		for(Discount d : API.getActiveDiscounts(shop)){
			if(p.hasPermission(d.getPermission())){
				price = price - ((d.getPercentage()/100)*price);
				price = price - d.getAmount();
			}
		}
		for(Sale s : API.getActiveSales(shop)){
			if(p.hasPermission(s.getPermission())){
				price = price - ((s.getPercentage()/100)*price);
				price = price - s.getAmount();
			}
		}
		return price;
	}
}
