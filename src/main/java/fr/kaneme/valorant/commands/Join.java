package fr.kaneme.valorant.commands;

import fr.kaneme.valorant.Main;
import fr.kaneme.valorant.managers.GameState;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Join implements CommandExecutor {
    private Main main;
    public Join(Main plugin) {
        this.main = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        FileConfiguration config = this.main.getConfig();
        if (Main.getInstance().isState(GameState.GAME)) return false;
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args[0].equalsIgnoreCase("red") && this.main.getRed() != null) {
                if (!this.main.getRed().getPlayers().contains(player)) {
                    this.main.getBlue().getPlayers().remove(player);
                    this.main.getRed().addPlayer(player);
                    Location spawnRed = new Location(player.getWorld(), config.getDouble("Join.red-spawn.x"), config.getDouble("Join.red-spawn.y"), config.getDouble("Join.red-spawn.z"), config.getInt("Join.red-spawn.yaw"), config.getInt("Join.red-spawn.pitch"));
                    player.teleport(spawnRed);
                    player.sendMessage(this.main.getConfig().getString("Team.join-message-red"));
                }
            } else if (args[0].equalsIgnoreCase("blue") && this.main.getBlue() != null) {
                if (!this.main.getBlue().getPlayers().contains(player)) {
                    this.main.getRed().getPlayers().remove(player);
                    this.main.getBlue().addPlayer(player);
                    Location spawnBlue = new Location(player.getWorld(), config.getDouble("Join.blue-spawn.x"), config.getDouble("Join.blue-spawn.y"), config.getDouble("Join.blue-spawn.z"), config.getInt("Join.blue-spawn.yaw"), config.getInt("Join.blue-spawn.pitch"));
                    player.teleport(spawnBlue);
                    player.sendMessage(this.main.getConfig().getString("Team.join-message-blue"));
                }
            }
        }
        return false;
    }
}
