package ru.glavtoy.glavtoygame.level;

import java.util.HashMap;

public class Level {

    private static HashMap<Integer, Integer> levelSetsBase = new HashMap<Integer, Integer>();

    public static void addLevelSetToBase(int level, int exp) {
        levelSetsBase.put(level, exp);
    }

    public static HashMap getLevelSetsBase() {
        return levelSetsBase;
    }

    public static int getNeedExpForLevel(int level) {
        return levelSetsBase.get(level);
    }

    public static boolean levelSetsBaseContainsLevel(int level) {
        return levelSetsBase.containsKey(level);
    }
}
