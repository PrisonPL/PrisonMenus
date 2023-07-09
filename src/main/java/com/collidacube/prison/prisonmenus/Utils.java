package com.collidacube.prison.prisonmenus;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Utils {

    public static final String PAPI = "PlaceholderAPI";

    public static boolean isPluginEnabled(String pluginName) {
        return Bukkit.getPluginManager().isPluginEnabled(pluginName);
    }

    public static ItemStack buildFormattedItem(Player player, ItemStack template) {
        if (!isPluginEnabled(PAPI))
            return null;

        ItemStack item = template.clone();
        ItemMeta meta =  item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(formatAndColorString(player, meta.getDisplayName()));
            List<String> lines = meta.getLore();
            if (lines != null) {
                lines = lines.stream().map(s -> formatAndColorString(player, s)).toList();
                meta.setLore(lines);
            }
            item.setItemMeta(meta);
        }
        return item;
    }

    public static String formatAndColorString(Player player, String str) {
        str = ChatColor.translateAlternateColorCodes('&', str);
        return PlaceholderAPI.setPlaceholders(player, str);
    }

}
