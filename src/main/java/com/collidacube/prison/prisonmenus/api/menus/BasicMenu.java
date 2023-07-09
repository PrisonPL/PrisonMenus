package com.collidacube.prison.prisonmenus.api.menus;

import com.collidacube.prison.prisonmenus.api.items.BasicItem;
import com.collidacube.prison.prisonmenus.api.items.IClickableItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class BasicMenu extends Menu {

    protected static final IClickableItem EMPTY_SLOT = new BasicItem(new ItemStack(Material.AIR), false);

    protected final InventoryType type;
    protected final int size;
    protected final IClickableItem[] contents;
    protected final String title;
    protected final IClickableItem defaultItem;
    public BasicMenu(String title, InventoryType type, IClickableItem... contents) {
        this(title, type, type.getDefaultSize(), EMPTY_SLOT, contents);
    }

    public BasicMenu(String title, int rows, IClickableItem... contents) {
        this(title, InventoryType.CHEST, rows*9, EMPTY_SLOT, contents);
    }

    public BasicMenu(String title, InventoryType type, IClickableItem defaultItem, IClickableItem... contents) {
        this(title, type, type.getDefaultSize(), defaultItem, contents);
    }

    public BasicMenu(String title, int rows, IClickableItem defaultItem, IClickableItem... contents) {
        this(title, InventoryType.CHEST, rows*9, defaultItem, contents);
    }

    protected BasicMenu(String title, InventoryType type, int size, IClickableItem defaultItem, IClickableItem... contents) {
        this.title = title;
        this.type = type;
        this.size = size;
        this.contents = contents;
        this.defaultItem = defaultItem == null ? EMPTY_SLOT : defaultItem;
    }

    public void updateAllInventories() {
        getViewers().forEach(this::render);
    }

    public IClickableItem getItemAt(int slot) {
        return contents[slot] == null ? defaultItem : contents[slot];
    }

    @Override
    public ItemStack[] getItems(Player player) {
        ItemStack[] items = new ItemStack[size];
        for (int i = 0; i < size; i++)
            items[i] = getItemAt(i).getItem(player);
        return items;
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
        getItemAt(slot).onClick(event, this, player);
    }

}
