package com.collidacube.prison.prisonmenus.api.items;

import com.collidacube.prison.prisonmenus.ConfigLoader;
import com.collidacube.prison.prisonmenus.api.menus.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class RedirectItem extends ClickableItemImpl {

    private final Menu menu;
    private final String menuId;
    public RedirectItem(int slot, ItemStack item, Menu menu) {
        super(slot, item);
        this.menu = menu;
        this.menuId = null;
    }

    public RedirectItem(int slot, ItemStack item, String menuId) {
        super(slot, item);
        this.menu = null;
        this.menuId = menuId;
    }

    public Menu getMenu() {
        return menuId == null ? menu : ConfigLoader.getMenu(menuId);
    }

    @Override
    public void onClick(InventoryClickEvent event, Menu menu, Player player) {
        event.setCancelled(true);
        this.getMenu().openTo(player);
    }

}
