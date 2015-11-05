package com.j0ach1mmall3.permissionsshop.commands;

import com.j0ach1mmall3.jlib.commands.CommandHandler;
import com.j0ach1mmall3.permissionsshop.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * @author j0ach1mmall3 (business.j0ach1mmall3@gmail.com)
 * @since 5/11/2015
 */
public class PSCommandHandler extends CommandHandler {
    private final Main plugin;
    public PSCommandHandler(Main plugin) {
        this.plugin = plugin;
    }

    public boolean handleCommand(CommandSender sender, String[] args) {
        if(args[0].equalsIgnoreCase("reload")){
            plugin.reload();
            sender.sendMessage(ChatColor.GREEN + "All Configs reloaded!");
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "Usage: /ps reload");
            return true;
        }
    }
}
