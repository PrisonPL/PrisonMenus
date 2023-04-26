package com.collidacube.prison.prisonmenus.api.menus;

import com.collidacube.prison.prisonmenus.api.items.ClickableItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.List;

public abstract class Menu {

    protected final HashMap<Integer, ClickableItem> layout = new HashMap<>();
    protected final Inventory inventory;
    protected boolean otherwiseInteractive = false;
    public Menu(Inventory inventory, List<ClickableItem> items) {
        items.forEach(item -> layout.put(item.getSlot(), item));
        this.inventory = inventory;
    }
    public Menu(Inventory inventory, List<ClickableItem> items, boolean otherwiseInteractive) {
        items.forEach(item -> layout.put(item.getSlot(), item));
        this.inventory = inventory;
        this.otherwiseInteractive = otherwiseInteractive;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void onClick(InventoryClickEvent event) {
        if (!otherwiseInteractive)
            event.setCancelled(true);

        if (event.getWhoClicked() instanceof Player player) {
            if (event.getClickedInventory() != event.getInventory()) return;
            int slot = event.getSlot();
            ClickableItem item = layout.get(slot);
            if (item != null)
                item.onClick(event, this, player);
        }
    }

    public abstract Inventory formatInventory(Player player);
    public abstract void openTo(Player player);

}
