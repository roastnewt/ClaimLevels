package com.github.xCyanide;

import com.github.xCyanide.Utils.Lang;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import com.github.xCyanide.Utils.DataManager;

public class PlayerListener implements Listener {

    @SuppressWarnings("unused")
    private ClaimLevels plugin;
    DataManager dm = DataManager.getInstance();

    public PlayerListener(ClaimLevels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if(!player.hasPlayedBefore()) {
            int startupAmount = dm.getData().getInt("startAmount");
            if(startupAmount > 0) {
                dm.getData().set(player.getUniqueId().toString() + ".credits", startupAmount);
                dm.saveData();
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        int levels = dm.getData().getInt(player.getUniqueId().toString() + ".credits");
        if(levels > 0) {
            player.sendMessage(Lang.PREFIX.toString() + ChatColor.AQUA + "You have " + ChatColor.DARK_AQUA + levels + ChatColor.AQUA + " level(s) to redeem on a mcMMO skill");
            if(!dm.getData().contains(player.getUniqueId().toString())) {
                dm.getData().set(player.getUniqueId().toString() + ".credits", levels);
            }
        }
    }
}