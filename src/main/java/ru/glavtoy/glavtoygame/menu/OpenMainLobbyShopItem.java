package ru.glavtoy.glavtoygame.menu;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.glavtoy.glavtoygame.util.ColorUtil;

public class OpenMainLobbyShopItem {

    private ItemStack is;
    private ItemMeta im;

    public OpenMainLobbyShopItem (Material material, String customName) {
        this.is = new ItemStack(material, 1);
        this.im = this.is.getItemMeta();
        this.im.setDisplayName(ColorUtil.msg(customName));
        this.is.setItemMeta(this.im);
    }

    public static ItemStack getItemStackOpenMainLobbyShopItem(OpenMainLobbyShopItem openMainLobbyShopItem) {
        return openMainLobbyShopItem.is;
    }

    public static Material getMaterialOpenMainLobbyShopItem(OpenMainLobbyShopItem openMainLobbyShopItem) {
        return openMainLobbyShopItem.is.getType();
    }
}
