package ru.glavtoy.glavtoygame.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import ru.glavtoy.glavtoygame.GlavtoyGame;
import ru.glavtoy.glavtoygame.game.Game;
import ru.glavtoy.glavtoygame.game.LeaveItem;
import ru.glavtoy.glavtoygame.runnable.MainLobbyRunnable;
import ru.glavtoy.glavtoygame.scoreboard.MainLobbyScoreboard;
import ru.glavtoy.glavtoygame.scoreboard.WaitingLobbyScoreboard;
import ru.glavtoy.glavtoygame.util.ColorUtil;

public class LeaveItemListener implements Listener {

    @EventHandler
    public void onClickOnLeaveItem(PlayerInteractEvent e) {
        if (!e.hasItem()) return;
        if ((e.getAction() != Action.RIGHT_CLICK_AIR) && (e.getAction() != Action.RIGHT_CLICK_BLOCK)) return;

        ItemStack itemStack = e.getItem();
        Player player = e.getPlayer();

        if (itemStack.getType() == LeaveItem.getMaterialLeaveItem(Game.getLeaveItem())) {
            Game game = Game.getGameAtPlayer(player);
            WaitingLobbyScoreboard.putAmountOfPlayersToOldBase(game, Game.getPlayersAmountInGame(game));
            Game.setPlayersAmountInGame(game, Game.getPlayersAmountInGame(game) - 1);
            Game.sendMessageForAllPlayers(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("leave-message").replace("{p}", String.valueOf(Game.getPlayers(game))).replace("{max}", String.valueOf(Game.getMaxPlayers(game))).replace("{player}", player.getName())));
            Game.removeCountdownBarForPlayer(player, game);
            Game.removePlayerToWaitingLobbyList(player);
            Game.removePlayerFromGameBase(player);
            Game.removePlayerInGameList(player);
            Game.removePlayerFromBalanceBase(game, player);
            Game.removePlayerFromPlayersList(game, player);
            Game.hideCountdownBarForPlayer(player, game);
            Game.hideWaitingBar(game, player);
            if (Game.getPlayersFromMainLobbyAmount() == 0) {
                Game.addPlayerToMainLobbyAmount();
                Game.addPlayerToPlayersInMainLobbyList(player);
                MainLobbyRunnable.startRunnable();
            } else {
                Game.addPlayerToMainLobbyAmount();
                Game.addPlayerToPlayersInMainLobbyList(player);
            }
            MainLobbyScoreboard.setScoreboardPlayer(player);
            player.getInventory().clear();
            Location loc = new Location(Bukkit.getWorld((String) GlavtoyGame.getMapsConfig().get("main-lobby.world")),
                    GlavtoyGame.getMapsConfig().getDouble("main-lobby.x"),
                    GlavtoyGame.getMapsConfig().getDouble("main-lobby.y"),
                    GlavtoyGame.getMapsConfig().getDouble("main-lobby.z"),
                    GlavtoyGame.getMapsConfig().getInt("main-lobby.yaw"),
                    GlavtoyGame.getMapsConfig().getInt("main-lobby.pitch"));
            player.teleport(loc);
            System.out.println("[" + Game.getMapName(game) + "] -> " + player.getName() + " вышел (" + Game.getPlayersAmountInGame(game) + "/" + GlavtoyGame.getMapsConfig().getInt("maps." + Game.getMapName(game) + ".max-players") + ")");
        }
    }
}
