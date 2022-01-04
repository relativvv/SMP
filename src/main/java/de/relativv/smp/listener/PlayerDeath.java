package de.relativv.smp.listener;

import de.relativv.smp.main.Smp;
import de.relativv.smp.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDeath implements Listener {

    private Smp smp;

    public PlayerDeath(Smp smp) {
        this.smp = smp;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        e.setDeathMessage(null);

        for (Player all : Bukkit.getOnlinePlayers()) {
            all.playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1, 1);
        }

        String deathMessage = "";
        if (p.getKiller() != null) {
            Player k = p.getKiller();

            this.playerKill(p, k);

            deathMessage = ChatColor.translateAlternateColorCodes('&', this.smp.fileManager.cfg.getString("messages.deathSlain"));
            Bukkit.broadcastMessage(deathMessage.replaceAll("%player%", p.getName()).replaceAll("%killer%", k.getName()));
            return;
        }

        if (this.smp.lastHit.containsKey(p)) {
            Player k = this.smp.lastHit.get(p);

            this.playerKill(p, k);

            deathMessage = ChatColor.translateAlternateColorCodes('&', this.smp.fileManager.cfg.getString("messages.deathSlain"));
            Bukkit.broadcastMessage(deathMessage.replaceAll("%player%", p.getName()).replaceAll("%killer%", k.getName()));
            return;
        }

        this.smp.fileManager.saveLifeFile();
        deathMessage = ChatColor.translateAlternateColorCodes('&', this.smp.fileManager.cfg.getString("messages.deathNoKiller"));
        Bukkit.broadcastMessage(Smp.pr + deathMessage.replaceAll("%player%", p.getName()));
        p.setPlayerListName(p.getName() + " (" + this.smp.lifeManager.getLifesAsString(p) + ")");
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        p.setMaxHealth(22 - (this.smp.lifeManager.getLives(p.getUniqueId().toString()) * 2));
        p.setPlayerListName(p.getName() + " (" + this.smp.lifeManager.getLifesAsString(p) + ")");
    }

    private void playerKill(Player dead, Player killer) {
        this.smp.lifeManager.removeLives(dead, 1);
        this.smp.lifeManager.addLives(killer.getUniqueId().toString(), 1);
        this.smp.fileManager.saveLifeFile();
        if(this.smp.lifeManager.getLives(killer.getUniqueId().toString()) >= 8) {
            ItemStack extraLife = new ItemBuilder(Material.RED_DYE, 1)
                    .setDisPlayname("§4§lExtra Life")
                    .setLore(" ", "§7Right-Click to gain an extra life")
                    .build();
            dead.getWorld().dropItemNaturally(dead.getLocation(), extraLife);
        }
        killer.setMaxHealth(22 - (this.smp.lifeManager.getLives(killer.getUniqueId().toString()) * 2));
        killer.setPlayerListName(killer.getName() + " (" + this.smp.lifeManager.getLifesAsString(killer) + ")");
    }
}
