package fr.kaneme.valorant.commands;

import fr.kaneme.valorant.Main;
import fr.kaneme.valorant.managers.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Shop implements CommandExecutor {
    private Main main;
    public Shop(Main mainPlugin) {
        this.main = mainPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        Player player = (Player) sender;
        if (Main.getInstance().isState(GameState.GAME)) return false;
        if(sender instanceof Player) {
            Inventory shopGui = Bukkit.createInventory((Player) sender, 27, this.main.getConfig().getString("Shop.name"));
            ItemStack vandal = this.main.getItemBuilder().loadItem(this.main.getConfig().getConfigurationSection("Shop.vandal"));
            ItemStack stinger = this.main.getItemBuilder().loadItem(this.main.getConfig().getConfigurationSection("Shop.stinger"));
            ItemStack shotgun = this.main.getItemBuilder().loadItem(this.main.getConfig().getConfigurationSection("Shop.shotgun"));

            ItemStack whiteGlass = this.main.getItemBuilder().loadItem(this.main.getConfig().getConfigurationSection("Shop.complete-block"));
            for (int i = 0; i < shopGui.getSize(); i++) {
                ItemStack item = shopGui.getItem(i);
                if (item == null || item.getType() == Material.AIR) {
                    shopGui.setItem(i, whiteGlass);
                }
            }

            shopGui.setItem(10, vandal);
            shopGui.setItem(13, stinger);
            shopGui.setItem(16, shotgun);

            player.openInventory(shopGui);
        }
        return false;
    }
}
