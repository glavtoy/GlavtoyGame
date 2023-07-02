package ru.glavtoy.glavtoygame.runnable;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.glavtoy.glavtoygame.GlavtoyGame;
import ru.glavtoy.glavtoygame.game.Game;

public class MainLobbyRunnable {
    private Game game;

    public MainLobbyRunnable(Game game) {
        this.game = game;
    }

    public static void startRunnable() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Game.getPlayersFromMainLobbyAmount() == 0) {
                    cancel();
                } else {
                    if (!(Game.getPlayersFromMainLobbyList().isEmpty())) {
                        //Game.sendActionbarToAllPlayersInMainLobbyList();
                        //Game.sendAllPlayersMainLobbyScoreboard();
                        Game.sendAllPlayersHisLevel();
                        Game.updateScoreboardInMainLobbyForAllPlayers();
                    }
                }
            }
        }.runTaskTimer(GlavtoyGame.getInstance(), 0, 20);
    }
}
