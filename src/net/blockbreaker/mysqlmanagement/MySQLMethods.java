package net.blockbreaker.mysqlmanagement;

import org.bukkit.OfflinePlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Locale;

/**
 * Created by Lukas on 02.04.2015.
 */
public class MySQLMethods {

    public static void createTableIfNotExists() {
        MySQL.update("CREATE TABLE IF NOT EXISTS data(name VARCHAR(100), uuid VARCHAR(100), lastlogin VARCHAR(100), logincounter VARCHAR(100), nick BOOLEAN)");

        MySQL.update("CREATE TABLE IF NOT EXISTS rpg(name VARCHAR(100), uuid VARCHAR(100), ep INTEGER, coins INTEGER, campaignprogress INTEGER)");

        MySQL.update("CREATE TABLE IF NOT EXISTS serverdata(motd VARCHAR(100), submotd VARCHAR(100))");
    }

    public static void createData(OfflinePlayer player) {

        // allgemeine Variablen definieren
        String uuid = player.getUniqueId().toString();

        //  "data" Tabelle
        if(isInDataBase(player, "data")) {

            DateFormat dmy = DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMANY);
            String lastLoginAsString = dmy.format(System.currentTimeMillis());

            boolean nick = false;
            int logincounter = 0;

            ResultSet rs = MySQL.getResult("SELECT uuid FROM data WHERE uuid = '" + uuid + "'");
            try {
                if (!rs.next()) {
                    MySQL.update("INSERT INTO data VALUES('" + player.getName() + "', '" + uuid + "', '" + lastLoginAsString + "', '" + logincounter + "' ,nick)");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        //  "rpg" Tabelle
        if(isInDataBase(player, "rpg")) {
            int ep = 0;
            int coins = 0;
            int campaignprogress = 1; // TODO: 1 oda 0 je nach dem was f�r 1. bzw Startmission besser vom logischen Zusammenhang passt

            ResultSet sr = MySQL.getResult("SELECT uuid FROM rpg WHERE uuid = '" + uuid + "'");

            try {
                if (!sr.next()) {
                    MySQL.update("INSERT INTO rpg VALUES('" + player.getName() + "', '" + uuid + "', '" + ep + "', '" + coins + "', '" + campaignprogress + "')");
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isInDataBase(OfflinePlayer target, String database) {
        String uuid = target.getUniqueId().toString();

        boolean isInDatabase = false;

        ResultSet rs = MySQL.getResult("SELECT uuid FROM " + database + " WHERE uuid = '" + uuid + "'");

        try {
            if(!rs.next()) {
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
