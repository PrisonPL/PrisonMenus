package com.collidacube.prison.prisonmenus;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class Utils {

    public static Inventory clone(Inventory inventory, String title) {
        Inventory clone = null;
        if (inventory.getType() == InventoryType.CHEST)
            clone = Bukkit.createInventory(inventory.getHolder(), inventory.getSize(), title);
        else clone = Bukkit.createInventory(inventory.getHolder(), inventory.getType(), title);

        clone.setContents(inventory.getContents());

        return clone;
    }

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
            formatString(player, meta, ItemMeta::getDisplayName, ItemMeta::setDisplayName);
            List<String> lines = meta.getLore();
            if (lines != null) {
                lines = lines.stream().map(s -> formatString(player, s)).toList();
                meta.setLore(lines);
            }
            item.setItemMeta(meta);
        }
        return item;
    }

    public static String formatString(Player player, String str) {
        str = ChatColor.translateAlternateColorCodes('&', str);
        return PlaceholderAPI.setPlaceholders(player, str);
    }
    public static <T> void formatString(Player player, T obj, Function<T, String> getter, BiConsumer<T, String> setter) {
        setter.accept(obj, formatString(player, getter.apply(obj)));
    }

}
