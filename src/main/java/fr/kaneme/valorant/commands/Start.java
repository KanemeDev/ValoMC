package fr.kaneme.valorant.commands;

import fr.kaneme.valorant.Main;
import fr.kaneme.valorant.managers.GameState;
import fr.kaneme.valorant.utils.Timer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Start implements CommandExecutor {
    private Main main;
    public Start(Main plugin) {
        this.main = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        Player player = (Player) sender;
        Location doorBlue1 = new Location(player.getWorld(), this.main.getConfig().getDouble("Blue.door.1.x"), this.main.getConfig().getDouble("Blue.door.1.y"), this.main.getConfig().getDouble("Blue.door.1.z"));
        Location doorBlue2 = new Location(player.getWorld(), this.main.getConfig().getDouble("Blue.door.2.x"), this.main.getConfig().getDouble("Blue.door.2.y"), this.main.getConfig().getDouble("Blue.door.2.z"));
        Location doorRed1 = new Location(player.getWorld(), this.main.getConfig().getDouble("Red.door.1.x"), this.main.getConfig().getDouble("Red.door.1.y"), this.main.getConfig().getDouble("Red.door.1.z"));
        Location doorRed2 = new Location(player.getWorld(), this.main.getConfig().getDouble("Red.door.2.x"), this.main.getConfig().getDouble("Red.door.2.y"), this.main.getConfig().getDouble("Red.door.2.z"));
        if (Main.getInstance().isState(GameState.GAME)) return false;
        if (sender instanceof Player) {
            if (main.getRed().getPlayers().size() >= 1 && main.getBlue().getPlayers().size() >= 1) {
                Timer timer = new Timer(main);
                timer.runTaskTimer(main, 0, 20);
                Main.getInstance().getBlockBuilder().blockBuild(doorBlue1, doorBlue2, Material.GLASS);
                Main.getInstance().getBlockBuilder().blockBuild(doorRed1, doorRed2, Material.GLASS);
            } else {
                player.sendMessage(this.main.getConfig().getString("Start.need-players"));
            }
        }
        return false;
    }
}
