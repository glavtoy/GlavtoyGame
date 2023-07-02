package ru.glavtoy.glavtoygame.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.glavtoy.glavtoygame.GlavtoyGame;
import ru.glavtoy.glavtoygame.data.Data;
import ru.glavtoy.glavtoygame.runnable.MainLobbyRunnable;
import ru.glavtoy.glavtoygame.scoreboard.MainLobbyScoreboard;

public class DataListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (GlavtoyGame.getDataConfig().contains("users." + player.getName())) {
            Data.addPlayerToBalanceBase(player, GlavtoyGame.getDataConfig().getInt("users." + player.getName() + ".coins"));
            Data.addPlayerToWinsBase(player, GlavtoyGame.getDataConfig().getInt("users." + player.getName() + ".wins"));
            Data.addPlayerToKillsBase(player, GlavtoyGame.getDataConfig().getInt("users." + player.getName() + ".kills"));
            Data.addPlayerToLevelBase(player, GlavtoyGame.getDataConfig().getInt("users." + player.getName() + ".level"));
            Data.addPlayerToExpBase(player, GlavtoyGame.getDataConfig().getInt("users." + player.getName() + ".exp"));
            MainLobbyScoreboard.addPlayerToKillsBaseOld(player, Data.getKills(player));
            MainLobbyScoreboard.addPlayerToWinsBaseOld(player, Data.getWins(player));
            MainLobbyScoreboard.addPlayerToBalanceBaseOld(player, Data.getBalance(player));
            MainLobbyScoreboard.addPlayerToLevelBaseOld(player, Data.getLevel(player));
        } else {
            Data.writeBalanceToPlayerInDataConfig(player, 0);
            GlavtoyGame.saveDataConfig();
            Data.addPlayerToBalanceBase(player, Data.getBalanceFromPlayerInDataConfig(player));
            Data.writeWinsToPlayerInDataConfig(player, 0);
            GlavtoyGame.saveDataConfig();
            Data.addPlayerToWinsBase(player, Data.getWinsFromPlayerInDataConfig(player));
            Data.writeKillsToPlayerInDataConfig(player, 0);
            GlavtoyGame.saveDataConfig();
            Data.addPlayerToKillsBase(player, Data.getKillsFromPlayerInDataConfig(player));
            Data.writeLevelToPlayerInDataConfig(player, 1);
            GlavtoyGame.saveDataConfig();
            Data.addPlayerToLevelBase(player, Data.getLevelFromPlayerInDataConfig(player));
            Data.writeExpToPlayerInDataConfig(player, 0);
            GlavtoyGame.saveDataConfig();
            Data.addPlayerToExpBase(player, Data.getExpFromPlayerInDataConfig(player));
            MainLobbyScoreboard.addPlayerToKillsBaseOld(player, Data.getKills(player));
            MainLobbyScoreboard.addPlayerToWinsBaseOld(player, Data.getWins(player));
            MainLobbyScoreboard.addPlayerToBalanceBaseOld(player, Data.getBalance(player));
            MainLobbyScoreboard.addPlayerToLevelBaseOld(player, Data.getLevel(player));
        }
        MainLobbyRunnable.startRunnable();
        MainLobbyScoreboard.setScoreboardPlayer(player);
    }
}
