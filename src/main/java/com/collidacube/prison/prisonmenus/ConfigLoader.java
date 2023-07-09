package com.collidacube.prison.prisonmenus;

import com.collidacube.prison.prisonmenus.api.items.BasicItem;
import com.collidacube.prison.prisonmenus.api.items.CommandItem;
import com.collidacube.prison.prisonmenus.api.items.IClickableItem;
import com.collidacube.prison.prisonmenus.api.menus.Menu;
import com.collidacube.prison.prisonmenus.api.menus.MenuData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class ConfigLoader {

    private static final HashMap<String, Function<MenuData, Menu>> menuBuilders = new HashMap<>();
    public static boolean registerMenuBuilder(String menuMode, Function<MenuData, Menu> builder) {
        return menuBuilders.putIfAbsent(menuMode, builder) == null;
    }

    public static void load(FileConfiguration config) {
        Menu.clearAllRegisteredMenus();

        for (String key : config.getKeys(false)) {
            ConfigurationSection menuConfig = config.getConfigurationSection(key);
            MenuData menuData = buildMenuData(key, menuConfig);
            if (menuData == null) continue;

            Function<MenuData, Menu> builder = menuBuilders.get(menuData.mode);
            if (builder == null) continue;

            Menu.registerMenu(key, builder.apply(menuData));
        }
    }

    public static MenuData buildMenuData(String menuId, ConfigurationSection config) {
        MenuData menuData = new MenuData(config);
        menuData.menuId = menuId;
        menuData.mode = config.getString("mode", "basic");
        menuData.title = ChatColor.translateAlternateColorCodes('&', config.getString("title", ""));

        String type = config.getString("type", "chest");
        try {
            menuData.type = InventoryType.valueOf(type.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            PrisonMenus.getInstance().getLogger().severe("There is no inventory type called " + type);
            return null;
        }

        menuData.rows = config.getInt("rows", 3);

        List<IClickableItem> items = new ArrayList<>();
        int minSize = menuData.calcSize();
        while (minSize >= items.size()) items.add(null);
        for (String key : config.getKeys(false)) {
            if (!key.startsWith("slot_")) continue;

            String slotString = key.substring("slot_".length());
            int slot;
            try {
                slot = Integer.parseInt(slotString);
            } catch (NumberFormatException ignored) {
                PrisonMenus.getInstance().getLogger().severe("Invalid slot index: " + slotString + " [" + menuId + "]");
                continue;
            }

            @SuppressWarnings("DataFlowIssue")
            IClickableItem item = buildClickableItem(config.getConfigurationSection(key));

            if (item == null) continue;

            while (slot >= items.size()) items.add(null);
            items.set(slot, item);
        }
        menuData.contents = items;

        return menuData;
    }

    public static CommandItem.CommandData parseCommand(String command) {
        boolean consoleCommand = command.startsWith("@");
        String commandString = consoleCommand ? command.substring(1) : command;
        return new CommandItem.CommandData(consoleCommand, commandString);
    }

    public static IClickableItem buildClickableItem(ConfigurationSection config) {
        boolean stealable = config.getBoolean("stealable");
        ItemStack item = buildItem(config);
        List<CommandItem.CommandData> commands = config.getStringList("commands")
                .stream()
                .map(ConfigLoader::parseCommand)
                .toList();
        if (item == null) return null;

        if (commands.size() > 0)
            return new CommandItem(item, stealable, commands);
        return new BasicItem(item, stealable);
    }

    public static ItemStack buildItem(ConfigurationSection config) {
        String itemType = config.getString("item", "air");
        int amount = Math.max(config.getInt("amount"), 1);
        String displayName = config.getString("displayName");
        List<String> lore = config.getStringList("lore");

        Material mat;
        try { mat = Material.valueOf(itemType.toUpperCase()); }
        catch (Exception e) {
            PrisonMenus.getInstance().getLogger().severe("Invalid item type: " + itemType);
            return null;
        }

        ItemStack item = new ItemStack(mat, amount);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            if (displayName != null)
                meta.setDisplayName(displayName);
            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        return item;
    }

}
