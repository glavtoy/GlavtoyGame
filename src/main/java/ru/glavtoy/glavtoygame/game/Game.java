package ru.glavtoy.glavtoygame.game;

import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Scoreboard;
import ru.glavtoy.glavtoygame.GlavtoyGame;
import ru.glavtoy.glavtoygame.data.Data;
import ru.glavtoy.glavtoygame.menu.OpenShopItem;
import ru.glavtoy.glavtoygame.runnable.CountdownRunnable;
import ru.glavtoy.glavtoygame.runnable.GameRunnable;
import ru.glavtoy.glavtoygame.runnable.WaitingRunnable;
import ru.glavtoy.glavtoygame.scoreboard.GameScoreboard;
import ru.glavtoy.glavtoygame.scoreboard.MainLobbyScoreboard;
import ru.glavtoy.glavtoygame.scoreboard.WaitingLobbyScoreboard;
import ru.glavtoy.glavtoygame.util.ColorUtil;

import java.util.*;

public class Game {

    private World world;
    private int minPlayers;
    private int maxPlayers;
    private int players;
    private int minutes;
    private int seconds;
    private List<String> spawns;
    private List<Player> playersList;
    private boolean isEnd;
    private List<String> busySpawns;
    private static List<Player> playersInGameList = new ArrayList<Player>();
    private static List<Player> playersInMainLobbyList = new ArrayList<Player>();
    private static List<Player> playersInWaitingLobbyList = new ArrayList<Player>();
    private HashMap<Game, String> gameHash;
    private BossBar countdownBar;
    private BossBar waitingBar;
    private BossBar gameBar;
    private CountdownRunnable countdownRunnable;
    private GameRunnable gameRunnable;
    private WaitingRunnable waitingRunnable;
    private int countdown;
    private int kills;
    private int duration;
    private static int playersInMainLobbyAmount;
    private HashMap<Player, Integer> balanceBase;
    private HashMap<Player, Integer> killBase;
    private List<Player> leadersList;
    private static HashMap<Player, Game> playerGameBase = new HashMap<Player, Game>();
    private Player winner;
    private List<Player> endPlayers = new ArrayList<Player>();
    private Location waitingLobby;
    private static Material leaveItemMat = Material.getMaterial((String) GlavtoyGame.getInstance().getConfig().get("leave-item.item-id"));
    private static LeaveItem leaveItem = new LeaveItem(leaveItemMat, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("leave-item.custom-name")));

    public Game(World world, int minPlayers, int maxPlayers, List spawns, String mapName) {
        this.world = world;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.players = 0;
        this.spawns = spawns;
        this.isEnd = true;
        this.playersList = new ArrayList<Player>();
        this.busySpawns = new ArrayList<String>();
        this.gameHash = new HashMap<Game, String>();
        this.gameHash.put(this, mapName);
        this.countdownBar = Bukkit.createBossBar("", BarColor.RED, BarStyle.SOLID);
        this.waitingBar = Bukkit.createBossBar("", BarColor.RED, BarStyle.SOLID);
        this.gameBar = Bukkit.createBossBar("", BarColor.RED, BarStyle.SOLID);
        this.countdownRunnable = new CountdownRunnable(this);
        this.gameRunnable = new GameRunnable(this);
        this.waitingRunnable = new WaitingRunnable(this);
        this.countdown = GlavtoyGame.getInstance().getConfig().getInt("countdown");
        this.balanceBase = new HashMap<Player, Integer>();
        this.killBase = new HashMap<Player, Integer>();
        this.winner = null;
        this.minutes = 0;
        this.seconds = 0;
        this.kills = 0;
        this.leadersList = new ArrayList<Player>();
        this.endPlayers = new ArrayList<Player>();
        this.duration = GlavtoyGame.getInstance().getConfig().getInt("game-duration");
        this.waitingLobby = new Location(Bukkit.getWorld((String) GlavtoyGame.getMapsConfig().get("maps." + mapName + ".world")),
                GlavtoyGame.getMapsConfig().getInt("maps." + mapName + ".lobby.x"),
                GlavtoyGame.getMapsConfig().getInt("maps." + mapName + ".lobby.y"),
                GlavtoyGame.getMapsConfig().getInt("maps." + mapName + ".lobby.z"));
    }

    public static void setMinutes(Game game, int amount) {
        game.minutes = amount;
    }
    
    public static void removeAllPlayersFromPlayerIsInMainLobbyList(Game game) {
        for (Player player : game.playersList) {
            playersInMainLobbyList.remove(player);
        }
    }

    public static void sendAllPlayersHisLevel() {
        for (Player player : playersInMainLobbyList) {
            player.setLevel(GlavtoyGame.getDataConfig().getInt("users." + player.getName() + ".level"));
        }
    }

    public static void removePlayerFromPlayersInMainLobbyList(Player player) {
        playersInMainLobbyList.remove(player);
    }

    public static boolean playerIsInEndPlayers(Player player, Game game) {
         return game.endPlayers.contains(player);
    }

    public static Boolean playerIsInMainLobby(Player player) {
        return playersInMainLobbyList.contains(player);
    }

    public static void addPlayerToMainLobbyAmount() {
        playersInMainLobbyAmount++;
    }

    public static void addAllPlayersToMainLobbyAmount(Game game) {
        for (Player player : game.playersList) {
            playersInMainLobbyAmount++;
        }
    }

    public static void removePlayerFromMainLobbyAmount() {
        playersInMainLobbyAmount--;
    }

    public static void sendActionbarToAllPlayersInMainLobbyList() {
        for (Player player : playersInMainLobbyList) {
            player.sendActionBar(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("actionbar-stats-main-lobby")
                    .replace("{coins}", String.valueOf(Data.getBalance(player)))
                    .replace("{wins}", String.valueOf(Data.getWins(player)))
                    .replace("{kills}", String.valueOf(Data.getKills(player)))));
        }
    }

    public static List getPlayersFromMainLobbyList() {
        return playersInMainLobbyList;
    }

    public static int getPlayersFromMainLobbyAmount() {
        return playersInMainLobbyAmount;
    }

    public static void addPlayerToPlayersInMainLobbyList(Player player) {
        playersInMainLobbyList.add(player);
    }

    public static void removePlayerToPlayersInMainLobbyList(Player player) {
        playersInMainLobbyList.remove(player);
    }

    public static void setSeconds(Game game, int amount) {
        game.seconds = amount;
    }

    public static int getMinutes(Game game) {
        return game.minutes;
    }

    public static int getSeconds(Game game) {
        return game.seconds;
    }

    public static void clearBusySpawns(Game game) {
        game.busySpawns.clear();
    }

    public static int getDuration(Game game) {
        return game.duration;
    }

    public static void addKillToKills(Game game) {
        game.kills++;
    }

    public static LeaveItem getLeaveItem() {
        return leaveItem;
    }

    public static void hideCountdownBarForPlayer(Player player, Game game) {
        game.countdownBar.removePlayer(player);
    }

    public static void clearInventoryForAllPlayers(Game game) {
        for (Player player : game.playersList) {
            player.getInventory().clear();
        }
    }

    public static void setLevelCountdownForAllPlayers(Game game, int level) {
        for (Player player : game.playersList) {
            player.setLevel(level);
        }
    }

    public static Boolean playerIsInGame(Player player) {
        return playersInGameList.contains(player);
    }
    
    public static Boolean playerIsInWaitingLobby(Player player) {
        return playersInWaitingLobbyList.contains(player);
    }

    public static void addPlayerToWaitingLobbyList(Player player) {
        playersInWaitingLobbyList.add(player);
    }

    public static void removePlayerToWaitingLobbyList(Player player) {
        playersInWaitingLobbyList.remove(player);
    }

    public static Boolean playerIsInLobbys(Player player) {
        return (playersInWaitingLobbyList.contains(player) || playersInMainLobbyList.contains(player));
    }

    public static void addPlayerToPlayerInGameList(Player player) {
        playersInGameList.add(player);
    }

    public static void removePlayerFromPlayerInGameList(Player player) {
        playersInGameList.remove(player);
    }

    public static void addPlayerToPlayerInLobbysList(Player player) {
        //playersInLobbysList.add(player);
    }

    public static void removePlayerFromPlayerInLobbysList(Player player) {
        //playersInLobbysList.remove(player);
    }

    public static void getPlayersAmountFromWaitingLobby(Game game) {
        //todo
    }

    public static void addAllPlayersToPlayerInGameList(Game game) {
        for (Player player : game.playersList) {
            playersInGameList.add(player);
        }
    }

    public static void removeAllPlayersFromPlayerInGameList(Game game) {
        for (Player player : game.playersList) {
            playersInGameList.remove(player);
        }
    }

    public static void addAllPlayersToPlayerInLobbysList(Game game) {
        for (Player player : game.playersList) {
            //playersInLobbysList.add(player);
        }
    }

    public static void removeAllPlayersFromPlayerInLobbysList(Game game) {
        for (Player player : game.playersList) {
            //playersInLobbysList.remove(player);
        }
    }

    public static void teleportPlayerToWaitingLobby(Game game, Player player) {
        player.teleport(game.waitingLobby);
    }

    public static Player getWinner(Game game) {
        return game.winner;
    }

    public static void removeCountdownBarForPlayer(Player player, Game game) {
        game.countdownBar.removePlayer(player);
    }

    public static Game getGameAtPlayer(Player player) {
        return playerGameBase.get(player);
    }

    public static HashMap getPlayersGameBase(Player player) {
        return playerGameBase;
    }
    public static int getBalance(Game game, Player player) {
        return game.balanceBase.get(player);
    }

    public static void giveStartKitForPlayer(Player player) {
        ItemStack is = new ItemStack(Material.LEATHER_HELMET, 1);
        ItemMeta im = is.getItemMeta();
        im.setUnbreakable(true);
        is.setItemMeta(im);
        ItemStack is1 = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        ItemMeta im1 = is1.getItemMeta();
        im1.setUnbreakable(true);
        is1.setItemMeta(im1);
        ItemStack is2 = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        ItemMeta im2 = is2.getItemMeta();
        im2.setUnbreakable(true);
        is2.setItemMeta(im2);
        ItemStack is3 = new ItemStack(Material.LEATHER_BOOTS, 1);
        ItemMeta im3 = is3.getItemMeta();
        im3.setUnbreakable(true);
        is3.setItemMeta(im3);
        ItemStack is4 = new ItemStack(Material.WOODEN_SWORD, 1);
        ItemMeta im4 = is4.getItemMeta();
        im4.setUnbreakable(true);
        is4.setItemMeta(im4);
        player.getInventory().setHelmet(is);
        player.getInventory().setChestplate(is1);
        player.getInventory().setLeggings(is2);
        player.getInventory().setBoots(is3);
        player.getInventory().setItem(0, is4);
    }

    public static void giveMoney(Game game, Player player, int amount) {
        game.balanceBase.put(player, game.balanceBase.get(player) + amount);
    }

    public static void takeMoney(Game game, Player player, int amount) {
        game.balanceBase.put(player, game.balanceBase.get(player) - amount);
    }

    public static void removeAllPlayersFromWaitingLobbyList(Game game) {
        for (Player player : game.playersList) {
            removePlayerToWaitingLobbyList(player);
        }
    }

    public static void addPlayer(Game game) {
        game.players++;
    }

    public static String getKillsLeader(Game game) {
        int maxKills = 0;
        Player killsLeader = null;
        String name = "";
            for (Map.Entry<Player, Integer> key : game.killBase.entrySet()) {
                if (key.getValue() >= maxKills) {
                    maxKills = key.getValue();
                    if (game.leadersList.contains(Bukkit.getPlayer(key.getKey().getName()))) {
                        game.leadersList.remove(Bukkit.getPlayer(key.getKey().getName()));
                        game.leadersList.add(Bukkit.getPlayer(key.getKey().getName()));
                    } else {
                        game.leadersList.add(Bukkit.getPlayer(key.getKey().getName()));
                    }
                    String nickname = key.getKey().getName();
                    killsLeader = Bukkit.getPlayer(nickname);
                }
            name = killsLeader.getName();
        }
            GameScoreboard.addKillsLeaderToOldBase(game, game.leadersList.get(game.leadersList.size() - 2));
            System.out.println(GameScoreboard.getKillsLeaderFromOldBase(game));
        return name;
    }

    public static void setMaxHealthForAllPlayers(Game game) {
        for (Player player : game.playersList) {
            player.setHealth(20);
        }
    }

    public static HashMap getKillBase(Game game) {
        return game.killBase;
    }

    public static void removePlayerFromKillBase(Game game, Player player) {
        game.killBase.remove(player);
    }

    public static void addKillToPlayer(Game game, Player player) {
        game.killBase.put(player, game.killBase.get(player) + 1);
    }

    public static void sendAllPlayersHisStats(Game game) {
        for (Player player : game.playersList) {
            player.sendActionBar(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("actionbar-stats-game")
                    .replace("{kills}", String.valueOf(game.killBase.get(player)))
                    .replace("{balance}", String.valueOf(game.balanceBase.get(player)))));
        }
    }

    public static void sendScoreboardForAllPlayers(Scoreboard scoreboard, Game game) {
        for (Player player : game.playersList) {
            player.setScoreboard(scoreboard);
        }
    }

    public static int getPlayerKillsInGame(Game game, Player player) {
        return game.killBase.get(player);
    }

    public static void connectToGame(Game game, Player player) {
        if (!(game.playersList.contains(player)) && game.players < GlavtoyGame.getMapsConfig().getInt("maps." + game.gameHash.get(game) + ".max-players")) {
            WaitingLobbyScoreboard.putAmountOfPlayersToOldBase(game, Game.getPlayersAmountInGame(game));
            game.playersList.add(player);
            game.players += 1;
            game.balanceBase.put(player, 0);
            game.killBase.put(player, 0);
            player.getInventory().setItem(GlavtoyGame.getInstance().getConfig().getInt("leave-item.slot"), LeaveItem.getItemStackLeaveItem(leaveItem));
            addPlayerToWaitingLobbyList(player);
            playerGameBase.put(player, game);
            Collections.shuffle(game.spawns);
            player.teleport(game.waitingLobby);
            playersInMainLobbyList.remove(player);
            Game.removePlayerFromMainLobbyAmount();
            WaitingLobbyScoreboard.setScoreboardForAllPlayers(game);
            System.out.println("[" + game.gameHash.get(game) + "] -> " + player.getName() + " присоединился (" + game.players + "/" + GlavtoyGame.getMapsConfig().getInt("maps." + game.gameHash.get(game) + ".max-players") + ")");
            sendMessageForAllPlayers(game, ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("join-message").replace("{p}", String.valueOf(game.players)).replace("{max}", String.valueOf(game.maxPlayers)).replace("{player}", player.getName())));
            if (game.players < GlavtoyGame.getMapsConfig().getInt("maps." + game.gameHash.get(game) + ".min-players")) {
                WaitingRunnable.startWaiting(game);
            }
            if (Game.getPlayers(game) == GlavtoyGame.getMapsConfig().getInt("maps." + Game.getMapName(game) + ".max-players") && game.countdown > 15) {
                Game.setCountdown(game, 15);
            }
        }
    }

    public static void playCountdownSoundForAllPlayers(Game game) {
        for (Player player : game.playersList) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
        }
    }

    public static void teleportAllPlayersToMainLobby(Game game) {
        Location loc;
        for (Player player : game.playersList) {
            loc = new Location(Bukkit.getWorld((String) GlavtoyGame.getMapsConfig().get("main-lobby.world")),
                    GlavtoyGame.getMapsConfig().getDouble("main-lobby.x"),
                    GlavtoyGame.getMapsConfig().getDouble("main-lobby.y"),
                    GlavtoyGame.getMapsConfig().getDouble("main-lobby.z"),
                    GlavtoyGame.getMapsConfig().getInt("main-lobby.yaw"),
                    GlavtoyGame.getMapsConfig().getInt("main-lobby.pitch"));
            player.teleport(loc);
        }
    }

    public static void addAllPlayersToKillBase(Game game) {
        for (Player player : game.playersList) {
            game.killBase.put(player, 0);
        }
    }

    public static void addAllPlayersToOldWinsBase(Game game) {
        for (Player player : game.playersList) {
            MainLobbyScoreboard.addPlayerToWinsBaseOld(player, Data.getWins(player));
        }
    }

    public static void addAllPlayersToOldLevelBase(Game game) {
        for (Player player : game.playersList) {
            MainLobbyScoreboard.addPlayerToLevelBaseOld(player, Data.getLevel(player));
        }
    }

    public static void addAllPlayersToOldBalanceBase(Game game) {
        for (Player player : game.playersList) {
            MainLobbyScoreboard.addPlayerToBalanceBaseOld(player, Data.getBalance(player));
        }
    }

    public static void addAllPlayersToOldKillsBaseInGameScoreboard(Game game) {
        for (Player player : game.playersList) {
            GameScoreboard.addPlayerToOldKillsBase(player, Game.getPlayerKillsInGame(game, player));
        }
    }

    public static void addAllPlayersToOldBalanceBaseInGameScoreboard(Game game) {
        for (Player player : game.playersList) {
            GameScoreboard.addPlayerToOldBalanceBase(player, Game.getBalance(game, player));
        }
    }

    public static void addAllPlayersToOldKillsBase(Game game) {
        for (Player player : game.playersList) {
            MainLobbyScoreboard.addPlayerToKillsBaseOld(player, Data.getKills(player));
        }
    }

    public static void pullFireworkByWinner(Game game) {
        Player player = Bukkit.getPlayer(getKillsLeader(game));
        Location loc = player.getLocation();
        Firework fw = (Firework) (loc.getWorld().spawnEntity(loc, EntityType.FIREWORK));
    }

    public static void updateScoreboardInMainLobbyForAllPlayers() {
        for (Player player : playersInMainLobbyList) {
            MainLobbyScoreboard.updateScoreboards(
                    MainLobbyScoreboard.getScoreboardAtPlayer(player),
                    MainLobbyScoreboard.getObjectiveAtPlayer(player),
                    player
            );
        }
    }

    public static void sendAllPlayersMainLobbyScoreboard() {
        for (Player player : playersInMainLobbyList) {
            MainLobbyScoreboard.updateScoreboards(MainLobbyScoreboard.getScoreboardAtPlayer(player),
                    MainLobbyScoreboard.getObjectiveAtPlayer(player),
                    player);
        }
    }

    public static void addAllPlayersToMainLobbyPlayersList(Game game) {
        for (Player player : game.playersList) {
            playersInMainLobbyList.add(player);
        }
    }

    public static void playStartSoundForAllPlayers(Game game) {
        for (Player player : game.playersList) {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        }
    }

    public static WaitingRunnable getWaitingRunnable(Game game) {
        return game.waitingRunnable;
    }

    public static GameRunnable getGameRunnable(Game game) {
        return game.gameRunnable;
    }

    public static void recreateGame(Game game) {
        String mapName = game.gameHash.get(game);
        for (String map : GlavtoyGame.getMapsConfig().getConfigurationSection("maps").getKeys(false)) {
            if (map.equalsIgnoreCase(mapName)) {
                List<String> keys;
                keys = new ArrayList<String>();
                GameManager.deleteGameToGames(game);
                for (String spawn : GlavtoyGame.getMapsConfig().getConfigurationSection("maps." + map + ".spawns").getKeys(false)) {
                    keys.add(spawn);
                }
                if (keys.size() == GlavtoyGame.getMapsConfig().getInt("maps." + map + ".max-players")) {
                    World world = Bukkit.getWorld((String) GlavtoyGame.getMapsConfig().get("maps." + map + ".world"));
                    GameManager.addGameToGames(new Game(world,
                            GlavtoyGame.getMapsConfig().getInt("maps." + map + ".min-players"),
                            GlavtoyGame.getMapsConfig().getInt("maps." + map + ".max-players"),
                            keys, map));
                }
            }
        }
    }

    public static void resetGame(Game game) {
        String mapName = game.gameHash.get(game);
        for (String map : GlavtoyGame.getMapsConfig().getConfigurationSection("maps").getKeys(false)) {
            if (map.equalsIgnoreCase(mapName)) {
                List<String> keys;
                keys = new ArrayList<String>();
                GameManager.deleteGameToGames(game);
                for (String spawn : GlavtoyGame.getMapsConfig().getConfigurationSection("maps." + map + ".spawns").getKeys(false)) {
                    keys.add(spawn);
                }
                if (keys.size() == GlavtoyGame.getMapsConfig().getInt("maps." + map + ".max-players")) {
                    World world = Bukkit.getWorld((String) GlavtoyGame.getMapsConfig().get("maps." + map + ".world"));
                    GameManager.addGameToGames(new Game(world,
                            GlavtoyGame.getMapsConfig().getInt("maps." + map + ".min-players"),
                            GlavtoyGame.getMapsConfig().getInt("maps." + map + ".max-players"),
                            keys, map));
                }
            }
        }
    }

    public static void removePlayerFromGameBase(Player player) {
        playerGameBase.remove(player);
    }

    public static void removePlayerFromPlayersList(Game game, Player player) {
        game.playersList.remove(player);
    }

    public static void removePlayerFromBalanceBase(Game game, Player player) {
        game.balanceBase.remove(player);
    }

    public static void removePlayerInGameList(Player player) {
        playersInGameList.remove(player);
    }

    public static void setPlayersAmountInGame(Game game, int amount) {
        game.players = amount;
    }

    public static int getPlayersAmountInGame(Game game) {
        return game.players;
    }

    public static void giveStartKitForAllPlayers(Game game) {
        for (Player player : game.playersList) {
            player.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
            player.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
            player.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
            player.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
            player.getInventory().setItem(0, new ItemStack(Material.WOODEN_SWORD));
        }
    }

    public static World getWorld(Game game) {
        return game.world;
    }

    public static CountdownRunnable getCountdownRunnable(Game game) {
        return game.countdownRunnable;
    }

    public static int getMinPlayers(Game game) {
        return game.minPlayers;
    }
    
    public static Location getFreeSpawn(Game game) {
        Location freeSpawnLoc;
        freeSpawnLoc = new Location(Bukkit.getWorld("world"), 0, 0, 0);
        for (String spawn : game.spawns) {
            if (!(game.busySpawns.contains(spawn))) {
                World world = Bukkit.getWorld((String) GlavtoyGame.getMapsConfig().get("maps." + getMapName(game) + ".world"));
                freeSpawnLoc.setWorld(world);
                freeSpawnLoc.setX(GlavtoyGame.getMapsConfig().getInt("maps." + getMapName(game) + ".spawns." + spawn + ".x"));
                freeSpawnLoc.setY(GlavtoyGame.getMapsConfig().getInt("maps." + getMapName(game) + ".spawns." + spawn + ".y"));
                freeSpawnLoc.setZ(GlavtoyGame.getMapsConfig().getInt("maps." + getMapName(game) + ".spawns." + spawn + ".z"));
                game.busySpawns.add(spawn);
                break;
            }
        }
        return freeSpawnLoc;
    }

    public static void setGameScoreboardForAllPlayers(Game game) {
        for (Player player : game.playersList) {
            GameScoreboard.setScoreboardToPlayer(game, player);
        }
    }

    public static void updateGameScoreboardForAllPlayers(Game game) {
        for (Player player : game.playersList) {
            GameScoreboard.updateScoreboards(GameScoreboard.getScoreboardAtPlayer(player),
                    GameScoreboard.getObjectiveAtPlayer(player), game, player);
        }
    }

    public static void teleportPlayersAfterLobbyToSpawns(Game game) {
        for (Player player : game.playersList) {
            Location loc = getFreeSpawn(game);
            player.teleport(loc);
        }
    }

    public static void teleportPlayerToSpawns(Game game, Player player) {
            Location loc = getFreeSpawn(game);
            player.teleport(loc);
    }

    public static void sendTitleForAllPlayers(Game game, String title) {
        for (Player player : game.playersList) {
            player.sendTitle(title, null);
        }
    }

    public static void sendMessageForAllPlayers(Game game, String message) {
        for (Player player : game.playersList) {
            player.sendMessage(message);
        }
    }

    public static int getMaxPlayers(Game game) {
        return game.maxPlayers;
    }

    public static List getSpawns(Game game) {
        return game.spawns;
    }

    public static Boolean gameIsEnd(Game game) {
        return game.isEnd;
    }

    public static void toggleGameIsEnd(Game game, boolean bool) {
        game.isEnd = bool;
    }

    public static BossBar getCountdownBar(Game game) {
        return game.countdownBar;
    }

    public static void showCountdownBar(Game game, Player player) {
        game.countdownBar.addPlayer(player);
    }

    public static BossBar getGameBar(Game game) {
        return game.gameBar;
    }

    public static void showGameBar(Game game, Player player) {
        game.gameBar.addPlayer(player);
    }

    public static int getCountdown(Game game) {
        return game.countdown;
    }

    public static void setCountdown(Game game, int count) {
        game.countdown = count;
    }

    public static void setDuration(Game game, int count) {
        game.duration = count;
    }

    public static void setTitleCountdownBar(Game game, String title) {
        game.countdownBar.setTitle(title);
    }

    public static void setTitleGameBar(Game game, String title) {
        game.gameBar.setTitle(title);
    }

    public static void hideCountdownBar(Game game, Player player) {
        game.countdownBar.removePlayer(player);
    }

    public static void setTitleWaitingBar(Game game, String title) {
        game.waitingBar.setTitle(title);
    }

    public static void hideWaitingBar(Game game, Player player) {
        game.waitingBar.removePlayer(player);
    }

    public static void hideGameBar(Game game, Player player) {
        game.gameBar.removePlayer(player);
    }

    public static void hideWaitingBarForAllPlayers(Game game) {
        for (Player player : game.playersList) {
            game.waitingBar.removePlayer(player);
        }
    }

    public static void showWaitingBarForAllPlayers(Game game) {
        for (Player player : game.playersList) {
            game.waitingBar.addPlayer(player);
        }
    }

    public static void showCountdownBarForAllPlayers(Game game) {
        for (Player player : game.playersList) {
            game.countdownBar.addPlayer(player);
        }
    }

    public static String getMapName(Game game) {
        return game.gameHash.get(game);
    }

    public static void hideCountdownBarForAllPlayers(Game game) {
        for (Player player : game.playersList) {
            game.countdownBar.removePlayer(player);
        }
    }

    public static void showGameBarForAllPlayers(Game game) {
        for (Player player : game.playersList) {
            game.gameBar.addPlayer(player);
        }
    }

    public static void hideGameBarForAllPlayers(Game game) {
        for (Player player : game.playersList) {
            game.gameBar.removePlayer(player);
        }
    }

    public static int getPlayers(Game game) {
        return game.players;
    }

    public static List getPlayerList(Game game) {
        return game.playersList;
    }

    public static void giveOpenShopItemForAllPlayers(Game game, OpenShopItem openShopItem, int slot) {
        for (Player player : game.playersList) {
            player.getInventory().setItem(slot, OpenShopItem.getItemStackOpenShopItem(openShopItem));
        }
    }

    public static void giveOpenShopItemForPlayer(Player player, OpenShopItem openShopItem, int slot) {
            player.getInventory().setItem(slot, OpenShopItem.getItemStackOpenShopItem(openShopItem));
    }
}
