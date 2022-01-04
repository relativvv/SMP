package de.relativv.smp.listener;

import de.relativv.smp.main.Smp;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;

import java.util.Iterator;
import java.util.Map;

public class BlockExplode implements Listener {

    private Smp smp;
    public BlockExplode(Smp smp) {
        this.smp = smp;
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent e) {
        Iterator<Map.Entry<Player, Location>> iterator = this.smp.placed.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Player, Location> entry = iterator.next();

            for(Block block : e.blockList()) {
                if (block.getLocation().subtract(0, 1, 0).equals(entry.getValue())) {
                    iterator.remove();
                }
            }
        }
    }
}
