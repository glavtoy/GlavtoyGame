package ru.glavtoy.glavtoygame.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import ru.glavtoy.glavtoygame.data.Data;

import java.util.HashMap;

public class MainLobbyScoreboard {

    private Scoreboard scoreboard;
    private static HashMap<Player, Scoreboard> baseSco = new HashMap<Player, Scoreboard>();
    private static HashMap<Player, Objective> baseObj = new HashMap<Player, Objective>();
    private static HashMap<Player, Integer> balanceBaseOld = new HashMap<Player, Integer>();
    private static HashMap<Player, Integer> killsBaseOld = new HashMap<Player, Integer>();
    private static HashMap<Player, Integer> winsBaseOld = new HashMap<Player, Integer>();
    private static HashMap<Player, Integer> levelBaseOld = new HashMap<Player, Integer>();
    private Objective objective;

    public MainLobbyScoreboard(Player player) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("mainLobby", "dummy");
        this.objective.setDisplayName(ChatColor.YELLOW + "§6§lGlavtoyGame");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score1 = this.objective.getScore("§etensorshop.ru");
        score1.setScore(0);
        Score score2 = this.objective.getScore("    ");
        score2.setScore(1);
        Score score3 = this.objective.getScore("§fМонет: §6" + Data.getBalance(player));
        score3.setScore(2);
        Score score4 = this.objective.getScore("   ");
        score4.setScore(3);
        Score score5 = this.objective.getScore("§fПобед: §a" + Data.getWins(player));
        score5.setScore(4);
        Score score6 = this.objective.getScore("§fУбийств: §c" + Data.getKills(player));
        score6.setScore(5);
        Score score7 = this.objective.getScore("  ");
        score7.setScore(6);
        Score score8 = this.objective.getScore("§fВаш уровень: §e" + Data.getLevel(player));
        score8.setScore(7);
        Score score9 = this.objective.getScore(" ");
        score9.setScore(8);
    }

    public static void addPlayerToBalanceBaseOld(Player player, int balance) {
        balanceBaseOld.put(player, balance);
    }

    public static void addPlayerToKillsBaseOld(Player player, int kills) {
        killsBaseOld.put(player, kills);
    }

    public static void addPlayerToWinsBaseOld(Player player, int wins) {
        winsBaseOld.put(player, wins);
    }

    public static void addPlayerToLevelBaseOld(Player player, int level) {
        levelBaseOld.put(player, level);
    }

    public static int getBalancePlayerAtOldBase(Player player) {
        return balanceBaseOld.get(player);
    }

    public static int getKillsPlayerAtOldBase(Player player) {
        return killsBaseOld.get(player);
    }

    public static int getWinsPlayerAtOldBase(Player player) {
        return winsBaseOld.get(player);
    }

    public static int getLevelPlayerAtOldBase(Player player) {
        return levelBaseOld.get(player);
    }

    public static void setScoreboardPlayer(Player player) {
        MainLobbyScoreboard mainLobbyScoreboard = new MainLobbyScoreboard(player);
        baseSco.put(player, mainLobbyScoreboard.scoreboard);
        baseObj.put(player, mainLobbyScoreboard.objective);
        player.setScoreboard(getScoreboardAtPlayer(player));
    }

    public static Scoreboard getScoreboardAtPlayer(Player player) {
        return baseSco.get(player);
    }

    public static Objective getObjectiveAtPlayer(Player player) {
        return baseObj.get(player);
    }

    public static void updateScoreboards(Scoreboard scoreboard, Objective objective, Player player) {
        if (Data.getBalance(player) > balanceBaseOld.get(player) && Data.getWins(player) > winsBaseOld.get(player) &&
                Data.getKills(player) > killsBaseOld.get(player) && Data.getLevel(player) <= levelBaseOld.get(player)) {
            scoreboard.resetScores("§fМонет: §6" + balanceBaseOld.get(player));
            objective.getScore("§fМонет: §6" + Data.getBalance(player)).setScore(2);
            scoreboard.resetScores("§fПобед: §a" + winsBaseOld.get(player));
            objective.getScore("§fПобед: §a" + Data.getWins(player)).setScore(4);
            scoreboard.resetScores("§fУбийств: §c" + killsBaseOld.get(player));
            objective.getScore("§fУбийств: §c" + Data.getKills(player)).setScore(5);
            player.setScoreboard(scoreboard);
        }
        else if (Data.getBalance(player) > balanceBaseOld.get(player) && Data.getWins(player) > winsBaseOld.get(player) &&
                Data.getKills(player) > killsBaseOld.get(player) && Data.getLevel(player) > levelBaseOld.get(player)) {
            scoreboard.resetScores("§fМонет: §6" + balanceBaseOld.get(player));
            objective.getScore("§fМонет: §6" + Data.getBalance(player)).setScore(2);
            scoreboard.resetScores("§fПобед: §a" + winsBaseOld.get(player));
            objective.getScore("§fПобед: §a" + Data.getWins(player)).setScore(4);
            scoreboard.resetScores("§fУбийств: §c" + killsBaseOld.get(player));
            objective.getScore("§fУбийств: §c" + Data.getKills(player)).setScore(5);
            scoreboard.resetScores("§fВаш уровень: §e" + levelBaseOld.get(player));
            objective.getScore("§fВаш уровень: §e" + Data.getLevel(player)).setScore(7);
            player.setScoreboard(scoreboard);
        }
        else if (Data.getWins(player) > winsBaseOld.get(player) && Data.getKills(player) <= killsBaseOld.get(player) &&
                Data.getLevel(player) <= levelBaseOld.get(player) && Data.getBalance(player) > balanceBaseOld.get(player)) {
            scoreboard.resetScores("§fМонет: §6" + balanceBaseOld.get(player));
            objective.getScore("§fМонет: §6" + Data.getBalance(player)).setScore(2);
            scoreboard.resetScores("§fПобед: §a" + winsBaseOld.get(player));
            objective.getScore("§fПобед: §a" + Data.getWins(player)).setScore(4);
            player.setScoreboard(scoreboard);
        }
        else if (Data.getKills(player) > killsBaseOld.get(player) && Data.getWins(player) <= winsBaseOld.get(player) &&
                Data.getLevel(player) <= levelBaseOld.get(player) && Data.getBalance(player) <= balanceBaseOld.get(player)) {
            scoreboard.resetScores("§fУбийств: §c" + killsBaseOld.get(player));
            objective.getScore("§fУбийств: §c" + Data.getKills(player)).setScore(5);
            player.setScoreboard(scoreboard);
        }
        else if (Data.getKills(player) <= killsBaseOld.get(player) && Data.getWins(player) <= winsBaseOld.get(player) &&
                Data.getLevel(player) <= levelBaseOld.get(player) && Data.getBalance(player) > balanceBaseOld.get(player)) {
            scoreboard.resetScores("§fМонет: §6" + balanceBaseOld.get(player));
            objective.getScore("§fМонет: §6" + Data.getBalance(player)).setScore(2);
            player.setScoreboard(scoreboard);
        }
    }
}
