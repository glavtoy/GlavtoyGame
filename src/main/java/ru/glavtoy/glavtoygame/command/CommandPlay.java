package ru.glavtoy.glavtoygame.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.glavtoy.glavtoygame.GlavtoyGame;
import ru.glavtoy.glavtoygame.game.Game;
import ru.glavtoy.glavtoygame.game.GameManager;
import ru.glavtoy.glavtoygame.util.ColorUtil;

public class CommandPlay implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
                if (!(Game.playerIsInGame(player))) {
                    if (GameManager.getFreeGame() != null) {
                        Game.connectToGame(GameManager.getFreeGame(), player);
                    } else {
                        player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("no-free-map")));
                    }
                } else {
                    player.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("already-connected")));
                }
        } else {
            sender.sendMessage(ColorUtil.msg(GlavtoyGame.getInstance().getConfig().getString("only-players")));
        }
        return true;
    }
}
