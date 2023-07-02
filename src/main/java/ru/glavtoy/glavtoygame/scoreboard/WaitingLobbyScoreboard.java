package ru.glavtoy.glavtoygame.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import ru.glavtoy.glavtoygame.game.Game;

import java.util.HashMap;

public class WaitingLobbyScoreboard {
    private Scoreboard scoreboard;
    private static HashMap<Game, Scoreboard> baseSco = new HashMap<Game, Scoreboard>();
    private static HashMap<Game, Objective> baseObj = new HashMap<Game, Objective>();
    private static HashMap<Game, Integer> oldAmountOfPlayersBase = new HashMap<Game, Integer>();
    private static HashMap<Game, Integer> oldCountdownBase = new HashMap<Game, Integer>();
    private Objective objective;

    public WaitingLobbyScoreboard(Game game) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("mainLobby", "dummy");
        this.objective.setDisplayName(ChatColor.YELLOW + "§6§lGlavtoyGame");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score1 = this.objective.getScore("§etensorshop.ru");
        score1.setScore(0);
        Score score2 = this.objective.getScore("   ");
        score2.setScore(1);
        Score score3 = this.objective.getScore("§fКарта: §6" + Game.getMapName(game));
        score3.setScore(2);
        Score score4 = this.objective.getScore("§fИгроков: §a" + Game.getPlayers(game) + "§7/§a" + Game.getMaxPlayers(game));
        score4.setScore(3);
        Score score5 = this.objective.getScore("  ");
        score5.setScore(4);
        Score score6 = this.objective.getScore("§fДо начала игры: §e" + Game.getCountdown(game) + "с");
        score6.setScore(5);
        Score score7 = this.objective.getScore(" ");
        score7.setScore(6);
    }

    public static void setScoreboardForAllPlayers(Game game) {
        WaitingLobbyScoreboard waitingLobbyScoreboard = new WaitingLobbyScoreboard(game);
        baseSco.put(game, waitingLobbyScoreboard.scoreboard);
        baseObj.put(game, waitingLobbyScoreboard.objective);
        oldCountdownBase.put(game, Game.getCountdown(game));
        Game.sendScoreboardForAllPlayers(getScoreboardAtGame(game), game);
    }

    public static int getAmountOfPlayersInOldBase(Game game) {
        return oldAmountOfPlayersBase.get(game);
    }

    public static void putAmountOfPlayersToOldBase(Game game, int amount) {
        oldAmountOfPlayersBase.put(game, amount);
    }

    public static int getCountdownInOldBase(Game game) {
        return oldCountdownBase.get(game);
    }

    public static void putCountdownToOldBase(Game game, int countdown) {
        oldCountdownBase.put(game, countdown);
    }

    public static Scoreboard getScoreboardAtGame(Game game) {
        return baseSco.get(game);
    }

    public static Objective getObjectiveAtGame(Game game) {
        return baseObj.get(game);
    }

    public static void updateScoreboards(Scoreboard scoreboard, Objective objective, Game game) {
        /*if (Game.getPlayersAmountInGame(game) != oldAmountOfPlayersBase.get(game)) {
            System.out.println("Изменение количества игроков в скорборде..");
            scoreboard.resetScores("§fИгроков: §a" + oldAmountOfPlayersBase.get(game) + "§7/§a" + Game.getMaxPlayers(game));
            objective.getScore("§fИгроков: §a" + Game.getPlayers(game) + "§7/§a" + Game.getMaxPlayers(game)).setScore(3);
            Game.sendScoreboardForAllPlayers(scoreboard, game);
        }*/
        if (Game.getCountdown(game) != oldCountdownBase.get(game)) {
            scoreboard.resetScores("§fИгроков: §a" + oldAmountOfPlayersBase.get(game) + "§7/§a" + Game.getMaxPlayers(game));
            objective.getScore("§fИгроков: §a" + Game.getPlayers(game) + "§7/§a" + Game.getMaxPlayers(game)).setScore(3);
            scoreboard.resetScores("§fДо начала игры: §e" + oldCountdownBase.get(game) + "с");
            scoreboard.resetScores("§fДо начала игры: §e" + Game.getCountdown(game) + "с");
            objective.getScore("§fДо начала игры: §e" + Game.getCountdown(game) + "с").setScore(5);
            Game.sendScoreboardForAllPlayers(scoreboard, game);
        }
    }
}
