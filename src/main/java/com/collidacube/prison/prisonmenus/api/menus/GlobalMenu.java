package com.collidacube.prison.prisonmenus.api.menus;

import com.collidacube.prison.prisonmenus.api.items.ClickableItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class GlobalMenu extends Menu {

    public GlobalMenu(Inventory inventory, List<ClickableItem> items) {
        super(inventory, items);
        formatInventory(null);
    }

    public GlobalMenu(Inventory inventory, List<ClickableItem> items, boolean otherwiseInteractive) {
        super(inventory, items, otherwiseInteractive);
        formatInventory(null);
    }

    @Override
    public Inventory formatInventory(Player player) {
        layout.forEach((slot, item) -> inventory.setItem(slot, item.getItem()));
        return inventory;
    }

    @Override
    public void openTo(Player player) {
        player.openInventory(inventory);
        MenuDelegate.subscribe(player, this);
    }

}
