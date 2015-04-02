package net.blockbreaker.mysqlmanagement;

import org.bukkit.OfflinePlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * Created by Lukas on 02.04.2015.
 */
public class MySQLMethods {

    public static void createTableIfNotExists() {
        MySQL.update("CREATE TABLE IF NOT EXISTS data (playername VARCHAR(100), uuid VARCHAR(100), nick BOOLEAN, lastlogin DATE, end VARCHAR(100), reason VARCHAR(100)");

        MySQL.update("CREATE TABLE IF NOT EXISTS rpg (playername VARCHAR(100), uuid VARCHAR(100), ep INTEGER, coins INTEGER, campaignprogress INTEGER)");
    }

    public static void createData(OfflinePlayer player) {

        // allgemeine Variablen definieren
        String uuid = player.getUniqueId().toString();

        //  "data" Tabelle

        boolean nick = false;

        SimpleDateFormat lastlogin = new SimpleDateFormat("yyyy.mm.dd");


        if(!isInDataBase(player)) {
            ResultSet rs = MySQL.getResult("SELECT uuid FROM data WHERE uuid = '" + uuid + "'");

            try {
                if (!rs.next()) {
                    MySQL.update("INSERT INTO data VALUES('" + player.getName() + "', '" + uuid + "', '" + nick + "', '" + lastlogin + "'"); // daaaaaaaaaaaaaa
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Tabelle zwei
            ResultSet sr = MySQL.getResult("SELECT uuid FROM data WHERE uuid = '" + uuid + "'");

            try {
                if (!sr.next()) {
                    MySQL.update("INSERT INTO data VALUES('" + player.getName() + "', '" + uuid + "', '" + ep + "', '" + coins + "', '" + campaignprogress + "', false)");
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        //int bla bla

        if(!isInDataBase(player)) {

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
