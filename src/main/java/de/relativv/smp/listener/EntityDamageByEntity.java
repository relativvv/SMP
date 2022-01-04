package de.relativv.smp.listener;

import de.relativv.smp.main.Smp;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
import java.util.Map;

public class EntityDamageByEntity implements Listener {

    private Smp smp;

    public EntityDamageByEntity(Smp smp) {
        this.smp = smp;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        Player d = null;
        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            if(e.getDamager() instanceof Player) {
                d = (Player) e.getDamager();
            } else if(e.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) e.getDamager();
                if(arrow.getShooter() instanceof Player) {
                    d = (Player) arrow.getShooter();
                }
            } else if(e.getDamager() instanceof Snowball) {
                Snowball snowball = (Snowball) e.getDamager();
                if(snowball.getShooter() instanceof Player) {
                    d = (Player) snowball.getShooter();
                }
            } else if(e.getDamager() instanceof Egg) {
                Egg egg = (Egg) e.getDamager();
                if(egg.getShooter() instanceof Player) {
                    d = (Player) egg.getShooter();
                }
            } else if(e.getDamager() instanceof EnderPearl) {
                EnderPearl ep = (EnderPearl) e.getDamager();
                if(ep.getShooter() instanceof Player) {
                    d = (Player) ep.getShooter();
                }
            } else if(e.getDamager() instanceof EnderCrystal) {
                EnderCrystal crystal = (EnderCrystal) e.getDamager();
                Iterator<Map.Entry<Player, Location>> iterator = this.smp.placed.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry<Player, Location> entry = iterator.next();
                    if (crystal.getLocation().subtract(0, 1, 0).equals(entry.getValue())) {
                        d = entry.getKey();
                        iterator.remove();
                    }
                }
            }

            if(d != null) {
                this.smp.lastHit.remove(p);
                this.smp.lastHit.put(p, d);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        smp.lastHit.remove(p);
                    }
                }.runTaskLater(this.smp, 20*30);
            }
        }
    }
}
