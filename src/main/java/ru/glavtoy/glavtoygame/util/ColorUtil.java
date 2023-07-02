package ru.glavtoy.glavtoygame.util;

import org.bukkit.ChatColor;

public class ColorUtil {

    public static String msg(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
