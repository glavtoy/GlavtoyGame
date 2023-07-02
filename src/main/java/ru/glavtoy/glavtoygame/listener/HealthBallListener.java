package ru.glavtoy.glavtoygame.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import ru.glavtoy.glavtoygame.GlavtoyGame;
import ru.glavtoy.glavtoygame.util.ColorUtil;

public class HealthBallListener implements Listener {

    @EventHandler
    public void onClickOnHealthBall(PlayerInteractEvent e) {
        if (!e.hasItem()) return;
        if ((e.getAction() != Action.RIGHT_CLICK_AIR) && (e.getAction() != Action.RIGHT_CLICK_BLOCK)) return;

        ItemStack itemStack = e.getItem();
        Player player = e.getPlayer();

        if (itemStack.getType() == Material.MAGMA_CREAM) {
            if (!(player.getHealth() == player.getMaxHealth())) {
                player.getInventory().clear(1);
                player.setHealth(20);
            } else {
                player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("full-health")));
            }
        }
    }
}
