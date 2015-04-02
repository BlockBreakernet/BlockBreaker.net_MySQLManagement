package net.blockbreaker.mysqlmanagement;

import org.bukkit.OfflinePlayer;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Lukas on 02.04.2015.
 */
public class MySQLMethods {

    public static void createTableIfNotExists() {
        MySQL.update("CREATE TABLE IF NOT EXISTS data (playername VARCHAR(100), uuid VARCHAR(100), ep INTEGER, coins INTEGER, campaignprogress INTEGER, nick BOOLEAN)");
    }

    public static void createData(OfflinePlayer player) {
        String uuid = player.getUniqueId().toString();

        int ep = 0;
        int coins = 0;
        int campaignprogress = 0;

        if(!isInDataBase(player)) {
            ResultSet rs = MySQL.getResult("SELECT uuid FROM data WHERE uuid = '" + uuid + "'");

            try {
                if (!rs.next()) {
                    MySQL.update("INSERT INTO data VALUES('" + player.getName() + "', '" + uuid + "', '" + ep + "', '" + coins + "', '" + campaignprogress + "', false)");
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isInDataBase(OfflinePlayer target) {
        String uuid = target.getUniqueId().toString();

        boolean isInDatabase = false;

        ResultSet rs = MySQL.getResult("SELECT uuid FROM data WHERE uuid = '" + uuid + "'");

        try {
            if(rs.next()) {
                isInDatabase = true;
            } else {
                isInDatabase = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Boolean.valueOf(isInDatabase).booleanValue();
    }
}
