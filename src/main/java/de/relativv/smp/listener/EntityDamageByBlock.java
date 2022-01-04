package de.relativv.smp.listener;

import de.relativv.smp.main.Smp;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
import java.util.Map;

public class EntityDamageByBlock implements Listener {

    private Smp smp;
    public EntityDamageByBlock(Smp smp) {
        this.smp = smp;
    }

    @EventHandler
    public void onEntityDamageByBlock(EntityDamageByBlockEvent e) {
        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            Iterator<Map.Entry<Player, Location>> iterator = this.smp.placed.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<Player, Location> entry = iterator.next();

                if (e.getDamager().getLocation().subtract(0, 1, 0).equals(entry.getValue())) {
                    this.smp.lastHit.remove(p);
                    this.smp.lastHit.put(p, entry.getKey());
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            smp.lastHit.remove(p);
                        }
                    }.runTaskLater(this.smp, 20*30);
                    iterator.remove();
                }
            }
        }
    }
}
