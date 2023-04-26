package com.collidacube.prison.prisonmenus.api.items;

import com.collidacube.prison.prisonmenus.Utils;
import com.collidacube.prison.prisonmenus.api.menus.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CommandItem extends ClickableItemImpl {

    protected final List<String> commands;
    public CommandItem(int slot, ItemStack item, List<String> commands) {
        super(slot, item);
        this.commands = commands;
    }
    public CommandItem(int slot, ItemStack item, boolean stealable, List<String> commands) {
        super(slot, item, stealable);
        this.commands = commands;
    }

    @Override
    public void onClick(InventoryClickEvent event, Menu menu, Player player) {
        super.onClick(event, menu, player);
        commands.forEach(cmd -> Bukkit.dispatchCommand(player, Utils.formatString(player, cmd)));
    }

}
