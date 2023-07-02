package ru.glavtoy.glavtoygame.menu;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.glavtoy.glavtoygame.util.ColorUtil;

public class OpenShopItem {

    private ItemStack is;
    private ItemMeta im;

    public OpenShopItem (Material material, String customName) {
        this.is = new ItemStack(material, 1);
        this.im = this.is.getItemMeta();
        this.im.setDisplayName(ColorUtil.msg(customName));
        this.is.setItemMeta(this.im);
    }

    public static ItemStack getItemStackOpenShopItem(OpenShopItem openShopItem) {
        return openShopItem.is;
    }

    public static Material getMaterialOpenShopItem(OpenShopItem openShopItem) {
        return openShopItem.is.getType();
    }
}
