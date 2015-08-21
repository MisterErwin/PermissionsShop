package com.j0ach1mmall3.permissionsshop.commands;

import com.j0ach1mmall3.jlib.methods.Placeholders;
import com.j0ach1mmall3.permissionsshop.Main;
import com.j0ach1mmall3.permissionsshop.config.Lang;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
	private Main plugin;
    private Lang lang;
	public Commands(Main plugin) {
        this.plugin = plugin;
        this.lang = plugin.getLang();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("PermissionsShop")){
			if(sender instanceof Player){
				Player p = (Player)sender;
		        if(args.length == 0){
		        	p.sendMessage("§cUsage: /PermissionsShop reload");
		        	return true;
		        }
		        if(args[0].equalsIgnoreCase("reload")){
		        	if(!p.hasPermission("permissionsshop.reload")){
		        		p.sendMessage(Placeholders.parse(lang.getCommandNoPermission(), p));
		        		return true;
		        	}
		        	plugin.loadConfigs();
		        	p.sendMessage("§2All Configs reloaded!");
		        	return true;
		        }
			} else {
				ConsoleCommandSender c = (ConsoleCommandSender)sender;
		        if(args.length == 0){
		        	c.sendMessage(ChatColor.RED + "Usage: /PermissionsShop reload");
		        	return true;
		        }
		        if(args[0].equalsIgnoreCase("reload")){
                    plugin.loadConfigs();
		        	c.sendMessage(ChatColor.DARK_GREEN + "All Configs reloaded!");
		        	return true;
		        }
			}
		}
		return true;
	}
}
