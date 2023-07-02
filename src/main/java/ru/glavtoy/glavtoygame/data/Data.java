package ru.glavtoy.glavtoygame.data;

import org.bukkit.entity.Player;
import ru.glavtoy.glavtoygame.GlavtoyGame;

import java.util.HashMap;

public class Data {

    private static HashMap<String, Integer> balanceBase = new HashMap<String, Integer>();
    private static HashMap<String, Integer> winsBase = new HashMap<String, Integer>();
    private static HashMap<String, Integer> killsBase = new HashMap<String, Integer>();
    private static HashMap<String, Integer> levelBase = new HashMap<String, Integer>();
    private static HashMap<String, Integer> expBase = new HashMap<String, Integer>();

    public static void addPlayerToBalanceBase(Player player, int amount) {
        balanceBase.put(player.getName(), amount);
    }

    public static void addPlayerToWinsBase(Player player, int amount) {
        winsBase.put(player.getName(), amount);
    }

    public static void addPlayerToKillsBase(Player player, int amount) {
        killsBase.put(player.getName(), amount);
    }

    public static void addPlayerToLevelBase(Player player, int amount) {
        levelBase.put(player.getName(), amount);
    }

    public static void addPlayerToExpBase(Player player, int amount) {
        expBase.put(player.getName(), amount);
    }

    public static HashMap getBalanceBase() {
        return balanceBase;
    }

    public static HashMap getWinsBase() {
        return winsBase;
    }

    public static HashMap getKillsBase() {
        return killsBase;
    }

    public static HashMap getLevelBase() {
        return levelBase;
    }

    public static HashMap getExpBase() {
        return expBase;
    }

    public static void writeBalanceToPlayerInDataConfig(Player player, int balance) {
        GlavtoyGame.getDataConfig().createSection("users." + player.getName() + ".coins");
        GlavtoyGame.getDataConfig().set("users." + player.getName() + ".coins", balance);
        GlavtoyGame.saveDataConfig();
    }

    public static void writeBalanceToPlayerIsExistInDataConfig(Player player, int balance) {
        GlavtoyGame.getDataConfig().set("users." + player.getName() + ".coins", balance);
        GlavtoyGame.saveDataConfig();
    }

    public static void writeWinsToPlayerInDataConfig(Player player, int wins) {
        GlavtoyGame.getDataConfig().createSection("users." + player.getName() + ".wins");
        GlavtoyGame.getDataConfig().set("users." + player.getName() + ".wins", wins);
        GlavtoyGame.saveDataConfig();
    }

    public static void writeWinsToPlayerIsExistInDataConfig(Player player, int wins) {
        GlavtoyGame.getDataConfig().set("users." + player.getName() + ".wins", wins);
        GlavtoyGame.saveDataConfig();
    }

    public static void writeKillsToPlayerInDataConfig(Player player, int kills) {
        GlavtoyGame.getDataConfig().createSection("users." + player.getName() + ".kills");
        GlavtoyGame.getDataConfig().set("users." + player.getName() + ".kills", kills);
        GlavtoyGame.saveDataConfig();
    }

    public static void writeKillsToPlayerIsExistInDataConfig(Player player, int kills) {
        GlavtoyGame.getDataConfig().set("users." + player.getName() + ".kills", kills);
        GlavtoyGame.saveDataConfig();
    }

    public static void writeLevelToPlayerInDataConfig(Player player, int level) {
        GlavtoyGame.getDataConfig().createSection("users." + player.getName() + ".level");
        GlavtoyGame.getDataConfig().set("users." + player.getName() + ".level", level);
        GlavtoyGame.saveDataConfig();
    }

    public static void writeLevelToPlayerIsExistInDataConfig(Player player, int level) {
        GlavtoyGame.getDataConfig().set("users." + player.getName() + ".level", level);
        GlavtoyGame.saveDataConfig();
    }

    public static void writeExpToPlayerInDataConfig(Player player, int exp) {
        GlavtoyGame.getDataConfig().createSection("users." + player.getName() + ".exp");
        GlavtoyGame.getDataConfig().set("users." + player.getName() + ".exp", exp);
        GlavtoyGame.saveDataConfig();
    }

    public static void writeExpToPlayerIsExistInDataConfig(Player player, int exp) {
        GlavtoyGame.getDataConfig().set("users." + player.getName() + ".exp", exp);
        GlavtoyGame.saveDataConfig();
    }

    public static boolean playerIsInBalanceBase(Player player) {
        return balanceBase.containsKey(player);
    }

    public static boolean playerIsInWinsBase(Player player) {
        return winsBase.containsKey(player);
    }

    public static boolean playerIsInKillsBase(Player player) {
        return killsBase.containsKey(player);
    }

    public static boolean playerIsInLevelBase(Player player) {
        return levelBase.containsKey(player);
    }

    public static boolean playerIsInExpBase(Player player) {
        return expBase.containsKey(player);
    }

    public static int getBalanceFromPlayerInDataConfig(Player player) {
        return GlavtoyGame.getDataConfig().getInt("users." + player.getName() + ".coins");
    }

    public static int getKillsFromPlayerInDataConfig(Player player) {
        return GlavtoyGame.getDataConfig().getInt("users." + player.getName() + ".kills");
    }

    public static int getWinsFromPlayerInDataConfig(Player player) {
        return GlavtoyGame.getDataConfig().getInt("users." + player.getName() + ".wins");
    }

    public static int getLevelFromPlayerInDataConfig(Player player) {
        return GlavtoyGame.getDataConfig().getInt("users." + player.getName() + ".level");
    }

    public static int getExpFromPlayerInDataConfig(Player player) {
        return GlavtoyGame.getDataConfig().getInt("users." + player.getName() + ".exp");
    }

    public static int getBalance(Player player) {
        return balanceBase.get(player.getName());
    }

    public static int getWins(Player player) {
        return winsBase.get(player.getName());
    }

    public static int getKills(Player player) {
        return killsBase.get(player.getName());
    }

    public static int getLevel(Player player) {
        return levelBase.get(player.getName());
    }

    public static int getExp(Player player) {
        return expBase.get(player.getName());
    }
}
