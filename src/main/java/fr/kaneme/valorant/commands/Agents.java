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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class Agents implements CommandExecutor {
    private Main main;
    public Agents(Main plugin) {
        this.main = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        Player player = (Player) sender;
        if (Main.getInstance().isState(GameState.GAME)) return false;
        if (player instanceof Player) {
            Inventory agentsGui = Bukkit.createInventory((Player) sender, 27, this.main.getConfig().getString("Agents.name"));

            //JETT HEAD
            ItemStack jettHead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta jettMeta = (SkullMeta) jettHead.getItemMeta();
            jettMeta.setOwner(this.main.getConfig().getString("Agents.Jett.owner"));
            jettMeta.setDisplayName(this.main.getConfig().getString("Agents.Jett.name"));
            jettMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            jettHead.setItemMeta(jettMeta);

            //OMEN HEAD
            ItemStack omenHead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta omenMeta = (SkullMeta) omenHead.getItemMeta();
            omenMeta.setOwner(this.main.getConfig().getString("Agents.Omen.owner"));
            omenMeta.setDisplayName(this.main.getConfig().getString("Agents.Omen.name"));
            omenMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            omenHead.setItemMeta(omenMeta);

            ItemStack whiteGlass = this.main.getItemBuilder().loadItem(this.main.getConfig().getConfigurationSection("Shop.complete-block"));
            for (int i = 0; i < agentsGui.getSize(); i++) {
                ItemStack item = agentsGui.getItem(i);
                if (item == null || item.getType() == Material.AIR) {
                    agentsGui.setItem(i, whiteGlass);
                }
            }

            agentsGui.setItem(10, jettHead);
            agentsGui.setItem(13, omenHead);
            player.openInventory(agentsGui);
        }

        return false;
    }
}
