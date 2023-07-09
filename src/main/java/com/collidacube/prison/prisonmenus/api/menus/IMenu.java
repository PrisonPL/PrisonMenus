package com.collidacube.prison.prisonmenus.api.menus;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface IMenu {
    ItemStack[] getItems(Player player);
    InventoryType getInventoryType(Player player);
    int getInventorySize(Player player);
    String getInventoryTitle(Player player);
    void onClick(InventoryClickEvent event, Player player);
    List<Player> getViewers();
    default void render(Player player) { render(player, new String[0]); }
    void render(Player player, String... args);
    boolean onClose(InventoryCloseEvent event, Player player);
}
