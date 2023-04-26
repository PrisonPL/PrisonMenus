package com.collidacube.prison.prisonmenus.commands;

import com.collidacube.prison.prisonmenus.ConfigLoader;
import com.collidacube.prison.prisonmenus.PrisonMenus;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;

public class ReloadMenusCommand implements CommandExecutor {

    public ReloadMenusCommand(PluginCommand command) {
        command.setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        try {
            PrisonMenus.getInstance().reloadConfig();
            ConfigLoader.load(PrisonMenus.getInstance().getConfig());
            sender.sendMessage("§aSuccessfully reloaded menus");
        } catch (Exception e) {
            sender.sendMessage("§cAn error occurred while reloading. Please check console for details.");
            PrisonMenus.getInstance().getLogger().severe("An error occurred while reloading:");
            e.printStackTrace();
        }
        return true;
    }
}
