package com.j0ach1mmall3.permissionsshop.commands;

import com.j0ach1mmall3.jlib.methods.Placeholders;
import com.j0ach1mmall3.permissionsshop.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * Created by j0ach1mmall3 on 9:13 26/08/2015 using IntelliJ IDEA.
 */
public class Commands implements CommandExecutor {
    private Main plugin;
    public Commands(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(cmd.getName().equalsIgnoreCase("PermissionsShop")){
            if(sender instanceof Player){
                Player p = (Player)sender;
                if(args.length == 0){
                    p.sendMessage(ChatColor.RED + "Usage: /PermissionsShop reload");
                    return true;
                }
                if(args[0].equalsIgnoreCase("reload")){
                    if(!p.hasPermission("permissionsshop.reload")){
                        p.sendMessage(Placeholders.parse(plugin.getLang().getCommandNoPermission(), p));
                        return true;
                    }
                    plugin.reload();
                    p.sendMessage(ChatColor.GREEN + "All Configs reloaded!");
                    return true;
                }
            } else {
                ConsoleCommandSender c = (ConsoleCommandSender)sender;
                if(args.length == 0){
                    c.sendMessage(ChatColor.RED + "Usage: /PermissionsShop reload");
                    return true;
                }
                if(args[0].equalsIgnoreCase("reload")){
                    plugin.reload();
                    c.sendMessage(ChatColor.GREEN + "All Configs reloaded!");
                    return true;
                }
            }
        }
        return true;
    }
}
