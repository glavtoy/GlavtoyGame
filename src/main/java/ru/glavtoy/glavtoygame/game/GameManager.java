package ru.glavtoy.glavtoygame.game;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private static List<Game> busyGames = new ArrayList<Game>();
    private static List<Game> games = new ArrayList<Game>();

    public static void addGameToBusy(Game game) {
        busyGames.add(game);
    }

    public static void deleteGameFromBusy(Game game) {
        busyGames.remove(game);
    }

    public static void clearBusyGamesList() {
        busyGames.clear();
    }

    public static void addGameToGames(Game game) {
        games.add(game);
    }

    public static void deleteGameToGames(Game game) {
        games.remove(game);
    }

    public static Game getFreeGame() {
        Game game = null;
        for (Game game1 : games) {
            if (!(busyGames.contains(game1))) {
                game = game1;
            }
        }
        return game;
    }

    public static void clearGames() {
        games.clear();
    }

    public static List getBusyGames() {
        return busyGames;
    }

    public static List getGames() {
        return games;
    }
}
