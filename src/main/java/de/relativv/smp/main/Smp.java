package de.relativv.smp.main;

import de.relativv.smp.commands.LifeTransfer;
import de.relativv.smp.commands.ManageLives;
import de.relativv.smp.listener.*;
import de.relativv.smp.utils.FileManager;
import de.relativv.smp.utils.LifeManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Smp extends JavaPlugin {

    private PluginManager pm;
    private ConsoleCommandSender cs;

    public FileManager fileManager;
    public LifeManager lifeManager;
    public HashMap<Player, Player> lastHit;
    public HashMap<Player, Location> placed;

    public static String pr;
    public static String noPerm;
    public static Smp instance;

    @Override
    public void onEnable() {
        instance = this;

        this.pm = Bukkit.getServer().getPluginManager();
        this.cs = Bukkit.getConsoleSender();

        this.fileManager = new FileManager(this);
        this.lifeManager = new LifeManager(this);

        this.lastHit = new HashMap<Player, Player>();
        this.placed = new HashMap<Player, Location>();

        this.fileManager.setConfigDefaults();

        this.register();

        for(Player all : Bukkit.getOnlinePlayers()) {
            all.setPlayerListName(all.getName() + " (" + this.lifeManager.getLifesAsString(all) + ")");
        }

        sendConsoleMessage("§e============== §aSurvival §e=============");
        sendConsoleMessage(" ");
        sendConsoleMessage("§3Author§8: §a" + this.getDescription().getAuthors());
        sendConsoleMessage("§3Version§8: §a" + this.getDescription().getVersion());
        sendConsoleMessage(" ");
        sendConsoleMessage("§a§lLOADED");
        sendConsoleMessage(" ");
        sendConsoleMessage(" ");
        sendConsoleMessage("§e============== §aSurvival §e=============");
    }

    @Override
    public void onDisable() {
        sendConsoleMessage("§e============== §aSurvival §e=============");
        sendConsoleMessage(" ");
        sendConsoleMessage("§3Author§8: §a" + this.getDescription().getAuthors());
        sendConsoleMessage("§3Version§8: §a" + this.getDescription().getVersion());
        sendConsoleMessage(" ");
        sendConsoleMessage("§4§lUNLOADED");
        sendConsoleMessage(" ");
        sendConsoleMessage(" ");
        sendConsoleMessage("§e============== §aSurvival §e=============");
    }

    private void register() {
        pr = ChatColor.translateAlternateColorCodes('&', this.fileManager.cfg.getString("prefix") + " §r");
        noPerm = this.fileManager.cfg.contains("noPerm") ? ChatColor.translateAlternateColorCodes('&', this.fileManager.cfg.getString("noPerm")) : "You don't have permission to do that.";

        this.pm.registerEvents(new BlockBreak(this), this);
        this.pm.registerEvents(new BlockExplode(this), this);
        this.pm.registerEvents(new BlockPlace(this), this);
        this.pm.registerEvents(new EntityDamageByBlock(this), this);
        this.pm.registerEvents(new EntityDamageByEntity(this), this);
        this.pm.registerEvents(new EntityDeath(this), this);
        this.pm.registerEvents(new EntityExplode(this), this);
        this.pm.registerEvents(new PlayerDeath(this), this);
        this.pm.registerEvents(new PlayerInteract(this), this);
        this.pm.registerEvents(new PlayerJoin(this), this);
        this.pm.registerEvents(new PlayerLeave(this), this);

        this.getCommand("lifetransfer").setExecutor(new LifeTransfer(this));
        this.getCommand("life").setExecutor(new ManageLives(this));
    }

    private void sendConsoleMessage(String msg) {
        this.cs.sendMessage(msg);
    }
}