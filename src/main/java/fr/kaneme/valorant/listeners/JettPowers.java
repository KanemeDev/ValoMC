package fr.kaneme.valorant.listeners;

import fr.kaneme.valorant.Main;
import fr.kaneme.valorant.managers.GameState;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class JettPowers implements Listener {
    private Main main;
    public JettPowers(Main plugin) {
        this.main = plugin;
    }

    @EventHandler
    public void onRightClickReset(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (this.main.isState(GameState.LOBBY)) return;
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (item != null && item.getType() == Material.matchMaterial(this.main.getConfig().getString("Agents.Jett.smoke.material")) && item.getItemMeta() != null && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase(this.main.getConfig().getString("Agents.Jett.smoke.name"))) {
                if (!item.getItemMeta().getLore().contains("§b>> §fUtilisations §b" + main.useJettSmoke + "§f/§b2")) {
                    main.useJettSmoke = 2;
                }
            }
        }
        if (this.main.isState(GameState.LOBBY)) return;
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (item != null && item.getType() == Material.matchMaterial(this.main.getConfig().getString("Agents.Jett.updraft.material")) && item.getItemMeta() != null && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase(this.main.getConfig().getString("Agents.Jett.updraft.name"))) {
                if (!item.getItemMeta().getLore().contains("§b>> §fUtilisations §b" + main.useJettUpdraft + "§f/§b1")) {
                    main.useJettUpdraft = 1;
                }
            }
        }
        if (this.main.isState(GameState.LOBBY)) return;
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (item != null && item.getType() == Material.matchMaterial(this.main.getConfig().getString("Agents.Jett.dash.material")) && item.getItemMeta() != null && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase(this.main.getConfig().getString("Agents.Jett.dash.name"))) {
                if (!item.getItemMeta().getLore().contains("§b>> §fUtilisations §b" + main.useJettDash + "§f/§b1")) {
                    main.useJettDash = 1;
                }
            }
        }
    }

    private HashMap<Player, Long> cooldownsSmoke = new HashMap<>();
    @EventHandler
    public void onSmoke(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (this.main.isState(GameState.LOBBY)) return;
        if (main.useJettSmoke != 0) {
            if (this.cooldownsSmoke.containsKey(event.getPlayer())) {
                long timeLeftSmoke = (cooldownsSmoke.get(event.getPlayer()) - System.currentTimeMillis()) / 1000;
                if (timeLeftSmoke > 0 && player.getItemInHand().getType() == Material.matchMaterial(this.main.getConfig().getString("Agents.Jett.smoke.material"))) {
                    //player.sendMessage("Attends un peu pour réutiliser ta Smoke !");
                    return;
                } else {
                    this.cooldownsSmoke.remove(event.getPlayer());
                }
            }
            if (item == null) return;
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase(this.main.getConfig().getString("Agents.Jett.smoke.name")) && item.getType().equals(Material.matchMaterial(this.main.getConfig().getString("Agents.Jett.smoke.material")))) {
                    Egg egg = player.launchProjectile(Egg.class);
                    egg.setVelocity(player.getLocation().getDirection().multiply(2));
                    this.cooldownsSmoke.put(event.getPlayer(), System.currentTimeMillis() + (5000L));
                    if (main.useJettSmoke > 0) {
                        main.useJettSmoke -= 1;
                    }

                    ItemMeta meta = item.getItemMeta();
                    List<String> lore = new ArrayList<>();
                    lore.add("§b>> §fJett lance un projectile qui crée un nuage de fumée");
                    lore.add("§b>> §fUtilisations §b" + main.useJettSmoke + "§f/§b2");
                    meta.setLore(lore);
                    item.setItemMeta(meta);

                    player.getInventory().setItemInHand(item);
                }
            }
        }
    }
    private HashMap<Player, Long> cooldownsUpdraft = new HashMap<>();
    @EventHandler
    public void onUpdraft(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (this.main.isState(GameState.LOBBY)) return;
        if (main.useJettUpdraft != 0) {
            if (this.cooldownsUpdraft.containsKey(event.getPlayer())) {
                long timeLeftUpdraft = (cooldownsUpdraft.get(event.getPlayer()) - System.currentTimeMillis()) / 1000;
                if (timeLeftUpdraft > 0) {
                    //player.sendMessage("Attends un peu pour réutiliser ton Updraft !");
                    return;
                } else {
                    this.cooldownsUpdraft.remove(event.getPlayer());
                }
            }
            if (item == null) return;
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase(this.main.getConfig().getString("Agents.Jett.updraft.name")) && item.getType().equals(Material.matchMaterial(this.main.getConfig().getString("Agents.Jett.updraft.material")))) {
                    this.cooldownsUpdraft.put(event.getPlayer(), System.currentTimeMillis() + (5000L));
                    if (main.useJettUpdraft > 0) {
                        Vector jump = player.getVelocity().setY(1.5);
                        player.setVelocity(jump);
                        main.useJettUpdraft -= 1;
                    }

                    ItemMeta meta = item.getItemMeta();
                    List<String> lore = new ArrayList<>();
                    lore.add("§b>> §fJett se propulse rapidement vers le haut");
                    lore.add("§b>> §fUtilisations §b" + main.useJettUpdraft + "§f/§b1");
                    meta.setLore(lore);
                    item.setItemMeta(meta);

                    player.getInventory().setItemInHand(item);
                }
            }
        }
    }
    private HashMap<Player, Long> cooldownsDash = new HashMap<>();
    @EventHandler
    public void onDash(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (this.main.isState(GameState.LOBBY)) return;
        if (main.useJettDash != 0) {
            if (this.cooldownsDash.containsKey(event.getPlayer())) {
                long timeLeftUpdraft = (cooldownsDash.get(event.getPlayer()) - System.currentTimeMillis()) / 1000;
                if (timeLeftUpdraft > 0) {
                    //player.sendMessage("Attends un peu pour réutiliser ton Dash !");
                    return;
                } else {
                    this.cooldownsDash.remove(event.getPlayer());
                }
            }
            if (item == null) return;
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase(this.main.getConfig().getString("Agents.Jett.dash.name")) && item.getType().equals(Material.matchMaterial(this.main.getConfig().getString("Agents.Jett.dash.material")))) {
                    this.cooldownsDash.put(event.getPlayer(), System.currentTimeMillis() + (5000L));
                    if (main.useJettDash > 0) {
                        Vector dash = player.getLocation().getDirection();
                        player.setVelocity(dash.multiply(3));
                        main.useJettDash -= 1;
                    }

                    ItemMeta meta = item.getItemMeta();
                    List<String> lore = new ArrayList<>();
                    lore.add("§b>> §fJett se déplace rapidement dans la direction choisie");
                    lore.add("§b>> §fUtilisations §b" + main.useJettDash + "§f/§b1");
                    meta.setLore(lore);
                    item.setItemMeta(meta);

                    player.getInventory().setItemInHand(item);
                }
            }
        }
    }

    @EventHandler
    public void onEgglHit(ProjectileHitEvent event) {
        if (this.main.isState(GameState.LOBBY)) return;
        if (event.getEntity() instanceof Egg) {
            Egg egg = (Egg) event.getEntity();
            Player player = (Player) egg.getShooter();
            ItemStack item = player.getItemInHand();
            Location hitLocation = egg.getLocation();

            if (hitLocation == null) return;
            if (item.getType() == Material.matchMaterial(this.main.getConfig().getString("Agents.Jett.smoke.material"))) {
                playSphereEffect(hitLocation, 4);
            }
        }
    }
    private static final int PARTICLE_COUNT = 500;
    private static final long FREEZE_DURATION_TICKS = 60L;

    public static void playSphereEffect(Location center, double radius) {
        World world = center.getWorld();
        if (world != null) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Random random = new Random();

                    for (int i = 0; i < PARTICLE_COUNT; i++) {
                        double theta = 2 * Math.PI * random.nextDouble();
                        double phi = Math.acos(2 * random.nextDouble() - 1);

                        double x = radius * Math.sin(phi) * Math.cos(theta);
                        double y = radius * Math.sin(phi) * Math.sin(theta);
                        double z = radius * Math.cos(phi);

                        Location particleLocation = center.clone().add(x, y, z);

                        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                                EnumParticle.SMOKE_LARGE,
                                true,
                                (float) particleLocation.getX(),
                                (float) particleLocation.getY(),
                                (float) particleLocation.getZ(),
                                0.1F,
                                0.1F,
                                0.1F,
                                1,
                                0
                        );

                        sendPacketToAllPlayers(world, packet);
                    }
                }
            }.runTaskTimer(Main.getInstance(), 1L, 1L);
            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.getScheduler().cancelTasks(Main.getInstance());
                }
            }.runTaskLater(Main.getInstance(), FREEZE_DURATION_TICKS);
        }
    }

    private static void sendPacketToAllPlayers(World world, PacketPlayOutWorldParticles packet) {
        for (Player player : world.getPlayers()) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }
}
