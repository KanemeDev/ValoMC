package fr.kaneme.valorant.listeners;

import fr.kaneme.valorant.Main;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Vandal implements Listener {
    private HashMap<Player, Long> cooldownsVandal = new HashMap<>();

    @EventHandler
    public void onVandal(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if(this.cooldownsVandal.containsKey(event.getPlayer())) {
            long timeLeft = (cooldownsVandal.get(event.getPlayer()) - System.currentTimeMillis()) / 1000;
            if (timeLeft > 0) {
                return;
            } else {
                this.cooldownsVandal.remove(event.getPlayer());
            }
        }
        if (item == null) return;
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase(Main.getInstance().getConfig().getString("Shop.vandal.name")) && item.getType().equals(Material.matchMaterial(Main.getInstance().getConfig().getString("Shop.vandal.material")))) {
                Snowball snowball = player.launchProjectile(Snowball.class);
                snowball.setVelocity(player.getLocation().getDirection().multiply(3));
                this.cooldownsVandal.put(event.getPlayer(), System.currentTimeMillis() + (1300L));
            }
        }
    }
}
