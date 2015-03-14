package me.Bahamut.DragonEggEnchanting;

import org.bukkit.ChatColor;

/**
 * Created by yunjang on 3/14/15.
 */
public class BetterEnchantingColor
{
    public static String green (String str)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.GREEN);
        sb.append(str);
        sb.append(ChatColor.RESET);
        return sb.toString();
    }

    public static String gold (String str)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.GOLD);
        sb.append(str);
        sb.append(ChatColor.RESET);
        return sb.toString();
    }

    public static String aqua (String str)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.AQUA);
        sb.append(str);
        sb.append(ChatColor.RESET);
        return sb.toString();
    }
}
