package de.relativv.smp.listener;

import de.relativv.smp.main.Smp;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {

    private Smp smp;

    public PlayerInteract(Smp smp) {
        this.smp = smp;
    }

    @EventHandler
    public void onExtraLife(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(e.getItem() != null) {
                if(e.getItem().getItemMeta() != null) {
                    if(e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4§lExtra Life")) {
                        if(this.smp.lifeManager.getLives(p.getUniqueId().toString()) < 8) {
                            this.smp.lifeManager.addLives(p.getUniqueId().toString(), 1);
                            p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 1, 2);
                        } else {
                            p.sendMessage(Smp.pr + ChatColor.translateAlternateColorCodes('&', this.smp.fileManager.cfg.getString("messages.maximumLives")));
                            p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                        }
                    }
                }
            }
        } else if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(e.getClickedBlock() != null) {
                if(e.getItem() != null) {
                    if (e.getItem().getType() == Material.END_CRYSTAL) {
                        this.smp.placed.put(p, e.getClickedBlock().getLocation());
                    }
                }
            }
        }
    }
}
