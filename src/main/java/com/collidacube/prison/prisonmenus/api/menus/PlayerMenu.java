package com.collidacube.prison.prisonmenus.api.menus;

import com.collidacube.prison.prisonmenus.Utils;
import com.collidacube.prison.prisonmenus.api.items.ClickableItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class PlayerMenu extends Menu {

    private final String title;
    public PlayerMenu(String title, Inventory inventory, List<ClickableItem> items) {
        super(inventory, items);
        this.title = title;
    }
    public PlayerMenu(String title, Inventory inventory, List<ClickableItem> items, boolean otherwiseInteractive) {
        super(inventory, items, otherwiseInteractive);
        this.title = title;
    }

    @Override
    public Inventory formatInventory(Player player) {
        Inventory inv = MenuDelegate.getOpenMenu(player) == this ? player.getOpenInventory().getTopInventory() : Utils.clone(inventory, title);
        layout.forEach((slot, item) -> inv.setItem(slot, item.getPlayerBasedItem(player)));
        return inv;
    }

    @Override
    public void openTo(Player player) {
        player.openInventory(formatInventory(player));
        MenuDelegate.subscribe(player, this);
    }

}
