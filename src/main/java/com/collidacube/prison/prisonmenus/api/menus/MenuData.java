package com.collidacube.prison.prisonmenus.api.menus;

import com.collidacube.prison.prisonmenus.api.items.IClickableItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.inventory.InventoryType;

import java.util.List;

public class MenuData {
    public String mode = null;
    public String menuId = null;
    public InventoryType type = null;
    public int rows = -1;
    public String title = null;
    public List<IClickableItem> contents = null;

    public int calcSize() {
        if (type == null) return 0;
        return type == InventoryType.CHEST ? rows * 9 : type.getDefaultSize();
    }

    public final ConfigurationSection config;

    public MenuData(ConfigurationSection config) {
        this.config = config;
    }
}
