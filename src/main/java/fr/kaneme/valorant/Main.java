package fr.kaneme.valorant;

import fr.kaneme.valorant.commands.Agents;
import fr.kaneme.valorant.commands.Join;
import fr.kaneme.valorant.commands.Shop;
import fr.kaneme.valorant.commands.Start;
import fr.kaneme.valorant.listeners.*;
import fr.kaneme.valorant.managers.GameState;
import fr.kaneme.valorant.managers.Team;
import fr.kaneme.valorant.utils.BlockBuilder;
import fr.kaneme.valorant.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private ItemBuilder itemBuilder;
    private BlockBuilder blockBuilder;
    private static Main instance;
    //JETT
    public int useJettSmoke = 2;
    public int useJettUpdraft = 1;
    public int useJettDash = 1;
    //OMEN
    public int useOmenSmoke = 2;
    public int useOmenTp = 2;
    public int useOmenFlash = 1;
    //GAME STATE
    private GameState state;
    //GAME PLAYERS
    private Team red;
    private Team blue;

    @Override
    public void onEnable() {
        instance = this;
        this.itemBuilder = new ItemBuilder(this);
        this.blockBuilder = new BlockBuilder(this);
        saveDefaultConfig();
        Bukkit.getConsoleSender().sendMessage("[Valorant] - Plugin Enabled");
        setState(GameState.LOBBY);
        this.red = new Team();
        this.blue = new Team();
        getCommand("shop").setExecutor(new Shop(this));
        getCommand("agents").setExecutor(new Agents(this));
        getCommand("start").setExecutor(new Start(this));
        getCommand("join").setExecutor(new Join(this));
        Bukkit.getPluginManager().registerEvents(new ShopListener(), this);
        Bukkit.getPluginManager().registerEvents(new Vandal(), this);
        Bukkit.getPluginManager().registerEvents(new Stinger(), this);
        Bukkit.getPluginManager().registerEvents(new Shotgun(), this);
        Bukkit.getPluginManager().registerEvents(new Guns(this), this);
        Bukkit.getPluginManager().registerEvents(new JettPowers(this), this);
        Bukkit.getPluginManager().registerEvents(new OmenPowers(this), this);
        Bukkit.getPluginManager().registerEvents(new AgentsListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GamePlayerListener(), this);
    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("[Valorant] - Plugin Disabled");
    }
    //STATE
    public void setState(GameState state) {
        this.state = state;
    }
    public boolean isState(GameState state) {
        return this.state == state;
    }

    //PLAYERS
    public Team getRed() {
        return red;
    }

    public Team getBlue() {
        return blue;
    }

    //UTILS
    public ItemBuilder getItemBuilder() {
        return this.itemBuilder;
    }

    public BlockBuilder getBlockBuilder() {
        return blockBuilder;
    }

    public static Main getInstance() {
        return instance;
    }
}
