package de.relativv.smp.listener;

import de.relativv.smp.main.Smp;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {

    private Smp smp;

    public BlockPlace(Smp smp) {
        this.smp = smp;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if(e.getBlock().getType() == Material.TNT ||
                e.getBlock().getType() == Material.RESPAWN_ANCHOR ||
                e.getBlock().getType() == Material.BLACK_BED ||
                e.getBlock().getType() == Material.BLUE_BED ||
                e.getBlock().getType() == Material.BROWN_BED ||
                e.getBlock().getType() == Material.CYAN_BED ||
                e.getBlock().getType() == Material.GRAY_BED ||
                e.getBlock().getType() == Material.GREEN_BED ||
                e.getBlock().getType() == Material.LIGHT_BLUE_BED ||
                e.getBlock().getType() == Material.LIGHT_GRAY_BED ||
                e.getBlock().getType() == Material.LIME_BED ||
                e.getBlock().getType() == Material.MAGENTA_BED ||
                e.getBlock().getType() == Material.ORANGE_BED ||
                e.getBlock().getType() == Material.PINK_BED ||
                e.getBlock().getType() == Material.PURPLE_BED ||
                e.getBlock().getType() == Material.RED_BED ||
                e.getBlock().getType() == Material.WHITE_BED ||
                e.getBlock().getType() == Material.YELLOW_BED
        ) {
            this.smp.placed.put(p, e.getBlock().getLocation());
        }
    }
}
