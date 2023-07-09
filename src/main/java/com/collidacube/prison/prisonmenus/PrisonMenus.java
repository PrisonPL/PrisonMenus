package com.collidacube.prison.prisonmenus;

import com.collidacube.prison.prisonmenus.api.items.IClickableItem;
import com.collidacube.prison.prisonmenus.api.menus.AutoUpdateMenu;
import com.collidacube.prison.prisonmenus.api.menus.BasicMenu;
import com.collidacube.prison.prisonmenus.api.menus.Menu;
import com.collidacube.prison.prisonmenus.commands.OpenMenuCommand;
import com.collidacube.prison.prisonmenus.commands.ReloadMenusCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;

public final class PrisonMenus extends JavaPlugin implements Listener {

    private static PrisonMenus instance = null;
    public static PrisonMenus getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        ConfigLoader.registerMenuBuilder("basic", menuData -> {
            IClickableItem[] contents = menuData.contents.subList(0, menuData.calcSize()).toArray(new IClickableItem[0]);
            if (menuData.type == InventoryType.CHEST)
                return new BasicMenu(menuData.title, menuData.rows, contents);
            else return new BasicMenu(menuData.title, menuData.type, contents);
        });

        ConfigLoader.registerMenuBuilder("autoUpdate", menuData -> {
            IClickableItem[] contents = menuData.contents.subList(0, menuData.calcSize()).toArray(new IClickableItem[0]);
            int updateDelay = menuData.config.getInt("updateDelay");
            if (menuData.type == InventoryType.CHEST)
                return new AutoUpdateMenu(menuData.title, updateDelay, menuData.rows, contents);
            else return new AutoUpdateMenu(menuData.title, updateDelay, menuData.type, contents);
        });
    }

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getPluginManager().registerEvents(this, this);

        saveDefaultConfig();
        ConfigLoader.load(getConfig());

        PluginCommand cmd = getCommand("openmenu");
        if (cmd != null) new OpenMenuCommand(cmd);

        cmd = getCommand("reloadmenus");
        if (cmd != null) new ReloadMenusCommand(cmd);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            Menu menu = Menu.getCurrentMenu(player);
            if (menu != null)
                menu.onClick(event, player);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player player) {
            Menu menu = Menu.getCurrentMenu(player);
            if (menu != null)
                menu.onClose(event, player);
        }
    }

    private static final YamlConfiguration EMPTY_CONFIG = new YamlConfiguration();

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        getConfig().setDefaults(EMPTY_CONFIG);
    }
}
