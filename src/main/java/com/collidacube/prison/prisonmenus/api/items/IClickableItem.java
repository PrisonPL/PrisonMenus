package com.collidacube.prison.prisonmenus.api.items;

import com.collidacube.prison.prisonmenus.api.menus.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface IClickableItem {
    ItemStack getItem(Player viewer);
    void onClick(InventoryClickEvent event, Menu menu, Player viewer);
}
