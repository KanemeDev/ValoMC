package fr.kaneme.valorant.utils;

import fr.kaneme.valorant.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockBuilder {
    private Main main;
    public BlockBuilder(Main plugin) {
        this.main = plugin;
    }

    public void blockBuild(Location location1, Location location2, Material material) {
        double xmin1 = Math.min(location1.getX(), location2.getX());
        double ymin1 = Math.min(location1.getY(), location2.getY());
        double zmin1 = Math.min(location1.getZ(), location2.getZ());
        double xmax1 = Math.max(location1.getX(), location2.getX());
        double ymax1 = Math.max(location1.getY(), location2.getY());
        double zmax1 = Math.max(location1.getZ(), location2.getZ());
        for (double x = xmin1; x <= xmax1; x++) {
            for (double y = ymin1; y <= ymax1; y++) {
                for (double z = zmin1; z <= zmax1; z++) {
                    Block block = new Location(location1.getWorld(),x ,y, z).getBlock();
                    block.setType(material);
                }
            }
        }
    }
}
