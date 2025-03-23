package fr.kaneme.valorant.listeners;

import fr.kaneme.valorant.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ShopListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        FileConfiguration config = Main.getInstance().getConfig();
        if (clickedItem != null) {
            if (inv.getName().equalsIgnoreCase(config.getString("Shop.name"))) {
                if (clickedItem.getType() == Material.STAINED_GLASS_PANE) {
                    event.setCancelled(true);
                } else if (clickedItem.getType() == Material.matchMaterial(config.getString("Shop.vandal.material")) ||clickedItem.getType() == Material.matchMaterial(config.getString("Shop.stinger.material")) ||clickedItem.getType() == Material.matchMaterial(config.getString("Shop.shotgun.material"))) {
                    event.setCancelled(true);
                    player.getInventory().setItem(0, clickedItem);
                }
            }
        }
    }
}