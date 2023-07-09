package com.collidacube.prison.prisonmenus.api.items;

import com.collidacube.prison.prisonmenus.Utils;
import com.collidacube.prison.prisonmenus.api.menus.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class BasicItem implements IClickableItem {

    private final ItemStack item;
    private final boolean stealable;
    public BasicItem(ItemStack item, boolean stealable) {
        this.item = item;
        this.stealable = stealable;
    }

    @Override
    public ItemStack getItem(Player viewer) {
        return Utils.buildFormattedItem(viewer, item);
    }

    @Override
    public void onClick(InventoryClickEvent event, Menu menu, Player viewer) {
        if (!stealable) event.setCancelled(true);
    }

}
