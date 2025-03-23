package fr.kaneme.valorant.utils;

import fr.kaneme.valorant.Main;
import fr.kaneme.valorant.managers.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable {
    private int timer = 30;
    private Main main;

    public Timer(Main plugin) {
        this.main = plugin;
    }

    @Override
    public void run() {
        Location doorBlue1 = new Location(Bukkit.getWorld("world"), this.main.getConfig().getDouble("Blue.door.1.x"), this.main.getConfig().getDouble("Blue.door.1.y"), this.main.getConfig().getDouble("Blue.door.1.z"));
        Location doorBlue2 = new Location(Bukkit.getWorld("world"), this.main.getConfig().getDouble("Blue.door.2.x"), this.main.getConfig().getDouble("Blue.door.2.y"), this.main.getConfig().getDouble("Blue.door.2.z"));
        Location doorRed1 = new Location(Bukkit.getWorld("world"), this.main.getConfig().getDouble("Red.door.1.x"), this.main.getConfig().getDouble("Red.door.1.y"), this.main.getConfig().getDouble("Red.door.1.z"));
        Location doorRed2 = new Location(Bukkit.getWorld("world"), this.main.getConfig().getDouble("Red.door.2.x"), this.main.getConfig().getDouble("Red.door.2.y"), this.main.getConfig().getDouble("Red.door.2.z"));
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.setLevel(timer);
        }

        if (timer == 30) {
            Bukkit.broadcastMessage(this.main.getConfig().getString("Timer.message-30s"));
        }
        if (timer == 15) {
            Bukkit.broadcastMessage(this.main.getConfig().getString("Timer.message-15s"));
        }
        if (timer == 5) {
            Bukkit.broadcastMessage(this.main.getConfig().getString("Timer.message-5s"));
        }
        if (timer == 0) {
            Bukkit.broadcastMessage(this.main.getConfig().getString("Timer.message-start"));
            Main.getInstance().getBlockBuilder().blockBuild(doorBlue1, doorBlue2, Material.AIR);
            Main.getInstance().getBlockBuilder().blockBuild(doorRed1, doorRed2, Material.AIR);
            main.setState(GameState.GAME);
            cancel();
        }

        timer --;
    }
}
