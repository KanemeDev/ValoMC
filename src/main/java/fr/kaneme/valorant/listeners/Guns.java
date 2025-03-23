package fr.kaneme.valorant.listeners;

import fr.kaneme.valorant.Main;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class Guns implements Listener {
    private Main main;
    public Guns(Main plugin) {
        this.main = plugin;
    }

    @EventHandler
    public void onEggHitFloor(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Egg) {
            Egg egg = (Egg) event.getEntity();
            egg.getWorld().getEntitiesByClass(Chicken.class).forEach(chicken -> {
                if (chicken.getType() == EntityType.CHICKEN) {
                    chicken.remove();
                }
            });
        }
    }

    @EventHandler
    public void onPlayerPickupArrow(PlayerPickupItemEvent event) {
        Item item = event.getItem();
        if(item.getItemStack().getType() == Material.ARROW) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onDamageSnowball(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Snowball) {
            Projectile projectile = (Projectile) event.getDamager();
            Player player = (Player) projectile.getShooter();
            ItemStack item = player.getItemInHand();
            if (item == null) return;
            if (item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase(this.main.getConfig().getString("Shop.vandal.name")) && item.getType().equals(Material.WOOD_PICKAXE)) {
                event.setDamage(EntityDamageEvent.DamageModifier.BASE, 5); //VANDAL

            } else if (item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase(this.main.getConfig().getString("Shop.stinger.name")) && item.getType().equals(Material.WOOD_HOE)) {
                event.setDamage(EntityDamageEvent.DamageModifier.BASE, 3); //STINGER
            }
            else if (item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase(this.main.getConfig().getString("Shop.shotgun.name")) && item.getType().equals(Material.WOOD_SPADE)) {
                event.setDamage(EntityDamageEvent.DamageModifier.BASE, 10); //SHOTGUN
            }
        }
    }
}
