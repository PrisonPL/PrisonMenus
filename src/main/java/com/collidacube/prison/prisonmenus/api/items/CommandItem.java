package com.collidacube.prison.prisonmenus.api.items;

import com.collidacube.prison.prisonmenus.api.menus.Menu;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CommandItem extends BasicItem {

    public record CommandData(boolean consoleCommand, String command) {

        public void execute(Player player) {
            Bukkit.dispatchCommand(
                    consoleCommand ? Bukkit.getConsoleSender() : player,
                    PlaceholderAPI.setPlaceholders(player, command)
            );
        }

    }

    private final List<CommandData> commands;

    public CommandItem(ItemStack item, boolean stealable, List<CommandData> commands) {
        super(item, stealable);
        this.commands = commands;
    }

    @Override
    public void onClick(InventoryClickEvent event, Menu menu, Player viewer) {
        super.onClick(event, menu, viewer);
        commands.forEach(cmd -> cmd.execute(viewer));
    }
}
