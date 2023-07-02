package ru.glavtoy.glavtoygame.runnable;

import org.bukkit.scheduler.BukkitRunnable;
import ru.glavtoy.glavtoygame.GlavtoyGame;
import ru.glavtoy.glavtoygame.game.Game;
import ru.glavtoy.glavtoygame.scoreboard.MainLobbyScoreboard;
import ru.glavtoy.glavtoygame.scoreboard.WaitingLobbyScoreboard;
import ru.glavtoy.glavtoygame.util.ColorUtil;

public class WaitingRunnable {

    private Game game;

    public WaitingRunnable(Game game) {
        this.game = game;
    }


    public static void startWaiting(Game game) {
        new BukkitRunnable() {
            @Override
            public void run() {
                    if (Game.getPlayersAmountInGame(game) == Game.getMinPlayers(game)) {
                        cancel();
                        Game.hideWaitingBarForAllPlayers(game);
                        CountdownRunnable.startCountdown(game);
                    }
                    else if (Game.getPlayersAmountInGame(game) == 0) {
                        cancel();
                        Game.hideWaitingBarForAllPlayers(game);
                    }
                    else {
                        Game.hideCountdownBarForAllPlayers(game);
                        Game.setTitleWaitingBar(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("bossbar-wait").replace("{p}", String.valueOf(Game.getPlayers(game))).replace("{max}", String.valueOf(Game.getMaxPlayers(game)))));
                        Game.hideWaitingBarForAllPlayers(game);
                        Game.showWaitingBarForAllPlayers(game);
                        WaitingLobbyScoreboard.updateScoreboards(WaitingLobbyScoreboard.getScoreboardAtGame(game),
                                WaitingLobbyScoreboard.getObjectiveAtGame(game), game);
                    }
            }
        }.runTaskTimer(GlavtoyGame.getInstance(), 0, 20);
    }
}
