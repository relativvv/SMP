package de.relativv.smp.utils;

import de.relativv.smp.main.Smp;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class LifeManager {

    private Smp smp;

    public LifeManager(Smp smp) {
        this.smp = smp;
    }

    public int getLives(String uuidString) {
        if(this.smp.fileManager.lifeConfiguration.get(uuidString) != null) {
            return this.smp.fileManager.lifeConfiguration.getInt(uuidString);
        } else {
            this.smp.fileManager.lifeConfiguration.set(uuidString, 5);
            this.smp.fileManager.saveLifeFile();
            return this.getLives(uuidString);
        }
    }

    public void setLives(String uuidString, int lives) {
        if(lives >= 8) {
            this.smp.fileManager.lifeConfiguration.set(uuidString, 8);
        }
        this.smp.fileManager.lifeConfiguration.set(uuidString, lives);
        this.smp.fileManager.saveLifeFile();
    }

    public void addLives(String uuidString, int lives) {
        int currentLives = this.getLives(uuidString);
        int newLives = currentLives + lives;

        if(newLives >= 8) {
            this.smp.fileManager.lifeConfiguration.set(uuidString, 8);
        }
        this.smp.fileManager.lifeConfiguration.set(uuidString, newLives);
        this.smp.fileManager.saveLifeFile();
    }

    public void removeLives(Player p, int lives) {
        int currentLives = this.getLives(p.getUniqueId().toString());
        int newLives = currentLives - lives;
        if(newLives <= 0) {
            this.smp.fileManager.lifeConfiguration.set(p.getUniqueId().toString(), 0);
            Bukkit.getBanList(BanList.Type.NAME).addBan(p.getName(), "ELIMINATED", null, "Server");
            p.kickPlayer(ChatColor.translateAlternateColorCodes('&', this.smp.fileManager.cfg.getString("messages.eliminated")));

            String broadcastEliminated = this.smp.fileManager.cfg.getString("messages.broadcastEliminated");
            Bukkit.broadcastMessage(Smp.pr + broadcastEliminated.replaceAll("%player%", p.getName()));
            for(Player all : Bukkit.getOnlinePlayers()) {
                all.playSound(all.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
            }
        }
        this.smp.fileManager.lifeConfiguration.set(p.getUniqueId().toString(), newLives);
        this.smp.fileManager.saveLifeFile();
    }

    public String getLifesAsString(Player p) {
        if(this.getLives(p.getUniqueId().toString()) >= 6) {
            return "§2" + this.getLives(p.getUniqueId().toString());
        } else if(this.getLives(p.getUniqueId().toString()) >= 4) {
            return "§a" + this.getLives(p.getUniqueId().toString());
        } else if(this.getLives(p.getUniqueId().toString()) >= 2) {
            return "§e" + this.getLives(p.getUniqueId().toString());
        } else if(this.getLives(p.getUniqueId().toString()) == 1) {
            return "§c" + this.getLives(p.getUniqueId().toString());
        }

        return "§r";
    }

}
