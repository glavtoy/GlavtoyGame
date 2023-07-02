package ru.glavtoy.glavtoygame.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.glavtoy.glavtoygame.GlavtoyGame;
import ru.glavtoy.glavtoygame.game.Game;
import ru.glavtoy.glavtoygame.scoreboard.GameScoreboard;
import ru.glavtoy.glavtoygame.util.ColorUtil;

public class ShopMenuListener implements Listener {
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = Bukkit.getPlayer(e.getWhoClicked().getName());
        Game game = Game.getGameAtPlayer(player);
        if (e.getView().getTitle().equalsIgnoreCase(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("shop-menu.inventory-title")))) {
        if (e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.STONE_SWORD) {
            if (Game.getBalance(game, player) >= GlavtoyGame.getInstance().getConfig().getInt("shop-menu.items.STONE_SWORD.price")) {
                ItemStack is = new ItemStack(Material.STONE_SWORD, 1);
                ItemMeta im = is.getItemMeta();
                im.setUnbreakable(true);
                is.setItemMeta(im);
                player.getInventory().setItem(0, is);
                GameScoreboard.addPlayerToOldBalanceBase(player, Game.getBalance(game, player));
                Game.takeMoney(game, player, GlavtoyGame.getInstance().getConfig().getInt("shop-menu.items.STONE_SWORD.price"));
            } else {
                player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("no-money")
                        .replace("{balance}", String.valueOf(Game.getBalance(game, player)))));
            }
        }
        if (e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.IRON_SWORD) {
            if (Game.getBalance(game, player) >= GlavtoyGame.getInstance().getConfig().getInt("shop-menu.items.IRON_SWORD.price")) {
                ItemStack is = new ItemStack(Material.IRON_SWORD, 1);
                ItemMeta im = is.getItemMeta();
                im.setUnbreakable(true);
                is.setItemMeta(im);
                player.getInventory().setItem(0, is);
                GameScoreboard.addPlayerToOldBalanceBase(player, Game.getBalance(game, player));
                Game.takeMoney(game, player, GlavtoyGame.getInstance().getConfig().getInt("shop-menu.items.IRON_SWORD.price"));
            } else {
                player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("no-money")
                        .replace("{balance}", String.valueOf(Game.getBalance(game, player)))));
            }
        }
        if (e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.DIAMOND_SWORD) {
            if (Game.getBalance(game, player) >= GlavtoyGame.getInstance().getConfig().getInt("shop-menu.items.DIAMOND_SWORD.price")) {
                ItemStack is = new ItemStack(Material.DIAMOND_SWORD, 1);
                ItemMeta im = is.getItemMeta();
                im.setUnbreakable(true);
                is.setItemMeta(im);
                player.getInventory().setItem(0, is);
                GameScoreboard.addPlayerToOldBalanceBase(player, Game.getBalance(game, player));
                Game.takeMoney(game, player, GlavtoyGame.getInstance().getConfig().getInt("shop-menu.items.DIAMOND_SWORD.price"));
            } else {
                player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("no-money")
                        .replace("{balance}", String.valueOf(Game.getBalance(game, player)))));
            }
        }
        if (e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.CHAINMAIL_HELMET) {
            if (Game.getBalance(game, player) >= GlavtoyGame.getInstance().getConfig().getInt("shop-menu.items.CHAINMAIL_HELMET.price")) {
                ItemStack is = new ItemStack(Material.CHAINMAIL_HELMET, 1);
                ItemMeta im = is.getItemMeta();
                im.setUnbreakable(true);
                is.setItemMeta(im);
                ItemStack is1 = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
                ItemMeta im1 = is1.getItemMeta();
                im1.setUnbreakable(true);
                is1.setItemMeta(im1);
                ItemStack is2 = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
                ItemMeta im2 = is2.getItemMeta();
                im2.setUnbreakable(true);
                is2.setItemMeta(im2);
                ItemStack is3 = new ItemStack(Material.CHAINMAIL_BOOTS, 1);
                ItemMeta im3 = is3.getItemMeta();
                im3.setUnbreakable(true);
                is3.setItemMeta(im3);
                player.getInventory().setHelmet(is);
                player.getInventory().setChestplate(is1);
                player.getInventory().setLeggings(is2);
                player.getInventory().setBoots(is3);
                GameScoreboard.addPlayerToOldBalanceBase(player, Game.getBalance(game, player));
                Game.takeMoney(game, player, GlavtoyGame.getInstance().getConfig().getInt("shop-menu.items.CHAINMAIL_HELMET.price"));
            } else {
                player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("no-money")
                        .replace("{balance}", String.valueOf(Game.getBalance(game, player)))));
            }
        }
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.IRON_HELMET) {
                if (Game.getBalance(game, player) >= GlavtoyGame.getInstance().getConfig().getInt("shop-menu.items.IRON_HELMET.price")) {
                    ItemStack is = new ItemStack(Material.IRON_HELMET, 1);
                    ItemMeta im = is.getItemMeta();
                    im.setUnbreakable(true);
                    is.setItemMeta(im);
                    ItemStack is1 = new ItemStack(Material.IRON_CHESTPLATE, 1);
                    ItemMeta im1 = is1.getItemMeta();
                    im1.setUnbreakable(true);
                    is1.setItemMeta(im1);
                    ItemStack is2 = new ItemStack(Material.IRON_LEGGINGS, 1);
                    ItemMeta im2 = is2.getItemMeta();
                    im2.setUnbreakable(true);
                    is2.setItemMeta(im2);
                    ItemStack is3 = new ItemStack(Material.IRON_BOOTS, 1);
                    ItemMeta im3 = is3.getItemMeta();
                    im3.setUnbreakable(true);
                    is3.setItemMeta(im3);
                    player.getInventory().setHelmet(is);
                    player.getInventory().setChestplate(is1);
                    player.getInventory().setLeggings(is2);
                    player.getInventory().setBoots(is3);
                    GameScoreboard.addPlayerToOldBalanceBase(player, Game.getBalance(game, player));
                    Game.takeMoney(game, player, GlavtoyGame.getInstance().getConfig().getInt("shop-menu.items.IRON_HELMET.price"));
                } else {
                    player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("no-money")
                            .replace("{balance}", String.valueOf(Game.getBalance(game, player)))));
                }
            }
        if (e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.DIAMOND_HELMET) {
            if (Game.getBalance(game, player) >= GlavtoyGame.getInstance().getConfig().getInt("shop-menu.items.DIAMOND_HELMET.price")) {
                ItemStack is = new ItemStack(Material.DIAMOND_HELMET, 1);
                ItemMeta im = is.getItemMeta();
                im.setUnbreakable(true);
                is.setItemMeta(im);
                ItemStack is1 = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
                ItemMeta im1 = is1.getItemMeta();
                im1.setUnbreakable(true);
                is1.setItemMeta(im1);
                ItemStack is2 = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
                ItemMeta im2 = is2.getItemMeta();
                im2.setUnbreakable(true);
                is2.setItemMeta(im2);
                ItemStack is3 = new ItemStack(Material.DIAMOND_BOOTS, 1);
                ItemMeta im3 = is3.getItemMeta();
                im3.setUnbreakable(true);
                is3.setItemMeta(im3);
                player.getInventory().setHelmet(is);
                player.getInventory().setChestplate(is1);
                player.getInventory().setLeggings(is2);
                player.getInventory().setBoots(is3);
                GameScoreboard.addPlayerToOldBalanceBase(player, Game.getBalance(game, player));
                Game.takeMoney(game, player, GlavtoyGame.getInstance().getConfig().getInt("shop-menu.items.DIAMOND_HELMET.price"));
            } else {
                player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("no-money")
                        .replace("{balance}", String.valueOf(Game.getBalance(game, player)))));
                }
            }
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.MAGMA_CREAM) {
                if (Game.getBalance(game, player) >= GlavtoyGame.getInstance().getConfig().getInt("shop-menu.items.MAGMA_CREAM.price")) {
                    ItemStack is = new ItemStack(Material.MAGMA_CREAM, 1);
                    ItemMeta im = is.getItemMeta();
                    im.setDisplayName(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("shop-menu.items.MAGMA_CREAM.custom-name")));
                    is.setItemMeta(im);
                    player.getInventory().setItem(1, is);
                    GameScoreboard.addPlayerToOldBalanceBase(player, Game.getBalance(game, player));
                    Game.takeMoney(game, player, GlavtoyGame.getInstance().getConfig().getInt("shop-menu.items.MAGMA_CREAM.price"));
                } else {
                    player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("no-money")
                            .replace("{balance}", String.valueOf(Game.getBalance(game, player)))));
                }
            }
        }
    }
}
