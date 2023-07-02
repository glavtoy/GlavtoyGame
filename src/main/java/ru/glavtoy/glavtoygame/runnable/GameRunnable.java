package ru.glavtoy.glavtoygame.runnable;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.glavtoy.glavtoygame.GlavtoyGame;
import ru.glavtoy.glavtoygame.data.Data;
import ru.glavtoy.glavtoygame.game.Game;
import ru.glavtoy.glavtoygame.game.GameManager;
import ru.glavtoy.glavtoygame.scoreboard.GameScoreboard;
import ru.glavtoy.glavtoygame.scoreboard.MainLobbyScoreboard;
import ru.glavtoy.glavtoygame.util.ColorUtil;

public class GameRunnable {

    private Game game;

    public GameRunnable(Game game) {
        this.game = game;
    }

    public static void startGame(Game game) {
        new BukkitRunnable() {
            @Override
            public void run() {
                    if (Game.getPlayersAmountInGame(game) == 0) {
                        cancel();
                        Game.recreateGame(game);
                        GameManager.deleteGameFromBusy(game);
                    }
                    else if (Game.getDuration(game) == 0) {
                        Game.hideGameBarForAllPlayers(game);
                        Game.sendMessageForAllPlayers(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString(
                                "win-message-2"
                        ).replace("{player}", Game.getKillsLeader(game))));
                        Player player = Bukkit.getPlayer(Game.getKillsLeader(game));
                        player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString(
                                "win-message-1"
                        )).replace("{coins}", String.valueOf(GlavtoyGame.getInstance().getConfig().getInt("coins-reward-for-win")))
                                .replace("{exp}", String.valueOf(GlavtoyGame.getInstance().getConfig().getInt("exp-reward-for-win"))));
                        Game.setDuration(game, Game.getDuration(game) - 1);
                    } else if (Game.getDuration(game) <= -5) {
                        cancel();
                        Game.setMaxHealthForAllPlayers(game);
                        Game.clearInventoryForAllPlayers(game);
                        Player winner = Bukkit.getPlayer(Game.getKillsLeader(game));
                        int beforeLevel = Data.getLevel(winner);
                        MainLobbyScoreboard.addPlayerToLevelBaseOld(winner, Data.getLevel(winner));
                        MainLobbyScoreboard.addPlayerToWinsBaseOld(winner, Data.getWins(winner));
                        MainLobbyScoreboard.addPlayerToBalanceBaseOld(winner, Data.getBalance(winner));
                        Data.addPlayerToWinsBase(Bukkit.getPlayer(Game.getKillsLeader(game)), Data.getWins(Bukkit.getPlayer(Game.getKillsLeader(game))) + 1);
                        Data.writeWinsToPlayerIsExistInDataConfig(Bukkit.getPlayer(Game.getKillsLeader(game)), Data.getWins(Bukkit.getPlayer(Game.getKillsLeader(game))));
                        Data.addPlayerToBalanceBase(Bukkit.getPlayer(Game.getKillsLeader(game)), Data.getBalance(Bukkit.getPlayer(Game.getKillsLeader(game))) + GlavtoyGame.getInstance().getConfig().getInt("coins-reward-for-win"));
                        Data.writeBalanceToPlayerIsExistInDataConfig(Bukkit.getPlayer(Game.getKillsLeader(game)), Data.getBalance(Bukkit.getPlayer(Game.getKillsLeader(game))));
                        Data.addPlayerToExpBase(Bukkit.getPlayer(Game.getKillsLeader(game)), Data.getExp(Bukkit.getPlayer(Game.getKillsLeader(game))) + GlavtoyGame.getInstance().getConfig().getInt("exp-reward-for-win"));
                        Data.writeExpToPlayerIsExistInDataConfig(Bukkit.getPlayer(Game.getKillsLeader(game)), Data.getExp(Bukkit.getPlayer(Game.getKillsLeader(game))));
                        GlavtoyGame.saveDataConfig();
                        Game.teleportAllPlayersToMainLobby(game);
                        Game.addAllPlayersToMainLobbyPlayersList(game);
                        Game.removeAllPlayersFromPlayerInGameList(game);
                        Game.sendAllPlayersMainLobbyScoreboard();
                        Game.recreateGame(game);
                        int winnerExp = Data.getExp(winner);
                        for (String key : GlavtoyGame.getLevelConfig().getConfigurationSection("levels").getKeys(false)) {
                            int needExpToNLevel = GlavtoyGame.getLevelConfig().getInt("levels." + key + ".exp");
                            if (winnerExp >= needExpToNLevel) {
                                Data.writeLevelToPlayerIsExistInDataConfig(winner, Integer.parseInt(key));
                                Data.addPlayerToLevelBase(winner, Integer.parseInt(key));
                            }
                        }
                        int afterLevel = Data.getLevel(winner);
                        if (afterLevel > beforeLevel) {
                            winner.sendTitle(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("level-up-title")),
                                    ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("level-up-subtitle")
                                            .replace("{level}", String.valueOf(Data.getLevel(winner)))));
                            winner.playSound(winner.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                        }
                        if (Game.getPlayersFromMainLobbyAmount() == 0) {
                            Game.addAllPlayersToMainLobbyAmount(game);
                            MainLobbyRunnable.startRunnable();
                        } else {
                            Game.addAllPlayersToMainLobbyAmount(game);
                        }
                    } else if (Game.getDuration(game) > 0) {
                        Game.sendAllPlayersHisStats(game);
                        GameScoreboard.addMinutesToOldBase(game, Game.getMinutes(game));
                        GameScoreboard.addSecondsToOldBase(game, Game.getSeconds(game));
                        Game.setMinutes(game, (Game.getDuration(game) % 3600 - Game.getDuration(game) % 3600 % 60) / 60);
                        Game.setSeconds(game, Game.getDuration(game) % 3600 % 60);
                        if (Game.getKillBase(game).get(Bukkit.getPlayer(Game.getKillsLeader(game))) != null) {
                            Game.setTitleGameBar(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("bossbar-game").replace("{m}", String.valueOf(Game.getMinutes(game))).replace("{s}", String.valueOf(Game.getSeconds(game))).replace("{leader}", Game.getKillsLeader(game)).replace("{kills}", String.valueOf(Game.getKillBase(game).get(Bukkit.getPlayer(Game.getKillsLeader(game)))))));
                        } else {
                            Game.setTitleGameBar(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("bossbar-game").replace("{m}", String.valueOf(Game.getMinutes(game))).replace("{s}", String.valueOf(Game.getSeconds(game))).replace("{leader}", Game.getKillsLeader(game)).replace("{kills}", String.valueOf(0))));
                        }
                        Game.hideGameBarForAllPlayers(game);
                        Game.showGameBarForAllPlayers(game);
                        Game.setDuration(game, Game.getDuration(game) - 1);
                        Game.updateGameScoreboardForAllPlayers(game);
                    } else if (Game.getDuration(game) < 0) {
                        Game.pullFireworkByWinner(game);
                        Game.sendAllPlayersHisStats(game);
                        Game.setDuration(game, Game.getDuration(game) - 1);
            }
            }
        }.runTaskTimer(GlavtoyGame.getInstance(), 0, 20);
    }
}

