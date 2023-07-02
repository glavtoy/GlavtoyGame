package ru.glavtoy.glavtoygame.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import ru.glavtoy.glavtoygame.menu.OpenShopItem;
import ru.glavtoy.glavtoygame.menu.ShopMenu;

public class OpenItemListener implements Listener {

    @EventHandler
    public void onClickOnOpenShopItem(PlayerInteractEvent e) {
        if (!e.hasItem()) return;
        if ((e.getAction() != Action.RIGHT_CLICK_AIR) && (e.getAction() != Action.RIGHT_CLICK_BLOCK)) return;

        ItemStack itemStack = e.getItem();
        Player player = e.getPlayer();

        if (itemStack.getType() == OpenShopItem.getMaterialOpenShopItem(ShopMenu.getOpenShopItem())) {
            player.openInventory(ShopMenu.getInv());
        }
    }
}
