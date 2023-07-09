package com.collidacube.prison.prisonmenus.api.menus;

import com.collidacube.prison.prisonmenus.PrisonMenus;
import com.collidacube.prison.prisonmenus.api.items.IClickableItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class AutoUpdateMenu extends BasicMenu {

    private final int autoUpdateDelay;
    private int taskIdNumber = -1;
    public AutoUpdateMenu(String title, int autoUpdateDelay, InventoryType type, IClickableItem... contents) {
        this(title, autoUpdateDelay, type, type.getDefaultSize(), EMPTY_SLOT, contents);
    }

    public AutoUpdateMenu(String title, int autoUpdateDelay, int rows, IClickableItem... contents) {
        this(title, autoUpdateDelay, InventoryType.CHEST, rows*9, EMPTY_SLOT, contents);
    }
    public AutoUpdateMenu(String title, int autoUpdateDelay, InventoryType type, IClickableItem defaultItem, IClickableItem... contents) {
        this(title, autoUpdateDelay, type, type.getDefaultSize(), defaultItem, contents);
    }

    public AutoUpdateMenu(String title, int autoUpdateDelay, int rows, IClickableItem defaultItem, IClickableItem... contents) {
        this(title, autoUpdateDelay, InventoryType.CHEST, rows*9, defaultItem, contents);
    }

    protected AutoUpdateMenu(String title, int autoUpdateDelay, InventoryType type, int rows, IClickableItem defaultItem, IClickableItem... contents) {
        super(title, type, rows, defaultItem, contents);
        this.autoUpdateDelay = autoUpdateDelay;
    }

    public void pauseAutoUpdate() {
        if (taskIdNumber != -1) {
            Bukkit.getScheduler().cancelTask(taskIdNumber);
            taskIdNumber = -1;
        }
    }

    public void startAutoUpdate() {
        if (taskIdNumber != -1) return;
        if (this.autoUpdateDelay >= 0) {
            taskIdNumber = Bukkit.getScheduler().scheduleSyncRepeatingTask(PrisonMenus.getInstance(), this::updateAllInventories, autoUpdateDelay, autoUpdateDelay);
            if (taskIdNumber == -1)
                PrisonMenus.getInstance().getLogger().severe("Failed to schedule update task for menu titled: " + title);
        }
    }

    @Override
    protected Inventory initInventory(Player player) {
        startAutoUpdate();
        return super.initInventory(player);
    }

    @Override
    public boolean onClose(InventoryCloseEvent event, Player player) {
        if (super.onClose(event, player)) {
            pauseAutoUpdate();
            return true;
        }
        return false;
    }
}
