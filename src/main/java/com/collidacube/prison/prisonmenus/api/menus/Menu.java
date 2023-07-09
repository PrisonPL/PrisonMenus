package com.collidacube.prison.prisonmenus.api.menus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public abstract class Menu implements IMenu {

    private static final HashMap<String, Menu> menus = new HashMap<>();
    private static final HashMap<Player, Menu> viewers = new HashMap<>();

    public static Menu getMenu(String menuId) {
        return menus.get(menuId);
    }
    public static @NotNull Set<String> getRegisteredMenus() {
        return menus.keySet();
    }
    public static boolean registerMenu(String menuId, Menu menu) {
        return menus.putIfAbsent(menuId, menu) == null;
    }

    public static void clearAllRegisteredMenus() {
        menus.clear();
        new ArrayList<>(viewers.keySet()).forEach(Player::closeInventory);
        viewers.clear();
    }

    public static Menu getCurrentMenu(Player player) {
        return viewers.get(player);
    }

    protected final HashMap<Player, Inventory> inventories = new HashMap<>();

    public List<Player> getViewers() {
        return new ArrayList<>(inventories.keySet());
    }

    protected Inventory initInventory(Player player) {
        InventoryType type = getInventoryType(player);
        int size = getInventorySize(player);
        String title = getInventoryTitle(player);

        Inventory inventory = type == InventoryType.CHEST
                                      ? Bukkit.createInventory(null, size, title)
                                      : Bukkit.createInventory(null, type, title);
        player.openInventory(inventory);
        return inventory;
    }

    public void render(Player player) {
        Inventory inventory = inventories.computeIfAbsent(player, this::initInventory);
        inventory.setContents(getItems(player));

        viewers.put(player, this);
    }

    public boolean onClose(InventoryCloseEvent event, Player player) {
        this.inventories.remove(player);
        viewers.remove(player);
        return true;
    }

}
