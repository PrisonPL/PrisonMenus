package com.collidacube.prison.prisonmenus.api.menus;

import com.collidacube.prison.prisonmenus.api.items.IClickableItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class BasicMenu extends Menu {

    protected final InventoryType type;
    protected final int size;
    protected final IClickableItem[] contents;
    protected final String title;
    public BasicMenu(String title, InventoryType type, IClickableItem[] contents) {
        this(title, type, type.getDefaultSize(), contents);
    }

    public BasicMenu(String title, int rows, IClickableItem[] contents) {
        this(title, InventoryType.CHEST, rows*9, contents);
    }

    protected BasicMenu(String title, InventoryType type, int size, IClickableItem[] contents) {
        this.title = title;
        this.type = type;
        this.size = size;
        this.contents = contents;
    }

    public void updateAllInventories() {
        getViewers().forEach(this::render);
    }

    @Override
    public ItemStack[] getItems(Player player) {
        return Arrays.stream(contents)
                .map(i -> i == null ? null : i.getItem(player))
                .toArray(ItemStack[]::new);
    }

    @Override
    public InventoryType getInventoryType(Player player) {
        return type;
    }

    @Override
    public int getInventorySize(Player player) {
        return size;
    }

    @Override
    public String getInventoryTitle(Player player) {
        return title;
    }

    public void onClick(InventoryClickEvent event, Player player) {
        int slot = event.getSlot();
        if (slot == -999) return; // clicked outside of inventory
        if (event.getClickedInventory() != event.getInventory()) return; // clicked bottom inventory

        IClickableItem item = contents[slot];
        if (item != null)
            item.onClick(event, this, player);
    }

}
