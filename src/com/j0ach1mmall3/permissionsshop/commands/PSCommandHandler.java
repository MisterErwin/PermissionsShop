package com.j0ach1mmall3.permissionsshop.commands;

import com.j0ach1mmall3.jlib.commands.CommandHandler;
import com.j0ach1mmall3.jlib.integration.Placeholders;
import com.j0ach1mmall3.jlib.logging.JLogger;
import com.j0ach1mmall3.jlib.storage.StorageAction;
import com.j0ach1mmall3.jlib.storage.database.CallbackHandler;
import com.j0ach1mmall3.jlib.storage.file.yaml.ConfigLoader;
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

    public boolean handleCommand(final CommandSender sender, String[] args) {
        if(args[0].equalsIgnoreCase("reload")) {
            if(!sender.hasPermission("ps.reload")) {
                sender.sendMessage(Placeholders.parse(this.plugin.getLang().getCommandNoPermission()));
                return true;
            }
            this.plugin.reload();
            sender.sendMessage(ChatColor.GREEN + "All Configs reloaded!");
            return true;
        }
        if(args[0].equalsIgnoreCase("debug")) {
            if(!sender.hasPermission("ps.debug")) {
                sender.sendMessage(Placeholders.parse(this.plugin.getLang().getCommandNoPermission()));
                return true;
            }
            new JLogger(this.plugin).dumpDebug(new StorageAction[0], new ConfigLoader[]{
                    this.plugin.getBabies(),
                    this.plugin.getLang(),
                    this.plugin.getShops(),
                    this.plugin.getDiscounts(),
                    this.plugin.getSales(),
            }, new CallbackHandler<String>() {
                @Override
                public void callback(String s) {
                    sender.sendMessage(ChatColor.GREEN + "Debug dump can be found at " + ChatColor.GOLD + s);
                }
            });
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Usage: /ps reload, /ps debug");
        return true;
    }
}
