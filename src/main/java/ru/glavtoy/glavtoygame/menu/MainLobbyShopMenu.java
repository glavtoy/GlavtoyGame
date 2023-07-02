package ru.glavtoy.glavtoygame.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.glavtoy.glavtoygame.GlavtoyGame;
import ru.glavtoy.glavtoygame.util.ColorUtil;

import java.util.ArrayList;
import java.util.List;

public class MainLobbyShopMenu {

    private static Inventory inv;
    private static OpenMainLobbyShopItem openMainLobbyShopItem;

    public static void init() {
        inv = Bukkit.createInventory(null, GlavtoyGame.getInstance().getConfig().getInt("main-lobby-shop-menu.inventory-size"), GlavtoyGame.getInstance().getConfig().getString("main-lobby-shop-menu.inventory-title"));
        Material material = Material.getMaterial((String) GlavtoyGame.getInstance().getConfig().get("main-lobby-shop-menu.open-item.item-id"));
        openMainLobbyShopItem = new OpenMainLobbyShopItem(material, GlavtoyGame.getInstance().getConfig().getString("main-lobby-shop-menu.open-item.custom-name"));
        generateItems();
    }

    public static OpenMainLobbyShopItem getOpenMainLobbyShopItem() {
        return openMainLobbyShopItem;
    }

    public static void generateItems() {
        List<String> lore;
        for (String key : GlavtoyGame.getInstance().getConfig().getConfigurationSection("shop-menu.items").getKeys(false)) {
            Material material = Material.getMaterial(key);
            ItemStack is = new ItemStack(material, GlavtoyGame.getInstance().getConfig().getInt("shop-menu.items." + key + ".amount"));
            ItemMeta im = is.getItemMeta();
            im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            im.setDisplayName(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("shop-menu.items." + key + ".custom-name")));
            lore = new ArrayList<String>();
            for (String line : GlavtoyGame.getInstance().getConfig().getStringList("shop-menu.items." + key + ".lore")) {
                lore.add(ColorUtil.msg(line.replace("{cost}", String.valueOf(GlavtoyGame.getInstance().getConfig().getInt("shop-menu.items." + key + ".price")))));
            }
            im.setLore(lore);
            is.setItemMeta(im);
            inv.setItem(GlavtoyGame.getInstance().getConfig().getInt("shop-menu.items." + key + ".slot"), is);
        }
    }

    public static Inventory getInv() {
        return inv;
    }
}
