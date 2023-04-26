package com.collidacube.prison.prisonmenus;

import com.collidacube.prison.prisonmenus.api.items.ClickableItem;
import com.collidacube.prison.prisonmenus.api.items.ClickableItemImpl;
import com.collidacube.prison.prisonmenus.api.items.CommandItem;
import com.collidacube.prison.prisonmenus.api.items.RedirectItem;
import com.collidacube.prison.prisonmenus.api.menus.GlobalMenu;
import com.collidacube.prison.prisonmenus.api.menus.LiveUpdateMenu;
import com.collidacube.prison.prisonmenus.api.menus.Menu;
import com.collidacube.prison.prisonmenus.api.menus.PlayerMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ConfigLoader {

    private static final HashMap<String, Menu> menus = new HashMap<>();
    private static Set<String> configMenus = new HashSet<>();
    public static Menu getMenu(String menuId) {
        return menus.get(menuId);
    }

    public static boolean registerMenu(String menuId, Menu menu) {
        return menus.putIfAbsent(menuId, menu) == null;
    }
    public static Set<String> getRegisteredMenus() {
        return menus.keySet();
    }

    public static void load(FileConfiguration config) {
        for (String configMenu : configMenus)
            menus.remove(configMenu);
        configMenus.clear();

        configMenus = config.getKeys(false);
        for (String key : configMenus) {
            ConfigurationSection menuConfig = config.getConfigurationSection(key);
            @SuppressWarnings("DataFlowIssue")
            Menu menu = buildMenu(menuConfig);
            registerMenu(key, menu);
            configMenus.add(key);
        }
    }

    public static Menu buildMenu(ConfigurationSection config) {
        String mode = config.getString("mode", "global");
        String label = ChatColor.translateAlternateColorCodes('&', config.getString("label", ""));
        String type = config.getString("type", "chest");
        int rows = config.getInt("rows", 3);
        int updateDelay = config.getInt("updateDelay", 20);
        boolean otherwiseInteractive = config.getBoolean("otherwiseInteractive", true);

        InventoryType invType;
        try { invType = InventoryType.valueOf(type.toUpperCase()); }
        catch (IllegalArgumentException e) { PrisonMenus.getInstance().getLogger().info("There is no inventory type called " + type); return null; }
        Inventory inv = invType == InventoryType.CHEST ?
                Bukkit.createInventory(null, rows*9, label) :
                Bukkit.createInventory(null, invType, label);

        List<ClickableItem> items = new ArrayList<>();
        for (int i = 0; i < inv.getSize(); i++) {
            ConfigurationSection configItem = config.getConfigurationSection("slot_"+i);
            if (configItem == null) continue;

            ClickableItem item = buildClickableItem(i, configItem);
            if (item != null)
                items.add(item);
        }

        if (mode.equalsIgnoreCase("global"))
            return new GlobalMenu(inv, items, otherwiseInteractive);

        if (mode.equalsIgnoreCase("player"))
            return new PlayerMenu(label, inv, items, otherwiseInteractive);

        if (mode.equalsIgnoreCase("liveUpdate")) {
            if (updateDelay < 1)
                PrisonMenus.getInstance().getLogger().severe("Update delay cannot be less that one!");
            else {
                if (updateDelay < 5)
                    PrisonMenus.getInstance().getLogger().warning("Update delay is recommended to be at least 5.");
                return new LiveUpdateMenu(label, inv, items, updateDelay, otherwiseInteractive);
            }
        }

        return null;
    }

    public static ClickableItem buildClickableItem(int slot, ConfigurationSection config) {
        List<String> commands = config.getStringList("commands");
        String menuId = config.getString("redirectTo");
        boolean stealable = config.getBoolean("stealable", false);
        ItemStack item = buildItem(config);
        if (item == null) return null;

        if (menuId != null) {
            if (stealable)
                PrisonMenus.getInstance().getLogger().warning("Stealable redirect items aren't supported!");

            if (!configMenus.contains(menuId) && !menus.containsKey(menuId))
                PrisonMenus.getInstance().getLogger().warning("There is no menu with the id '" + menuId + "'");
            if (commands.size() > 0) commands.add("prisonmenus:openmenu " + menuId);
            else return new RedirectItem(slot, item, menuId);
        }
        if (commands.size() > 0)
            return new CommandItem(slot, item, stealable, commands);
        return new ClickableItemImpl(slot, item, stealable);
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
