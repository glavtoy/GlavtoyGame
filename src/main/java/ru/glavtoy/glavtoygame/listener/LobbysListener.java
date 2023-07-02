package ru.glavtoy.glavtoygame.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import ru.glavtoy.glavtoygame.GlavtoyGame;
import ru.glavtoy.glavtoygame.game.Game;
import ru.glavtoy.glavtoygame.scoreboard.WaitingLobbyScoreboard;
import ru.glavtoy.glavtoygame.util.ColorUtil;

public class LobbysListener implements Listener {

    @EventHandler
    public void onDropItem(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        if (Game.playerIsInLobbys(player)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (Game.playerIsInLobbys(player)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        if (Game.playerIsInLobbys(player)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = Bukkit.getPlayer(e.getWhoClicked().getName());
        if (Game.playerIsInLobbys(player)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeaveFromServerInGame(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (Game.playerIsInWaitingLobby(player)) {
            Game game = Game.getGameAtPlayer(player);
            WaitingLobbyScoreboard.putAmountOfPlayersToOldBase(game, Game.getPlayersAmountInGame(game));
            Game.setPlayersAmountInGame(game, Game.getPlayersAmountInGame(game) - 1);
            Game.sendMessageForAllPlayers(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("leave-message").replace("{p}", String.valueOf(Game.getPlayers(game))).replace("{max}", String.valueOf(Game.getMaxPlayers(game))).replace("{player}", player.getName())));
            Game.removePlayerToWaitingLobbyList(player);
            Game.removePlayerFromGameBase(player);
            Game.removePlayerInGameList(player);
            Game.removePlayerFromBalanceBase(game, player);
            Game.removePlayerFromPlayersList(game, player);
            Game.removePlayerFromKillBase(game, player);
            player.setLevel(0);
            player.getInventory().clear();
            System.out.println("[" + Game.getMapName(game) + "] -> " + player.getName() + " вышел (" + Game.getPlayersAmountInGame(game) + "/" + GlavtoyGame.getMapsConfig().getInt("maps." + Game.getMapName(game) + ".max-players") + ")");
        }
    }

    @EventHandler
    public void onLeaveInMainLobby(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (Game.playerIsInMainLobby(player)) {
            Game.removePlayerFromMainLobbyAmount();
            Game.removePlayerToPlayersInMainLobbyList(player);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (Game.playerIsInWaitingLobby(player) || Game.playerIsInMainLobby(player)) {
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (Game.playerIsInWaitingLobby(player) || Game.playerIsInMainLobby(player)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockDamage(EntityDamageByBlockEvent e) {
        Entity entity = e.getEntity();
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (Game.playerIsInWaitingLobby(player) || Game.playerIsInMainLobby(player)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
            Player player = e.getPlayer();
                Location loc = new Location(Bukkit.getWorld((String) GlavtoyGame.getMapsConfig().get("main-lobby.world")),
                        GlavtoyGame.getMapsConfig().getDouble("main-lobby.x"),
                        GlavtoyGame.getMapsConfig().getDouble("main-lobby.y"),
                        GlavtoyGame.getMapsConfig().getDouble("main-lobby.z"),
                        GlavtoyGame.getMapsConfig().getInt("main-lobby.yaw"),
                        GlavtoyGame.getMapsConfig().getInt("main-lobby.pitch"));
                Game.addPlayerToMainLobbyAmount();
                Game.addPlayerToPlayersInMainLobbyList(player);
                player.setHealth(player.getMaxHealth());
                player.setFoodLevel(20);
                player.teleport(loc);
    }

    @EventHandler
    public void onCommandWrite(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        if ((!(player.isOp()) || !(player.hasPermission("glavtoygame.admin"))) && Game.playerIsInWaitingLobby(player)) {
            if (e.getMessage().startsWith("/")) {
                player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("block-command")));
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamageEntityByEntity(EntityDamageByEntityEvent e) {
        Entity entity = e.getEntity();
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (Game.playerIsInWaitingLobby(player) || Game.playerIsInMainLobby(player)) {
                e.setCancelled(true);
            }
        }
    }
}
