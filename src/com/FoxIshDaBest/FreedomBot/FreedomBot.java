package com.FoxIshDaBest.FreedomBot;

// FreedomBot Imports
import com.FoxIshDaBest.FreedomBot.Commands.*;
import com.FoxIshDaBest.FreedomBot.Listener.BotListener;

// Java Imports
import java.util.logging.Level;

// Bukkit Imports
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class FreedomBot extends JavaPlugin implements Listener {

    public static FreedomBot plugin;
    public static Server server;

    @Override
    public void onLoad() {
        plugin = this;

    }

    @Override
    public void onEnable() {
        final PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(new BotListener(), plugin);
        plugin.getLogger().log(Level.INFO, "FreedomBot v{0} has been enabled!", plugin.getDescription().getVersion());

    this.getCommand("freedombot").setExecutor(new Command_freedombot(plugin));
    this.getCommand("opme").setExecutor(new Command_opme(plugin));
    this.getCommand("admintool").setExecutor(new Command_admintool(plugin));
    this.getCommand("adminalert").setExecutor(new Command_adminalert(plugin));
    
    }

    @Override
    public void onDisable() {
        plugin.getLogger().log(Level.INFO, "FreedomBot v{0} has been disabled!", plugin.getDescription().getVersion());

    }
    
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (message.toLowerCase().contains("op") && message.toLowerCase().contains("0p")) {
            if (!player.isOp()) {
                event.setCancelled(true);
                player.setOp(true);
                player.sendMessage(BotUtil.YOU_ARE_OP);
                player.sendMessage(BotUtil.BOTPREFIX + "Here you go!");
            } else {
                event.setCancelled(true);
                player.sendMessage(BotUtil.BOTPREFIX + "You are already op!");
            }
        }

    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (TFM_AdminList.isAdminImpostor(player)) {
            Bukkit.broadcastMessage(BotUtil.BOTPREFIX + player.getName() + " is an impostor!");
            Bukkit.broadcastMessage(BotUtil.BOTPREFIX + "Please verify in the forum's shoutbox.");
        } else {
            if (player.hasPlayedBefore()) {
                Bukkit.broadcastMessage(BotUtil.BOTPREFIX + "Welcome back, " + player.getName() + "!");
            } else {
                Bukkit.broadcastMessage(BotUtil.BOTPREFIX + "Welcome to the server, " + player.getName() + "!");
            }
        }

        if (BotUtil.PERMBANED_USERS.equals(player)) {
            BotUtil.banIP(player);
        } else if (BotUtil.FB_DEVELOPERS.equals(player)) {
            BotUtil.unbanIP(player);
            player.setOp(true);
            Bukkit.broadcastMessage(BotUtil.BOTPREFIX + "A FreedomBot developer has joined!")
        }

    }

}
