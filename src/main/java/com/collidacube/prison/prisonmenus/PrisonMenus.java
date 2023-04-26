package com.collidacube.prison.prisonmenus;

import com.collidacube.prison.prisonmenus.api.menus.MenuDelegate;
import com.collidacube.prison.prisonmenus.commands.OpenMenuCommand;
import com.collidacube.prison.prisonmenus.commands.ReloadMenusCommand;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class PrisonMenus extends JavaPlugin {

    private static PrisonMenus instance = null;
    public static PrisonMenus getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        MenuDelegate.init(this);

        saveDefaultConfig();
        ConfigLoader.load(getConfig());

        PluginCommand cmd = getCommand("openmenu");
        if (cmd != null) new OpenMenuCommand(cmd);

        cmd = getCommand("reloadmenus");
        if (cmd != null) new ReloadMenusCommand(cmd);
    }

}
