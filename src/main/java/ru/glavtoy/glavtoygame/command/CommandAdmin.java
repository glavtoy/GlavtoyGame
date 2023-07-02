package ru.glavtoy.glavtoygame.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.glavtoy.glavtoygame.GlavtoyGame;
import ru.glavtoy.glavtoygame.game.Game;
import ru.glavtoy.glavtoygame.util.ColorUtil;

import java.util.ArrayList;
import java.util.List;

public class CommandAdmin implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("glavtoygame.admin") || player.isOp()) {
                if (args.length < 2 && !(args[0].equalsIgnoreCase("setmainlobby")) && !(args[0].equalsIgnoreCase("buildingmodeyes")) && !(args[0].equalsIgnoreCase("buildingmodeno"))) {
                    player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("no-args")));
                } else if (args.length == 2 && (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("delete"))) {
                    if (args[0].equalsIgnoreCase("create")) {
                        String map = args[1];
                        List<String> maps = new ArrayList<String>();
                        for (String key : GlavtoyGame.getMapsConfig().getConfigurationSection("maps").getKeys(false)) {
                            maps.add(key);
                        }
                        if (maps.contains(map)) {
                            player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("already-map").replace("{map}", map)));
                        } else {
                            GlavtoyGame.getMapsConfig().createSection("maps." + args[1]);
                            GlavtoyGame.getMapsConfig().createSection("maps." + args[1] + ".world");
                            GlavtoyGame.getMapsConfig().createSection("maps." + args[1] + ".min-players");
                            GlavtoyGame.getMapsConfig().createSection("maps." + args[1] + ".max-players");
                            GlavtoyGame.getMapsConfig().createSection("maps." + args[1] + ".spawns");
                            GlavtoyGame.getMapsConfig().createSection("maps." + args[1] + ".lobby");
                            GlavtoyGame.getMapsConfig().set("maps." + args[1] + ".world", player.getWorld().getName());
                            GlavtoyGame.saveMapsConfig();
                            player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("create-success").replace("{map}", map)));
                        }
                    } else if (args[0].equalsIgnoreCase("delete")) {
                        String map = args[1];
                        List<String> maps = new ArrayList<String>();
                        for (String key : GlavtoyGame.getMapsConfig().getConfigurationSection("maps").getKeys(false)) {
                            maps.add(key);
                        }
                        if (maps.contains(map)) {
                            GlavtoyGame.getMapsConfig().set("maps." + args[1], null);
                            GlavtoyGame.saveMapsConfig();
                            player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("delete-success").replace("{map}", map)));
                        } else {
                            player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("no-map").replace("{map}", map)));
                        }
                    } else {
                        player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("no-args")));
                    }
                } else if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("setminplayers")) {
                        try {
                        String map = args[1];
                        List<String> maps = new ArrayList<String>();
                        for (String key : GlavtoyGame.getMapsConfig().getConfigurationSection("maps").getKeys(false)) {
                            maps.add(key);
                        }
                        if (maps.contains(map)) {
                            if (Integer.parseInt(args[2]) > 0) {
                                GlavtoyGame.getMapsConfig().set("maps." + args[1] + ".min-players", Integer.parseInt(args[2]));
                                GlavtoyGame.saveMapsConfig();
                                player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("set-min-players-success").replace("{map}", map).replace("{min}", args[2])));
                            } else {
                                player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("negative-number-error")));
                        }
                            } else {
                                player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("no-map").replace("{map}", map)));
                            }
                        } catch (NumberFormatException e) {
                            player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("number-format-exception-error")));
                        }

                    } else if (args[0].equalsIgnoreCase("setmaxplayers")) {
                        try {
                        String map = args[1];
                        List<String> maps = new ArrayList<String>();
                        for (String key : GlavtoyGame.getMapsConfig().getConfigurationSection("maps").getKeys(false)) {
                            maps.add(key);
                        }
                        if (maps.contains(map)) {
                            if (Integer.parseInt(args[2]) > 0) {
                                GlavtoyGame.getMapsConfig().set("maps." + args[1] + ".max-players", Integer.parseInt(args[2]));
                                GlavtoyGame.saveMapsConfig();
                                player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("set-max-players-success").replace("{map}", map).replace("{max}", args[2])));
                            } else {
                                player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("negative-number-error")));
                        }
                            } else {
                            player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("no-map").replace("{map}", map)));
                        }
                        } catch (NumberFormatException e) {
                            player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("number-format-exception-error")));
                        }
                    } else if (args[0].equalsIgnoreCase("setspawn")) {
                        String map = args[1];
                        List<String> maps = new ArrayList<String>();
                        for (String key : GlavtoyGame.getMapsConfig().getConfigurationSection("maps").getKeys(false)) {
                            maps.add(key);
                        }
                        if (maps.contains(map)) {
                            List<String> spawns = new ArrayList<String>();
                            for (String spawn : GlavtoyGame.getMapsConfig().getConfigurationSection("maps." + args[1] + ".spawns").getKeys(false)) {
                                spawns.add(spawn);
                            }
                            if (spawns.size() < GlavtoyGame.getMapsConfig().getInt("maps." + args[1] + ".max-players")) {
                                GlavtoyGame.getMapsConfig().createSection("maps." + args[1] + ".spawns." + args[2]);
                                GlavtoyGame.getMapsConfig().createSection("maps." + args[1] + ".spawns." + args[2] + ".x");
                                GlavtoyGame.getMapsConfig().createSection("maps." + args[1] + ".spawns." + args[2] + ".y");
                                GlavtoyGame.getMapsConfig().createSection("maps." + args[1] + ".spawns." + args[2] + ".z");
                                GlavtoyGame.getMapsConfig().set("maps." + args[1] + ".spawns." + args[2] + ".x", player.getLocation().getX());
                                GlavtoyGame.getMapsConfig().set("maps." + args[1] + ".spawns." + args[2] + ".y", player.getLocation().getY());
                                GlavtoyGame.getMapsConfig().set("maps." + args[1] + ".spawns." + args[2] + ".z", player.getLocation().getZ());
                                GlavtoyGame.saveMapsConfig();
                                player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("set-spawn-success").replace("{map}", map).replace("{spawn}", args[2])));
                            } else {
                                player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("spawns-limit").replace("{map}", map)));
                            }
                        } else {
                            player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("no-map").replace("{map}", map)));
                        }
                    }
                } else if (args.length == 2) {
                     if (args[0].equalsIgnoreCase("setwaitinglobby")) {
                        String map = args[1];
                        List<String> maps = new ArrayList<String>();
                        for (String key : GlavtoyGame.getMapsConfig().getConfigurationSection("maps").getKeys(false)) {
                            maps.add(key);
                        }
                        if (maps.contains(map)) {
                            GlavtoyGame.getMapsConfig().createSection("maps." + map + ".lobby.x");
                            GlavtoyGame.getMapsConfig().createSection("maps." + map + ".lobby.y");
                            GlavtoyGame.getMapsConfig().createSection("maps." + map + ".lobby.z");
                            GlavtoyGame.getMapsConfig().set("maps." + map + ".lobby.x", player.getLocation().getX());
                            GlavtoyGame.getMapsConfig().set("maps." + map + ".lobby.y", player.getLocation().getY());
                            GlavtoyGame.getMapsConfig().set("maps." + map + ".lobby.z", player.getLocation().getZ());
                            GlavtoyGame.saveMapsConfig();
                            player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("set-waitinglobby-success").replace("{map}", map)));
                        } else {
                            player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("no-map").replace("{map}", map)));
                        }
                    } else {
                        player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("no-args")));
                    }
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("setmainlobby")) {
                        GlavtoyGame.getMapsConfig().set("main-lobby.world", player.getWorld().getName());
                        GlavtoyGame.getMapsConfig().set("main-lobby.x", player.getLocation().getX());
                        GlavtoyGame.getMapsConfig().set("main-lobby.y", player.getLocation().getY());
                        GlavtoyGame.getMapsConfig().set("main-lobby.z", player.getLocation().getZ());
                        GlavtoyGame.getMapsConfig().set("main-lobby.yaw", player.getLocation().getYaw());
                        GlavtoyGame.getMapsConfig().set("main-lobby.pitch", player.getLocation().getPitch());
                        GlavtoyGame.saveMapsConfig();
                        player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("set-mainlobby-success")));
                    }
                    else if (args[0].equalsIgnoreCase("buildingmodeyes")) {
                        Game.removePlayerToPlayersInMainLobbyList(player);
                    }
                    else if (args[0].equalsIgnoreCase("buildingmodeno")) {
                        Game.addPlayerToPlayersInMainLobbyList(player);
                    }
                    else {
                        player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("no-args")));
                    }
                } else {
                    player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("no-args")));
                }
            } else {
                player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("no-perms")));
            }
        } else {
            sender.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("only-players")));
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = null;
        Player player = (Player) sender;
        if (player.isOp() || player.hasPermission("glavtoygame.admin")) {
            if (args.length == 1) {
                list = new ArrayList<String>();
                list.add("create");
                list.add("delete");
                list.add("setspawn");
                list.add("setminplayers");
                list.add("setmaxplayers");
                list.add("setmaxplayers");
                list.add("setwaitinglobby");
                list.add("setmainlobby");
                list.add("buildingmodeyes");
                list.add("buildingmodeno");
            }
            else if (args.length == 2 && !(args[0].equalsIgnoreCase("create")) && !(args[0].equalsIgnoreCase("setmainlobby"))) {
                list = new ArrayList<String>();
                for (String key : GlavtoyGame.getMapsConfig().getConfigurationSection("maps").getKeys(false)) {
                    list.add(key);
                }
            }
        }
        return list;
    }
}
