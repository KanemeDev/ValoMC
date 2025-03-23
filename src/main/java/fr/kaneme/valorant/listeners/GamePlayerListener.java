package fr.kaneme.valorant.listeners;

import fr.kaneme.valorant.Main;
import fr.kaneme.valorant.managers.GameState;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class GamePlayerListener implements Listener {
    Location doorBlue1 = new Location(Bukkit.getWorld("world"), Main.getInstance().getConfig().getDouble("Blue.door.1.x"), Main.getInstance().getConfig().getDouble("Blue.door.1.y"), Main.getInstance().getConfig().getDouble("Blue.door.1.z"));
    Location doorBlue2 = new Location(Bukkit.getWorld("world"), Main.getInstance().getConfig().getDouble("Blue.door.2.x"), Main.getInstance().getConfig().getDouble("Blue.door.2.y"), Main.getInstance().getConfig().getDouble("Blue.door.2.z"));
    Location doorRed1 = new Location(Bukkit.getWorld("world"), Main.getInstance().getConfig().getDouble("Red.door.1.x"), Main.getInstance().getConfig().getDouble("Red.door.1.y"), Main.getInstance().getConfig().getDouble("Red.door.1.z"));
    Location doorRed2 = new Location(Bukkit.getWorld("world"), Main.getInstance().getConfig().getDouble("Red.door.2.x"), Main.getInstance().getConfig().getDouble("Red.door.2.y"), Main.getInstance().getConfig().getDouble("Red.door.2.z"));

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(Main.getInstance().getConfig().getString("Join.join-message"));
    }

    @EventHandler
    public void onPlaceItem(BlockPlaceEvent event) {
        event.setCancelled(true);
    }
    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDestroyBlock(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (Main.getInstance().getRed().getPlayers().contains(player)) {
            Main.getInstance().getRed().getPlayers().remove(player);
            Main.getInstance().getRed().getPlayers().size();
            player.setGameMode(GameMode.SPECTATOR);
            String deathMessage = Main.getInstance().getConfig().getString("Team.death-message-red");
            deathMessage = deathMessage.replace("{player}", player.getName());
            Bukkit.broadcastMessage(deathMessage);
            if (Main.getInstance().getRed().getPlayers().size() == 0) {
                Main.getInstance().getRed().getPlayers().clear();
                Main.getInstance().getBlue().getPlayers().clear();
                Main.getInstance().getBlockBuilder().blockBuild(doorBlue1, doorBlue2, Material.GLASS);
                Main.getInstance().getBlockBuilder().blockBuild(doorRed1, doorRed2, Material.GLASS);
                Location spawn = new Location(Bukkit.getWorld("world"), 798, 195, 1390, 135, -3);
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.getInventory().clear();
                    players.setFoodLevel(2000);
                    players.setHealth(20);
                    players.setGameMode(GameMode.ADVENTURE);
                    players.teleport(spawn);
                    Main.getInstance().setState(GameState.LOBBY);
                }
                Bukkit.broadcastMessage(Main.getInstance().getConfig().getString("Team.blue-win"));
            }
        } else if (Main.getInstance().getBlue().getPlayers().contains(player)) {
            Main.getInstance().getBlue().getPlayers().remove(player);
            Main.getInstance().getBlue().getPlayers().size();
            player.setGameMode(GameMode.SPECTATOR);
            String deathMessage = Main.getInstance().getConfig().getString("Team.death-message-blue");
            deathMessage = deathMessage.replace("{player}", player.getName());
            Bukkit.broadcastMessage(deathMessage);
            if (Main.getInstance().getBlue().getPlayers().size() == 0) {
                Main.getInstance().getBlue().getPlayers().clear();
                Main.getInstance().getRed().getPlayers().clear();
                Main.getInstance().getBlockBuilder().blockBuild(doorBlue1, doorBlue2, Material.GLASS);
                Main.getInstance().getBlockBuilder().blockBuild(doorRed1, doorRed2, Material.GLASS);
                Location spawn = new Location(Bukkit.getWorld("world"), 798, 195, 1390, 135, -3);
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.getInventory().clear();
                    players.setFoodLevel(2000);
                    players.setHealth(20);
                    players.setGameMode(GameMode.ADVENTURE);
                    players.teleport(spawn);
                    Main.getInstance().setState(GameState.LOBBY);
                }
                Bukkit.broadcastMessage(Main.getInstance().getConfig().getString("Team.red-win"));
            }
        }
    }
    @EventHandler
    public void onGameState(EntityDamageByEntityEvent event) {
        if (Main.getInstance().isState(GameState.LOBBY)) {
            event.setCancelled(true);
        }

        Player player = null;
        Player damager = null;

        if (event.getEntity() instanceof Player) {
            player = (Player) event.getEntity();
        }

        if (event.getDamager() instanceof Player) {
            damager = (Player) event.getDamager();
        } else if (event.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile) event.getDamager();
            if (projectile.getShooter() instanceof Player) {
                damager = (Player) projectile.getShooter();
            }
        }

        if (player != null && damager != null) {
            if (Main.getInstance().getBlue().getPlayers().contains(player) && Main.getInstance().getBlue().getPlayers().contains(damager)) {
                event.setCancelled(true);
            } else if (Main.getInstance().getRed().getPlayers().contains(player) && Main.getInstance().getRed().getPlayers().contains(damager)) {
                event.setCancelled(true);
            }
        }
    }
}
