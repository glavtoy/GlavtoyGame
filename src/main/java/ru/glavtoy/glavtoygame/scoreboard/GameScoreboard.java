package ru.glavtoy.glavtoygame.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import ru.glavtoy.glavtoygame.game.Game;

import java.util.HashMap;

public class GameScoreboard {

    private Scoreboard scoreboard;
    private static HashMap<Player, Scoreboard> baseSco = new HashMap<Player, Scoreboard>();
    private static HashMap<Player, Objective> baseObj = new HashMap<Player, Objective>();
    private static HashMap<Game, Integer> oldMinutesBase = new HashMap<Game, Integer>();
    private static HashMap<Game, Integer> oldSecondsBase = new HashMap<Game, Integer>();
    private static HashMap<Player, Integer> oldKillsBase = new HashMap<Player, Integer>();
    private static HashMap<Player, Integer> oldBalanceBase = new HashMap<Player, Integer>();
    private static HashMap<Game, Player> oldKillsLeaderBase = new HashMap<Game, Player>();
    private Objective objective;

    public GameScoreboard(Game game, Player player) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("mainLobby", "dummy");
        this.objective.setDisplayName(ChatColor.YELLOW + "§6§lGlavtoyGame");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score1 = this.objective.getScore("§etensorshop.ru");
        score1.setScore(0);
        Score score2 = this.objective.getScore("    ");
        score2.setScore(1);
        Score score3 = this.objective.getScore("§fДенег: §a$" + Game.getBalance(game, player));
        score3.setScore(2);
        Score score4 = this.objective.getScore("§fУбийств: §c" + Game.getPlayerKillsInGame(game, player));
        score4.setScore(3);
        Score score5 = this.objective.getScore("   ");
        score5.setScore(4);
        Score score6 = this.objective.getScore("§6" + Game.getKillsLeader(game));
        score6.setScore(5);
        Score score7 = this.objective.getScore("§fГлавный убийца:");
        score7.setScore(6);
        Score score8 = this.objective.getScore("  ");
        score8.setScore(7);
        Score score9 = this.objective.getScore("§6" + Game.getMinutes(game) + "мин " + Game.getSeconds(game) + "с");
        score9.setScore(8);
        Score score10 = this.objective.getScore("§fДо конца игры:");
        score10.setScore(9);
        Score score11 = this.objective.getScore(" ");
        score11.setScore(10);
    }

    public static void setScoreboardToPlayer(Game game, Player player) {
        GameScoreboard gameScoreboard = new GameScoreboard(game, player);
        baseSco.put(player, gameScoreboard.scoreboard);
        baseObj.put(player, gameScoreboard.objective);
        player.setScoreboard(getScoreboardAtPlayer(player));
    }

    public static Scoreboard getScoreboardAtPlayer(Player player) {
        return baseSco.get(player);
    }

    public static Objective getObjectiveAtPlayer(Player player) {
        return baseObj.get(player);
    }

    public static void addPlayerToOldKillsBase(Player player, int kills) {
        oldKillsBase.put(player, kills);
    }

    public static int getKillsFromOldKillsBaseAtPlayer(Player player) {
        return oldKillsBase.get(player);
    }

    public static void addPlayerToOldBalanceBase(Player player, int balance) {
        oldBalanceBase.put(player, balance);
    }

    public static int getBalanceFromOldBalanceBaseAtPlayer(Player player) {
        return oldBalanceBase.get(player);
    }

    public static void addKillsLeaderToOldBase(Game game, Player player) {
        oldKillsLeaderBase.put(game, player);
    }

    public static Player getKillsLeaderFromOldBase(Game game) {
        return oldKillsLeaderBase.get(game);
    }

    public static void addSecondsToOldBase(Game game, int seconds) {
        oldSecondsBase.put(game, seconds);
    }

    public static int getSecondsFromOldBase(Game game) {
        return oldSecondsBase.get(game);
    }

    public static void addMinutesToOldBase(Game game, int minutes) {
        oldMinutesBase.put(game, minutes);
    }

    public static int getMinutesFromOldBase(Game game) {
        return oldMinutesBase.get(game);
    }

    public static void updateScoreboards(Scoreboard scoreboard, Objective objective, Game game, Player player) {
        if (Game.getPlayerKillsInGame(game, player) != oldKillsBase.get(player) &&
        Game.getBalance(game, player) != oldBalanceBase.get(player) && Bukkit.getPlayer(Game.getKillsLeader(game)) != oldKillsLeaderBase.get(game)) {
            scoreboard.resetScores("§fУбийств: §c" + oldKillsBase.get(player));
            objective.getScore("§fУбийств: §c" + Game.getPlayerKillsInGame(game, player)).setScore(3);
            scoreboard.resetScores("§fДенег: §a$" + oldBalanceBase.get(player));
            objective.getScore("§fДенег: §a$" + Game.getBalance(game, player)).setScore(2);
            scoreboard.resetScores("§6" + oldMinutesBase.get(game) + "мин " + oldSecondsBase.get(game) + "с");
            objective.getScore("§6" + Game.getMinutes(game) + "мин " + Game.getSeconds(game) + "с").setScore(8);
            scoreboard.resetScores("§6" + oldKillsLeaderBase.get(game).getName());
            objective.getScore("§6" + Game.getKillsLeader(game)).setScore(5);
            player.setScoreboard(scoreboard);
        } else if (Game.getPlayerKillsInGame(game, player) == oldKillsBase.get(player) &&
                Game.getBalance(game, player) == oldBalanceBase.get(player) && Bukkit.getPlayer(Game.getKillsLeader(game)) != oldKillsLeaderBase.get(game)) {
            scoreboard.resetScores("§6" + oldMinutesBase.get(game) + "мин " + oldSecondsBase.get(game) + "с");
            objective.getScore("§6" + Game.getMinutes(game) + "мин " + Game.getSeconds(game) + "с").setScore(8);
            scoreboard.resetScores("§6" + oldKillsLeaderBase.get(game).getName());
            objective.getScore("§6" + Game.getKillsLeader(game)).setScore(5);
            player.setScoreboard(scoreboard);
        } else if (Game.getPlayerKillsInGame(game, player) == oldKillsBase.get(player) &&
            Game.getBalance(game, player) == oldBalanceBase.get(player) && Bukkit.getPlayer(Game.getKillsLeader(game)) == oldKillsLeaderBase.get(game)) {
            scoreboard.resetScores("§6" + oldMinutesBase.get(game) + "мин " + oldSecondsBase.get(game) + "с");
            objective.getScore("§6" + Game.getMinutes(game) + "мин " + Game.getSeconds(game) + "с").setScore(8);
            player.setScoreboard(scoreboard);
    }
    }
}
