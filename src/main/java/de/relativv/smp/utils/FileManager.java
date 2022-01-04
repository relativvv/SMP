package de.relativv.smp.utils;

import de.relativv.smp.main.Smp;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private Smp smp;

    public File config;
    public FileConfiguration cfg;

    public File lives;
    public FileConfiguration lifeConfiguration;

    public FileManager(Smp smp) {
        this.smp = smp;

        this.lives = new File("plugins/Smp/lives.yml");
        this.config = new File("plugins/Smp/config.yml");

        this.lifeConfiguration = YamlConfiguration.loadConfiguration(this.lives);
        this.cfg = YamlConfiguration.loadConfiguration(config);
    }

    public void setConfigDefaults() {
        this.cfg.options().copyDefaults(true);
        this.cfg.addDefault("prefix", "&8▌ &2Survival &7»");
        this.cfg.addDefault("noPermission", "&cYou don't have the correct permission to perform this action!");
        this.cfg.addDefault("messages.join", "&a%player% &7has joined the game.");
        this.cfg.addDefault("messages.leave", "&a%player% &7has left the game.");
        this.cfg.addDefault("messages.eliminated", "&4You are eliminated!");
        this.cfg.addDefault("messages.deathNoKiller", "&a%player% &7died.");
        this.cfg.addDefault("messages.deathSlain", "&a%player% &7was slain by &a%killer%.");
        this.cfg.addDefault("messages.broadcastEliminated", "&a%player% &4has been eliminated.");
        this.cfg.addDefault("messages.maximumLives", "&cYou reached the maximum of 8 lives!");
        this.cfg.addDefault("messages.lifetransfersender", "&7You transferred &a%amount% &7lives to &a%target%");
        this.cfg.addDefault("messages.lifetransfertarget", "&7You got &a%amount% &7lives from &a%sender%");
        this.saveConfig();
    }

    public void saveLifeFile() {
        try {
            this.lifeConfiguration.save(this.lives);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            this.cfg.save(this.config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
