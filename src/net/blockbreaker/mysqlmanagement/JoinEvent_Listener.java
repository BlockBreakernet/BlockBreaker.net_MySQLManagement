package net.blockbreaker.mysqlmanagement;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Lukas on 02.04.2015.
 */
public class JoinEvent_Listener implements Listener {

    private final Main plugin;

    public JoinEvent_Listener(Main main) {
        this.plugin = main;
        plugin.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                MySQLMethods.createData(e.getPlayer());
            }
        }, 2 * 20);
    }
}
