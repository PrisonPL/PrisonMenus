package com.collidacube.prison.prisonmenus.api.menus;

import com.collidacube.prison.prisonmenus.PrisonMenus;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public final class MenuDelegate implements Listener {
    private MenuDelegate() {}

    public static void init(PrisonMenus plugin) {
        Bukkit.getPluginManager().registerEvents(new MenuDelegate(), plugin);
    }

    private static final HashMap<Player, Menu> openMenus = new HashMap<>();
    public static void subscribe(Player player, Menu menu) {
        openMenus.put(player, menu);
    }
    public static Menu getOpenMenu(Player player) {
        return openMenus.get(player);
    }
    public static List<Player> getViewers(Menu menu) {
        return openMenus.entrySet()
                .stream()
                .filter(e -> e.getValue() == menu)
                .map(Entry::getKey)
                .toList();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        openMenus.remove(event.getPlayer());
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        openMenus.remove((Player) event.getPlayer());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            Menu menu = openMenus.get(player);
            if (menu != null) menu.onClick(event);
        }
    }

}
