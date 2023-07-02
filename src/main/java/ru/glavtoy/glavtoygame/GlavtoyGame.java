package ru.glavtoy.glavtoygame;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.glavtoy.glavtoygame.command.CommandAdmin;
import ru.glavtoy.glavtoygame.command.CommandPlay;
import ru.glavtoy.glavtoygame.data.Data;
import ru.glavtoy.glavtoygame.game.Game;
import ru.glavtoy.glavtoygame.game.GameManager;
import ru.glavtoy.glavtoygame.listener.*;
import ru.glavtoy.glavtoygame.menu.ShopMenu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class GlavtoyGame extends JavaPlugin {

    private static File mapsFile;
    private static FileConfiguration mapsConfig;

    private static GlavtoyGame instance;
    private static File dataFile;
    private static FileConfiguration dataConfig;
    private static File levelFile;
    private static FileConfiguration levelConfig;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        mapsFile = new File(getDataFolder(), "maps.yml");
        dataFile = new File(getDataFolder(), "data.yml");
        levelFile = new File(getDataFolder(), "level.yml");
        mapsConfig = YamlConfiguration.loadConfiguration(mapsFile);
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        levelConfig = YamlConfiguration.loadConfiguration(levelFile);
        if (!mapsFile.exists()) {
            try {
                mapsFile.createNewFile();
                mapsConfig.createSection("main-lobby");
                mapsConfig.createSection("main-lobby.x");
                mapsConfig.createSection("main-lobby.y");
                mapsConfig.createSection("main-lobby.z");
                mapsConfig.createSection("main-lobby.yaw");
                mapsConfig.createSection("main-lobby.pitch");
                mapsConfig.createSection("main-lobby.world");
                mapsConfig.createSection("maps");
                saveMapsConfig();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
                dataConfig.createSection("users");
                saveDataConfig();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!levelFile.exists()) {
            try {
                levelFile.createNewFile();
                levelConfig.createSection("levels");
                levelConfig.createSection("levels.1");
                levelConfig.createSection("levels.1.exp");
                levelConfig.set("levels.1.exp", 0);
                saveLevelConfig();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        getCommand("glavtoygame").setExecutor(new CommandAdmin());
        getCommand("glavtoygame").setTabCompleter(new CommandAdmin());
        getCommand("play").setExecutor(new CommandPlay());
        getServer().getPluginManager().registerEvents(new OpenItemListener(), this);
        getServer().getPluginManager().registerEvents(new GameListener(), this);
        getServer().getPluginManager().registerEvents(new ShopMenuListener(), this);
        getServer().getPluginManager().registerEvents(new LobbysListener(), this);
        getServer().getPluginManager().registerEvents(new LeaveItemListener(), this);
        getServer().getPluginManager().registerEvents(new DataListener(), this);
        getServer().getPluginManager().registerEvents(new HealthBallListener(), this);

        for (String map : mapsConfig.getConfigurationSection("maps").getKeys(false)) {
            List<String> keys;
            if (mapsConfig.getString("maps." + map + ".world") != null && mapsConfig.getInt("maps." + map + ".min-players") != 0 &&
                    mapsConfig.getInt("maps." + map + ".max-players") != 0) {
                keys = new ArrayList<String>();
                for (String spawn : mapsConfig.getConfigurationSection("maps." + map + ".spawns").getKeys(false)) {
                    keys.add(spawn);
                }
                if (keys.size() == mapsConfig.getInt("maps." + map + ".max-players")) {
                    World world = Bukkit.getWorld((String) mapsConfig.get("maps." + map + ".world"));
                    GameManager.addGameToGames(new Game(world,
                            mapsConfig.getInt("maps." + map + ".min-players"),
                            mapsConfig.getInt("maps." + map + ".max-players"),
                            keys, map));
                }
            }
        }
        ShopMenu.init();
    }

    @Override
    public void onDisable() {
        saveMapsConfig();
    }

    public static FileConfiguration getMapsConfig() {
        return mapsConfig;
    }

    public static FileConfiguration getDataConfig() {
        return dataConfig;
    }

    public static FileConfiguration getLevelConfig() {
        return levelConfig;
    }

    public static GlavtoyGame getInstance() {
        return instance;
    }

    public static void saveMapsConfig() {
        try {
            mapsConfig.save(mapsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveDataConfig() {
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveLevelConfig() {
        try {
            levelConfig.save(levelFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
