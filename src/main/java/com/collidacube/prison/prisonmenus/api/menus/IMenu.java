package com.collidacube.prison.prisonmenus.api.menus;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public interface IMenu {
    ItemStack[] getItems(Player player);
    InventoryType getInventoryType(Player player);
    int getInventorySize(Player player);
    String getInventoryTitle(Player player);
    void onClick(InventoryClickEvent event, Player player);
}
