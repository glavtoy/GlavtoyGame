package ru.glavtoy.glavtoygame.game;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.glavtoy.glavtoygame.util.ColorUtil;

public class LeaveItem {

    private ItemStack is;
    private ItemMeta im;

    public LeaveItem (Material material, String customName) {
        this.is = new ItemStack(material, 1);
        this.im = this.is.getItemMeta();
        this.im.setDisplayName(ColorUtil.msg(customName));
        this.is.setItemMeta(this.im);
    }

    public static ItemStack getItemStackLeaveItem(LeaveItem leaveItem) {
        return leaveItem.is;
    }

    public static Material getMaterialLeaveItem(LeaveItem leaveItem) {
        return leaveItem.is.getType();
    }
}
