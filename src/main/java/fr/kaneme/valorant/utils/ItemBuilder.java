package fr.kaneme.valorant.utils;

import fr.kaneme.valorant.Main;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Iterator;

public class ItemBuilder {
    private Main plugin;

    public ItemBuilder(Main plugin) {
        this.plugin = plugin;
    }

    public ItemStack loadItem(ConfigurationSection itemSection) {
        String materialName;
        if (itemSection.contains("material")) {
            materialName = itemSection.getString("material");
        } else {
            materialName = Material.getMaterial(itemSection.getInt("material-id", 1)).name();
        }

        ItemStack itemStack = new ItemStack(Material.getMaterial(materialName), itemSection.getInt("amount", 1), (short) itemSection.getInt("data", 0));

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemSection.contains("name")) {
            itemMeta.setDisplayName(itemSection.getString("name"));
        }

        if (itemSection.contains("lore")) {
            itemMeta.setLore(itemSection.getStringList("lore"));
        }

        if (itemSection.contains("glow")) {
            itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        if (itemSection.getBoolean("damage-hider", true)) {
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }

        if (itemSection.contains("enchants")) {
            Iterator enchantIterator = itemSection.getStringList("enchants").iterator();

            while(enchantIterator.hasNext()) {
                String line = (String)enchantIterator.next();
                String[] splitter = line.split(":");
                itemMeta.addEnchant(Enchantment.getByName(splitter[0]), Integer.parseInt(splitter[1]), true);
            }
        }

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
