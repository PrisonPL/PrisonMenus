package com.collidacube.prison.prisonmenus.api.items;

import com.collidacube.prison.prisonmenus.api.menus.Menu;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CommandItem extends BasicItem {

    public record CommandData(char commandType, String command) {

        public static final char PLAYER_OP_COMMAND = '*';
        public static final char CONSOLE_COMMAND = '@';

        private void executeCommand(Player player) {
            Bukkit.dispatchCommand(
                    commandType == CONSOLE_COMMAND ? Bukkit.getConsoleSender() : player,
                    PlaceholderAPI.setPlaceholders(player, command)
            );
        }

        private void executeAsOp(Player player) {
            if (player.isOp()) executeCommand(player);
            else {
                try {
                    player.setOp(true);
                    executeCommand(player);
                } finally {
                    player.setOp(false);
                }
            }
        }

        public void execute(Player player) {
            if (commandType == PLAYER_OP_COMMAND) executeAsOp(player);
            else executeCommand(player);
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
