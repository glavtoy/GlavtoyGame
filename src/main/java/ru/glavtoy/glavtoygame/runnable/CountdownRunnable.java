package ru.glavtoy.glavtoygame.runnable;

import org.bukkit.scheduler.BukkitRunnable;
import ru.glavtoy.glavtoygame.GlavtoyGame;
import ru.glavtoy.glavtoygame.data.Data;
import ru.glavtoy.glavtoygame.game.Game;
import ru.glavtoy.glavtoygame.game.GameManager;
import ru.glavtoy.glavtoygame.menu.ShopMenu;
import ru.glavtoy.glavtoygame.scoreboard.GameScoreboard;
import ru.glavtoy.glavtoygame.scoreboard.MainLobbyScoreboard;
import ru.glavtoy.glavtoygame.scoreboard.WaitingLobbyScoreboard;
import ru.glavtoy.glavtoygame.util.ColorUtil;

public class CountdownRunnable {

    private Game game;

    public CountdownRunnable(Game game) {
        this.game = game;
    }

    public static void startCountdown(Game game) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Game.getPlayersAmountInGame(game) < Game.getMinPlayers(game)) {
                    cancel();
                    Game.setLevelCountdownForAllPlayers(game, 0);
                    Game.setCountdown(game, GlavtoyGame.getInstance().getConfig().getInt("countdown") + 1);
                    Game.hideCountdownBarForAllPlayers(game);
                    WaitingRunnable.startWaiting(game);
                    WaitingLobbyScoreboard.updateScoreboards(WaitingLobbyScoreboard.getScoreboardAtGame(game),
                            WaitingLobbyScoreboard.getObjectiveAtGame(game), game);
                }
                if (Game.getPlayersAmountInGame(game) == 0) {
                    cancel();
                    Game.setCountdown(game, GlavtoyGame.getInstance().getConfig().getInt("countdown"));
                }
                if (Game.getCountdown(game) == 15) {
                    Game.playCountdownSoundForAllPlayers(game);
                    Game.sendTitleForAllPlayers(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("title-countdown.15")));
                    Game.sendMessageForAllPlayers(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("chat-countdown").replace("{count}", String.valueOf(Game.getCountdown(game)))));
                }
                if (Game.getCountdown(game) == 10) {
                    Game.playCountdownSoundForAllPlayers(game);
                    Game.sendTitleForAllPlayers(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("title-countdown.10")));
                    Game.sendMessageForAllPlayers(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("chat-countdown").replace("{count}", String.valueOf(Game.getCountdown(game)))));
                }
                if (Game.getCountdown(game) == 5) {
                    Game.playCountdownSoundForAllPlayers(game);
                    Game.sendTitleForAllPlayers(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("title-countdown.5")));
                    Game.sendMessageForAllPlayers(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("chat-countdown").replace("{count}", String.valueOf(Game.getCountdown(game)))));
                }
                if (Game.getCountdown(game) == 4) {
                    Game.playCountdownSoundForAllPlayers(game);
                    Game.sendTitleForAllPlayers(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("title-countdown.4")));
                    Game.sendMessageForAllPlayers(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("chat-countdown").replace("{count}", String.valueOf(Game.getCountdown(game)))));
                }
                if (Game.getCountdown(game) == 3) {
                    Game.playCountdownSoundForAllPlayers(game);
                    Game.sendTitleForAllPlayers(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("title-countdown.3")));
                    Game.sendMessageForAllPlayers(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("chat-countdown").replace("{count}", String.valueOf(Game.getCountdown(game)))));
                }
                if (Game.getCountdown(game) == 2) {
                    Game.playCountdownSoundForAllPlayers(game);
                    Game.sendTitleForAllPlayers(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("title-countdown.2")));
                    Game.sendMessageForAllPlayers(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("chat-countdown").replace("{count}", String.valueOf(Game.getCountdown(game)))));
                }
                if (Game.getCountdown(game) == 1) {
                    Game.playCountdownSoundForAllPlayers(game);
                    Game.sendTitleForAllPlayers(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("title-countdown.1")));
                    Game.sendMessageForAllPlayers(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("chat-countdown").replace("{count}", String.valueOf(Game.getCountdown(game)))));
                }
                    Game.setTitleCountdownBar(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("bossbar-countdown").replace("{s}", String.valueOf(Game.getCountdown(game)))));
                    Game.hideCountdownBarForAllPlayers(game);
                    Game.showCountdownBarForAllPlayers(game);
                    Game.setLevelCountdownForAllPlayers(game, Game.getCountdown(game));
                    WaitingLobbyScoreboard.updateScoreboards(WaitingLobbyScoreboard.getScoreboardAtGame(game),
                        WaitingLobbyScoreboard.getObjectiveAtGame(game), game);
                    WaitingLobbyScoreboard.putCountdownToOldBase(game, Game.getCountdown(game));
                    Game.setCountdown(game, Game.getCountdown(game) - 1);
                if (Game.getCountdown(game) <= 0) {
                    Game.setLevelCountdownForAllPlayers(game,0);
                    Game.playStartSoundForAllPlayers(game);
                    cancel();
                    Game.clearInventoryForAllPlayers(game);
                    Game.sendTitleForAllPlayers(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("title-countdown.start")));
                    Game.hideCountdownBarForAllPlayers(game);
                    Game.removeAllPlayersFromWaitingLobbyList(game);
                    GameManager.addGameToBusy(game);
                    Game.teleportPlayersAfterLobbyToSpawns(game);
                    Game.giveOpenShopItemForAllPlayers(game, ShopMenu.getOpenShopItem(), GlavtoyGame.getInstance().getConfig().getInt("shop-menu.open-item.slot"));
                    Game.giveStartKitForAllPlayers(game);
                    Game.sendMessageForAllPlayers(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("game-start")));
                    Game.removeAllPlayersFromPlayerInLobbysList(game);
                    Game.addAllPlayersToPlayerInGameList(game);
                    Game.addAllPlayersToKillBase(game);
                    Game.addAllPlayersToOldKillsBaseInGameScoreboard(game);
                    Game.addAllPlayersToOldBalanceBaseInGameScoreboard(game);
                    Game.setGameScoreboardForAllPlayers(game);
                    GameRunnable.startGame(game);
                }
            }
        }.runTaskTimer(GlavtoyGame.getInstance(), 0, 20);
    }
}
