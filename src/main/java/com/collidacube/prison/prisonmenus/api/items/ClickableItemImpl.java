package com.collidacube.prison.prisonmenus.api.items;

import com.collidacube.prison.prisonmenus.api.menus.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ClickableItemImpl implements ClickableItem {

    protected final int slot;
    protected final ItemStack item;
    protected final boolean stealable;
    public ClickableItemImpl(int slot, ItemStack item) {
        this(slot, item, false);
    }

    public ClickableItemImpl(int slot, ItemStack item, boolean stealable) {
        this.slot = slot;
        this.item = item;
        this.stealable = stealable;
    }

    @Override
    public int getSlot() {
        return slot;
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public boolean stealable() {
        return stealable;
    }

    @Override
    public void onClick(InventoryClickEvent event, Menu menu, Player player) {
        event.setCancelled(!stealable());
    }

}