package ru.glavtoy.glavtoygame.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import ru.glavtoy.glavtoygame.GlavtoyGame;
import ru.glavtoy.glavtoygame.data.Data;
import ru.glavtoy.glavtoygame.game.Game;
import ru.glavtoy.glavtoygame.menu.ShopMenu;
import ru.glavtoy.glavtoygame.scoreboard.GameScoreboard;
import ru.glavtoy.glavtoygame.scoreboard.MainLobbyScoreboard;
import ru.glavtoy.glavtoygame.scoreboard.WaitingLobbyScoreboard;
import ru.glavtoy.glavtoygame.util.ColorUtil;

public class GameListener implements Listener {

    @EventHandler
    public void onDropItem(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        if (Game.playerIsInGame(player)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (Game.playerIsInGame(player)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onCommandWrite(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        if ((!(player.isOp()) || !(player.hasPermission("glavtoygame.admin"))) && Game.playerIsInGame(player)) {
            if (e.getMessage().startsWith("/")) {
                player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("block-command")));
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        Player player = e.getEntity();
        EntityDamageEvent damageEvent = player.getLastDamageCause();
        EntityDamageEvent.DamageCause damageCause = damageEvent.getCause();

        LivingEntity killer = player.getKiller();
        if (player.getKiller() != null && player.getKiller() instanceof Player) {
                Player playerKiller = (Player) killer;
                e.setDeathMessage(null);
                Game game = Game.getGameAtPlayer(playerKiller);
                GameScoreboard.addPlayerToOldKillsBase(playerKiller, Game.getPlayerKillsInGame(game, playerKiller));
                GameScoreboard.addPlayerToOldBalanceBase(playerKiller, Game.getBalance(game, playerKiller));
                Game.sendMessageForAllPlayers(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("kill-message-3")
                        .replace("{player}", player.getName()).replace("{killer}", playerKiller.getName())));
                Game.giveMoney(game, playerKiller, GlavtoyGame.getInstance().getConfig().getInt("reward-for-kill"));
                playerKiller.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("kill-message-1")
                        .replace("{player}", player.getName()).replace("{reward}", String.valueOf(
                                GlavtoyGame.getInstance().getConfig().getInt("reward-for-kill")))
                        .replace("{balance}", String.valueOf(Game.getBalance(game, playerKiller)))));
                player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("kill-message-2")
                        .replace("{player}", playerKiller.getName())));
                e.getDrops().clear();
                Game.addKillToKills(game);
                Game.addKillToPlayer(game, playerKiller);
                Data.addPlayerToKillsBase(playerKiller, Data.getKills(playerKiller) + 1);
                Data.writeKillsToPlayerIsExistInDataConfig(playerKiller, Data.getKills(playerKiller));
                GlavtoyGame.saveDataConfig();
        } else {
            e.setDeathMessage(null);
            Game game = Game.getGameAtPlayer(player);
            Game.sendMessageForAllPlayers(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("kill-message-4")
                    .replace("{player}", player.getName())));
            e.getDrops().clear();
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(GlavtoyGame.getInstance(), new Runnable() {
            @Override
            public void run() {
                e.getEntity().spigot().respawn();
            }
        }, 2L);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        if (Game.playerIsInGame(player)) {
            Game game = Game.getGameAtPlayer(player);
            Game.clearBusySpawns(game);
            e.setRespawnLocation(Game.getFreeSpawn(game));
            Game.giveStartKitForPlayer(player);
            Game.giveOpenShopItemForPlayer(player, ShopMenu.getOpenShopItem(), GlavtoyGame.getInstance().getConfig().getInt("shop-menu.open-item.slot"));
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (Game.playerIsInGame(player)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onLeaveFromServerInGame(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (Game.playerIsInGame(player)) {
            Game game = Game.getGameAtPlayer(player);
            WaitingLobbyScoreboard.putAmountOfPlayersToOldBase(game, Game.getPlayersAmountInGame(game)); // сотри если чо
            Game.setPlayersAmountInGame(game, Game.getPlayersAmountInGame(game) - 1);
            Game.sendMessageForAllPlayers(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("leave-message").replace("{p}", String.valueOf(Game.getPlayers(game))).replace("{max}", String.valueOf(Game.getMaxPlayers(game))).replace("{player}", player.getName())));
            Game.removePlayerFromPlayerInGameList(player);
            Game.removePlayerFromGameBase(player);
            Game.removePlayerInGameList(player);
            Game.removePlayerFromBalanceBase(game, player);
            Game.removePlayerFromPlayersList(game, player);
            Game.removePlayerFromKillBase(game, player);
            player.getInventory().clear();
            System.out.println("[" + Game.getMapName(game) + "] -> " + player.getName() + " вышел (" + Game.getPlayersAmountInGame(game) + "/" + GlavtoyGame.getMapsConfig().getInt("maps." + Game.getMapName(game) + ".max-players") + ")");
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = Bukkit.getPlayer(e.getWhoClicked().getName());
        if (Game.playerIsInGame(player)) {
            e.setCancelled(true);
        }
    }
}
