package net.blockbreaker.mysqlmanagement;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Lukas on 02.04.2015.
 */
public class Main extends JavaPlugin {

    public static String consolePrefix = "[MySQL] ";

    @Override
    public void onEnable() {
        //Enable Message
        Server server = Bukkit.getServer();
        ConsoleCommandSender console = server.getConsoleSender();

        console.sendMessage(" ");
        console.sendMessage(ChatColor.BLUE + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        console.sendMessage(" ");
        console.sendMessage(ChatColor.AQUA + getDescription().getName());
        console.sendMessage(ChatColor.AQUA + "Version: " + getDescription().getVersion());
        console.sendMessage(" ");
        console.sendMessage(ChatColor.BLUE + "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        console.sendMessage(" ");


        MySQLFile file = new MySQLFile();
        file.setStandard();
        file.readData();

        MySQL.connect();
        MySQLMethods.createTableIfNotExists();

        new JoinEvent_Listener(this);
    }

    @Override
    public void onDisable() {
        MySQL.disconnect();
    }
}
