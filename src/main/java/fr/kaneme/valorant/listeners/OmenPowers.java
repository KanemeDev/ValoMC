package fr.kaneme.valorant.listeners;

import fr.kaneme.valorant.Main;
import fr.kaneme.valorant.managers.GameState;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class OmenPowers implements Listener {
    private Main main;
    public OmenPowers(Main plugin) {
        this.main = plugin;
    }
    @EventHandler
    public void onRightClickReset(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (this.main.isState(GameState.LOBBY)) return;
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (item != null && item.getType() == Material.matchMaterial(this.main.getConfig().getString("Agents.Omen.smoke.material")) && item.getItemMeta() != null && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase(this.main.getConfig().getString("Agents.Omen.smoke.name"))) {
                if (!item.getItemMeta().getLore().contains("§5>> §fUtilisations §5" + main.useOmenSmoke + "§f/§52")) {
                    main.useOmenSmoke = 2;
                }
            }
        }
        if (this.main.isState(GameState.LOBBY)) return;
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (item != null && item.getType() == Material.matchMaterial(this.main.getConfig().getString("Agents.Omen.teleport.material")) && item.getItemMeta() != null && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase(this.main.getConfig().getString("Agents.Omen.teleport.name"))) {
                if (!item.getItemMeta().getLore().contains("§5>> §fUtilisations §5" + main.useOmenTp + "§f/§52")) {
                    main.useOmenTp = 2;
                }
            }
        }
        if (this.main.isState(GameState.LOBBY)) return;
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (item != null && item.getType() == Material.matchMaterial(this.main.getConfig().getString("Agents.Omen.flash.material")) && item.getItemMeta() != null && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase(this.main.getConfig().getString("Agents.Omen.flash.name"))) {
                if (!item.getItemMeta().getLore().contains("§5>> §fUtilisations §5" + main.useOmenFlash + "§f/§51")) {
                    main.useOmenFlash = 1;
                }
            }
        }
    }
    @EventHandler
    public void onFlashHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Projectile)) return;
        Projectile projectile = (Projectile) event.getDamager();
        if (!(projectile.getShooter() instanceof Player)) return;
        Player player = (Player) projectile.getShooter();
        if (!(event.getEntity() instanceof Player)) return;
        Player enemy = (Player) event.getEntity();
        ItemStack item = player.getInventory().getItemInHand();

        if (this.main.isState(GameState.LOBBY)) return;
        if (item == null) return;
        if (main.useOmenFlash > 0) {
            if (item.getType() == Material.matchMaterial(this.main.getConfig().getString("Agents.Omen.flash.material"))) {
                ItemStack pumpkin = new ItemStack(Material.PUMPKIN, 1);
                enemy.getInventory().setHelmet(pumpkin);
                enemy.sendMessage(player.getName() + " vous a flash pendant 3s !");

                main.useOmenFlash -= 1;
                ItemMeta meta = item.getItemMeta();
                List<String> lore = new ArrayList<>();
                lore.add("§5>> §fOmen aveugle son adversaire pendant 3s");
                lore.add("§5>> §fUtilisations §5" + main.useOmenFlash + "§f/§51");
                meta.setLore(lore);
                item.setItemMeta(meta);

                player.getInventory().setItemInHand(item);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (enemy.getInventory().getHelmet() != null && enemy.getInventory().getHelmet().getType() == Material.PUMPKIN) {
                            enemy.getInventory().setHelmet(null);
                        }
                    }
                }.runTaskLater(main, 60L);
            }
        }
    }

    @EventHandler
    public void onFlash(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (this.main.isState(GameState.LOBBY)) return;
        if (main.useOmenFlash > 0) {
            if (item == null) return;
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase(this.main.getConfig().getString("Agents.Omen.flash.name")) && item.getType().equals(Material.matchMaterial(this.main.getConfig().getString("Agents.Omen.flash.material")))) {
                    Snowball snowball = player.launchProjectile(Snowball.class);
                    snowball.setVelocity(player.getLocation().getDirection().multiply(2));
                }
            }
        }
    }

    private HashMap<Player, Long> cooldownsTp = new HashMap<>();
    @EventHandler
    public void onTp(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (this.main.isState(GameState.LOBBY)) return;
        if (main.useOmenTp != 0) {
            if (this.cooldownsTp.containsKey(event.getPlayer())) {
                long timeLeftSmoke = (cooldownsTp.get(event.getPlayer()) - System.currentTimeMillis()) / 1000;
                if (timeLeftSmoke > 0 && player.getItemInHand().getType() == Material.matchMaterial(this.main.getConfig().getString("Agents.Omen.teleport.material"))) {
                    //player.sendMessage("Attends un peu pour réutiliser ta Smoke !");
                    return;
                } else {
                    this.cooldownsTp.remove(event.getPlayer());
                }
            }
            if (item == null) return;
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase(this.main.getConfig().getString("Agents.Omen.teleport.name")) && item.getType().equals(Material.matchMaterial(this.main.getConfig().getString("Agents.Omen.teleport.material")))) {
                    Arrow arrow = player.launchProjectile(Arrow.class);
                    arrow.setVelocity(player.getLocation().getDirection().multiply(1.5));
                    this.cooldownsTp.put(event.getPlayer(), System.currentTimeMillis() + (5000L));
                    if (main.useOmenTp > 0) {
                        main.useOmenTp -= 1;
                    }

                    ItemMeta meta = item.getItemMeta();
                    List<String> lore = new ArrayList<>();
                    lore.add("§5>> §fOmen se téléporte a l'endroit visé");
                    lore.add("§5>> §fUtilisations §5" + main.useOmenTp + "§f/§52");
                    meta.setLore(lore);
                    item.setItemMeta(meta);

                    player.getInventory().setItemInHand(item);
                }
            }
        }
    }
    @EventHandler
    public void onTpHitFloor(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow)) {
            return;
        }

        Arrow arrow = (Arrow) event.getEntity();
        Player player = (Player) arrow.getShooter();
        if (player == null) {
            return;
        }

        ItemStack item = player.getInventory().getItemInHand();
        Location hitLocation = arrow.getLocation();

        if (this.main.isState(GameState.LOBBY)) return;
        if (item.getType() == Material.matchMaterial(this.main.getConfig().getString("Agents.Omen.teleport.material"))) {
            float yaw = player.getLocation().getYaw();
            float pitch = player.getLocation().getPitch();

            player.teleport(hitLocation);

            Location newLocation = player.getLocation();
            newLocation.setYaw(yaw);
            newLocation.setPitch(pitch);
            player.teleport(newLocation);
        }
    }

    private HashMap<Player, Long> cooldownsSmoke = new HashMap<>();
    @EventHandler
    public void onSmoke(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (this.main.isState(GameState.LOBBY)) return;
        if (main.useOmenSmoke != 0) {
            if (this.cooldownsSmoke.containsKey(event.getPlayer())) {
                long timeLeftSmoke = (cooldownsSmoke.get(event.getPlayer()) - System.currentTimeMillis()) / 1000;
                if (timeLeftSmoke > 0 && player.getItemInHand().getType() == Material.matchMaterial(this.main.getConfig().getString("Agents.Omen.smoke.material"))) {
                    //player.sendMessage("Attends un peu pour réutiliser ta Smoke !");
                    return;
                } else {
                    this.cooldownsSmoke.remove(event.getPlayer());
                }
            }
            if (item == null) return;
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase(this.main.getConfig().getString("Agents.Omen.smoke.name")) && item.getType().equals(Material.matchMaterial(this.main.getConfig().getString("Agents.Omen.smoke.material")))) {
                    Egg egg = player.launchProjectile(Egg.class);
                    egg.setVelocity(player.getLocation().getDirection().multiply(2));
                    this.cooldownsSmoke.put(event.getPlayer(), System.currentTimeMillis() + (5000L));
                    if (main.useOmenSmoke > 0) {
                        main.useOmenSmoke -= 1;
                    }

                    ItemMeta meta = item.getItemMeta();
                    List<String> lore = new ArrayList<>();
                    lore.add("§5>> §fOmen lance un projectile qui crée un nuage de fumée");
                    lore.add("§5>> §fUtilisations §5" + main.useOmenSmoke + "§f/§52");
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
            if (item.getType() == Material.matchMaterial(this.main.getConfig().getString("Agents.Omen.smoke.material"))) {
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
