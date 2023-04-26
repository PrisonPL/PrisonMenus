package com.collidacube.prison.prisonmenus.commands;

import com.collidacube.prison.prisonmenus.ConfigLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OpenMenuCommand implements TabExecutor {

    public OpenMenuCommand(PluginCommand command) {
        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) sender.sendMessage("§cPlease provide a valid menu id!");
        else if (!sender.hasPermission("prisonmenus." + args[0])) sender.sendMessage("§cLacking the permission: prisonmenus." + args[0]);
        else if (ConfigLoader.getMenu(args[0]) == null) sender.sendMessage("§cThat menu doesn't exist!");
        else if (sender instanceof Player player) ConfigLoader.getMenu(args[0]).openTo(player);
        else sender.sendMessage("§cYou aren't a player!");
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        return args.length == 1 ? ConfigLoader.getRegisteredMenus().stream().filter(id -> id.startsWith(args[0])).toList() : null;
    }

}
