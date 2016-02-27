package com.j0ach1mmall3.permissionsshop.listeners;

import com.j0ach1mmall3.jlib.integration.Placeholders;
import com.j0ach1mmall3.jlib.inventory.CustomEnchantment;
import com.j0ach1mmall3.jlib.inventory.CustomItem;
import com.j0ach1mmall3.jlib.inventory.GUI;
import com.j0ach1mmall3.jlib.inventory.GuiItem;
import com.j0ach1mmall3.jlib.methods.Sounds;
import com.j0ach1mmall3.permissionsshop.Main;
import com.j0ach1mmall3.permissionsshop.api.API;
import com.j0ach1mmall3.permissionsshop.api.Category;
import com.j0ach1mmall3.permissionsshop.api.CategoryPackageHolder;
import com.j0ach1mmall3.permissionsshop.api.Discount;
import com.j0ach1mmall3.permissionsshop.api.Package;
import com.j0ach1mmall3.permissionsshop.api.PathItem;
import com.j0ach1mmall3.permissionsshop.api.Sale;
import com.j0ach1mmall3.permissionsshop.api.Shop;
import com.j0ach1mmall3.permissionsshop.config.Config;
import com.j0ach1mmall3.permissionsshop.config.Lang;
import com.j0ach1mmall3.permissionsshop.config.Sales;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerListener implements Listener {
	private final Main plugin;
    private final Enchantment glow;
	private final Map<Player, PathItem> pathMap = new HashMap<>();
	public PlayerListener(Main plugin) {
		this.plugin = plugin;
        CustomEnchantment ce = new CustomEnchantment("SALESGLOW", null, null, 1, 10);
        ce.register();
        this.glow = ce.getEnchantment();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(ignoreCancelled=true)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
		Lang lang = this.plugin.getLang();
		String command = e.getMessage();
		for(Shop shop : this.plugin.getShops().getShops()) {
			if(shop.getCommand().equalsIgnoreCase(command)) {
				Player p = e.getPlayer();
				e.setCancelled(true);
				if(p.hasPermission(shop.getPermission())) {
                    if(this.plugin.getMoney(p) >= shop.getPrice()) this.openGUI(p, shop);
                    else p.sendMessage(Placeholders.parse(lang.getNotEnoughMoney(), p));
                } else p.sendMessage(Placeholders.parse(lang.getShopNoPermission(), p));
				break;
			}
		}
	}

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        this.pathMap.remove((Player) e.getPlayer());
    }

	@EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        this.pathMap.remove(e.getPlayer());
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent e) {
        this.pathMap.remove(e.getPlayer());
    }

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Config config = this.plugin.getBabies();
		API api = this.plugin.getApi();
		Player p = (Player) e.getWhoClicked();
        if(config.getConfirmGui().hasClicked(e)) {
            e.setCancelled(true);
            if(e.getSlot() == config.getConfirmItem().getPosition()) this.checkout(p, (Package) this.pathMap.get(p));
            if(e.getSlot() == config.getRefuseItem().getPosition()) {
                Sounds.playSound(p, Sound.LAVA_POP);
                p.sendMessage(Placeholders.parse(this.plugin.getLang().getRefusedPurchase(), p));
                p.closeInventory();
            }
            return;
        }
        if(this.pathMap.containsKey(p)) {
            e.setCancelled(true);
			if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
			Sounds.playSound(p, config.getClickSound());
            if(config.getReturnItem().getPosition() == e.getSlot()) {
				this.openGUI(p, this.pathMap.get(p).getParent());
				return;
			}
            PathItem pathItem = api.getPathItemByPosition(e.getSlot(), this.pathMap.get(p));
            if(pathItem == null) return;
            if(pathItem instanceof Package) this.handlePurchase(p, (Package) pathItem);
            else this.openGUI(p, (Category) pathItem);
        }
	}

    private void openGUI(Player p, CategoryPackageHolder categoryPackageHolder) {
        if(categoryPackageHolder == null) {
            p.closeInventory();
            this.pathMap.remove(p);
        } else {
            GUI gui = new GUI(Placeholders.parse(categoryPackageHolder.getGuiName(), p), categoryPackageHolder.getGuiSize());
            gui.setItems(this.getContents(p, categoryPackageHolder));
            gui.open(p);
            this.pathMap.put(p, categoryPackageHolder);
        }
    }

    private List<GuiItem> getContents(Player p, CategoryPackageHolder categoryPackageHolder) {
        List<GuiItem> guiItems = new ArrayList<>();
        for(Category category : categoryPackageHolder.getCategories()) {
            guiItems.add(category.getItem());
        }
        for(Package pckage : categoryPackageHolder.getPackages()) {
            guiItems.add(this.getItem(p, pckage));
        }
        guiItems.add(this.plugin.getBabies().getReturnItem());
        return guiItems;
    }
	
	private void handlePurchase(Player p, Package pckage) {
        Lang lang = this.plugin.getLang();
        Config config = this.plugin.getBabies();
		if(!p.hasPermission(pckage.getPermission())) {
			p.sendMessage(Placeholders.parse(lang.getPackageNoPermission(), p));
			return;
		}
		if(this.plugin.getMoney(p) < pckage.getPrice()) {
			p.sendMessage(Placeholders.parse(lang.getNotEnoughMoney(), p));
			return;
		}
		if(config.isPurchaseConfirmation()) {
            GUI confirmGui = config.getConfirmGui();
			confirmGui.setItem(13, this.getItem(p, pckage).getItem());
			confirmGui.setItem(config.getRefuseItem());
			confirmGui.setItem(config.getConfirmItem());
			confirmGui.open(p);
			this.pathMap.put(p, pckage);
		} else this.checkout(p, pckage);
	}
	
	private void checkout(Player p, Package pckage) {
		p.closeInventory();
		double price = this.calculatePrice(p, pckage);
		this.plugin.removeMoney(p, price);
		for(String command : pckage.getCommands()) {
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), Placeholders.parse(command, p));
		}
        Sounds.playSound(p, Sound.LEVEL_UP);
        p.sendMessage(Placeholders.parse(this.plugin.getLang().getSuccessfulPurchase(), p));
	}
	
	private GuiItem getItem(Player p, Package pckage) {
		API api = this.plugin.getApi();
		Sales sales = this.plugin.getSales();
		CustomItem ci = new CustomItem(pckage.getItem().getItem().clone());
		List<String> lore = ci.getItemMeta().getLore();
		lore.add(ChatColor.GRAY + "Price: " + this.calculatePrice(p, pckage));
        ci.setLore(lore);
		if(!api.getActiveSales(pckage.getShop()).isEmpty()) {
			if(sales.isSaleGlow()) ci.addEnchantment(this.glow, 1);
			ci.setName(Placeholders.parse(sales.getSalePrefix(), p) + ci.getName());
		}
		return new GuiItem(ci, pckage.getItem().getPosition());
	}
	
	private double calculatePrice(Player p, Package pckage) {
		API api = this.plugin.getApi();
		Shop shop = pckage.getShop();
		double price = pckage.getPrice();
		for(Discount d :api.getActiveDiscounts(shop)) {
			if(p.hasPermission(d.getPermission())) {
				price = price - ((d.getPercentage() / 100) * price);
				price = price - d.getAmount();
			}
		}
		for(Sale s : api.getActiveSales(shop)) {
			if(p.hasPermission(s.getPermission())) {
				price = price - ((s.getPercentage() / 100) * price);
				price = price - s.getAmount();
			}
		}
		return price;
	}
}
