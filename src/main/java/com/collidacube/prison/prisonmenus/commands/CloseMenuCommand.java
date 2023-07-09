package com.collidacube.prison.prisonmenus.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CloseMenuCommand implements TabExecutor {

    public CloseMenuCommand(PluginCommand command) {
        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player player) player.closeInventory();
            else sender.sendMessage("§cPlease provide a player!");
            return true;
        }
        else if (!sender.hasPermission("prisonmenus.closemenu.others")) {
            sender.sendMessage("§cYou don't have permission to do that!");
            return true;
        }

        String username = args[0];
        Player player = Bukkit.getPlayer(username);
        if (player == null) {
            sender.sendMessage("§cThis player is not online!");
            return true;
        }

        player.closeInventory();
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return args.length == 1 && sender.hasPermission("prisonmenus.closemenu.others")
                ? Bukkit.getOnlinePlayers()
                    .stream()
                    .map(Player::getName)
                    .filter(l -> l.startsWith(args[0]))
                    .toList()
                : null;
    }

}
