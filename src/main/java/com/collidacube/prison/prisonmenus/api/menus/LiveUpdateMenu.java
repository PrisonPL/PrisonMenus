package com.collidacube.prison.prisonmenus.api.menus;

import com.collidacube.prison.prisonmenus.PrisonMenus;
import com.collidacube.prison.prisonmenus.api.items.ClickableItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class LiveUpdateMenu extends PlayerMenu {

    protected int delay;
    protected boolean beingViewed = false;
    public LiveUpdateMenu(String title, Inventory inventory, List<ClickableItem> items, int updateDelay) {
        super(title, inventory, items);
        this.delay = updateDelay;
    }
    public LiveUpdateMenu(String title, Inventory inventory, List<ClickableItem> items, int updateDelay, boolean otherwiseInteractive) {
        super(title, inventory, items, otherwiseInteractive);
        this.delay = updateDelay;
    }

    protected void nextUpdate() {
        nextUpdate(false);
    }

    protected void nextUpdate(boolean force) {
        List<Player> viewers = MenuDelegate.getViewers(this);
        if (viewers.size() == 0)
            beingViewed = false;

        if (beingViewed || force) {
            viewers.forEach(this::formatInventory);
            Bukkit.getScheduler().scheduleSyncDelayedTask(PrisonMenus.getInstance(), this::nextUpdate, delay);
        }
    }

    @Override
    public void openTo(Player player) {
        super.openTo(player);
        if (!beingViewed) {
            beingViewed = true;
            nextUpdate(true);
        }
    }

}
