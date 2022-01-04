package de.relativv.smp.listener;

import de.relativv.smp.main.Smp;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Iterator;
import java.util.Map;

public class BlockBreak implements Listener {

    private Smp smp;

    public BlockBreak(Smp smp) {
        this.smp = smp;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Iterator<Map.Entry<Player, Location>> iterator = this.smp.placed.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Player, Location> entry = iterator.next();
            if (e.getBlock().getLocation().subtract(0, 0, 0).equals(entry.getValue())) {
                iterator.remove();
            }
        }
    }
}
