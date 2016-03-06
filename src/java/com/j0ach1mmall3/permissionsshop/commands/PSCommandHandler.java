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

    @Override
    public boolean handleCommand(final CommandSender commandSender, String[] strings) {
        if(strings[0].equalsIgnoreCase("reload")) {
            if(!commandSender.hasPermission("ps.reload")) {
                commandSender.sendMessage(Placeholders.parse(this.plugin.getLang().getCommandNoPermission()));
                return true;
            }
            this.plugin.reload();
            commandSender.sendMessage(ChatColor.GREEN + "All Configs reloaded!");
            return true;
        }
        if(strings[0].equalsIgnoreCase("debug")) {
            if(!commandSender.hasPermission("ps.debug")) {
                commandSender.sendMessage(Placeholders.parse(this.plugin.getLang().getCommandNoPermission()));
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
                public void callback(String o) {
                    commandSender.sendMessage(ChatColor.GREEN + "Debug dump can be found at " + ChatColor.GOLD + o);
                }
            });
            return true;
        }
        commandSender.sendMessage(ChatColor.RED + "Usage: /ps reload, /ps debug");
        return true;
    }
}
