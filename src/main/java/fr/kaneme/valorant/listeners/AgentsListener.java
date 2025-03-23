package fr.kaneme.valorant.listeners;

import fr.kaneme.valorant.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class AgentsListener implements Listener {
    private Main main;
    public AgentsListener(Main plugin) {
        this.main = plugin;
    }

    @EventHandler
    public void onClickOnInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        Inventory inventory = event.getInventory();
        if (player instanceof Player) {
            if (clickedItem != null) {
                if (inventory.getName() == this.main.getConfig().getString("Agents.name")) {
                    event.setCancelled(true);
                    if (clickedItem.getType() == Material.SKULL_ITEM && clickedItem.getItemMeta().hasDisplayName() && clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(this.main.getConfig().getString("Agents.Jett.name"))) {
                        //JETT HEAD
                        ItemStack jettHead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                        SkullMeta jettMeta = (SkullMeta) jettHead.getItemMeta();
                        jettMeta.setOwner(this.main.getConfig().getString("Agents.Jett.owner"));
                        jettMeta.setDisplayName(this.main.getConfig().getString("Agents.Jett.name"));
                        jettMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                        jettHead.setItemMeta(jettMeta);


                        ItemStack jettSmoke = this.main.getItemBuilder().loadItem(this.main.getConfig().getConfigurationSection("Agents.Jett.smoke"));
                        ItemStack jettUpdraft = this.main.getItemBuilder().loadItem(this.main.getConfig().getConfigurationSection("Agents.Jett.updraft"));
                        ItemStack jettDash = this.main.getItemBuilder().loadItem(this.main.getConfig().getConfigurationSection("Agents.Jett.dash"));
                        ItemStack jettKnife = this.main.getItemBuilder().loadItem(this.main.getConfig().getConfigurationSection("Agents.Jett.knife"));
                        player.getInventory().setItem(0, jettKnife);
                        player.getInventory().setItem(2, jettSmoke);
                        player.getInventory().setItem(3, jettUpdraft);
                        player.getInventory().setItem(4, jettDash);
                        player.getInventory().setItem(8, jettHead);

                        ItemStack whiteGlass = this.main.getItemBuilder().loadItem(this.main.getConfig().getConfigurationSection("Shop.complete-block"));
                        for (int i = 0; i < player.getInventory().getSize(); i++) {
                            ItemStack item = player.getInventory().getItem(i);
                            if (item == null || item.getType() == Material.AIR) {
                                player.getInventory().setItem(i, whiteGlass);
                            }
                        }
                    }
                } if (clickedItem.getType() == Material.SKULL_ITEM && clickedItem.getItemMeta().hasDisplayName() && clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(this.main.getConfig().getString("Agents.Omen.name"))) {
                    //OMEN HEAD
                    ItemStack omenHead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                    SkullMeta omenMeta = (SkullMeta) omenHead.getItemMeta();
                    omenMeta.setOwner(this.main.getConfig().getString("Agents.Omen.owner"));
                    omenMeta.setDisplayName(this.main.getConfig().getString("Agents.Omen.name"));
                    omenMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    omenHead.setItemMeta(omenMeta);

                    ItemStack omenTp = this.main.getItemBuilder().loadItem(this.main.getConfig().getConfigurationSection("Agents.Omen.teleport"));
                    ItemStack omenFlash = this.main.getItemBuilder().loadItem(this.main.getConfig().getConfigurationSection("Agents.Omen.flash"));
                    ItemStack omenSmoke = this.main.getItemBuilder().loadItem(this.main.getConfig().getConfigurationSection("Agents.Omen.smoke"));
                    ItemStack omenKnife = this.main.getItemBuilder().loadItem(this.main.getConfig().getConfigurationSection("Agents.Omen.knife"));
                    player.getInventory().setItem(0, omenKnife);
                    player.getInventory().setItem(2, omenTp);
                    player.getInventory().setItem(3, omenFlash);
                    player.getInventory().setItem(4, omenSmoke);
                    player.getInventory().setItem(8, omenHead);

                    ItemStack whiteGlass = this.main.getItemBuilder().loadItem(this.main.getConfig().getConfigurationSection("Shop.complete-block"));
                    for (int i = 0; i < player.getInventory().getSize(); i++) {
                        ItemStack item = player.getInventory().getItem(i);
                        if (item == null || item.getType() == Material.AIR) {
                            player.getInventory().setItem(i, whiteGlass);
                        }
                    }
                }
            }
        }
    }
}
