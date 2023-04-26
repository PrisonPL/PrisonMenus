package com.collidacube.prison.prisonmenus.api.items;

import com.collidacube.prison.prisonmenus.Utils;
import com.collidacube.prison.prisonmenus.api.menus.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface ClickableItem {

    int getSlot();
    ItemStack getItem();
    void onClick(InventoryClickEvent event, Menu menu, Player player);
    boolean stealable();
    default ItemStack getPlayerBasedItem(Player player) {
        return player == null ? getItem() : Utils.buildFormattedItem(player, getItem());
    }

}