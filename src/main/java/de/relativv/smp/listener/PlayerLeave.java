package de.relativv.smp.listener;

import de.relativv.smp.main.Smp;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {

    private Smp smp;

    public PlayerLeave(Smp smp) {
        this.smp = smp;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        this.smp.lastHit.remove(p);

        String quitMessage = ChatColor.translateAlternateColorCodes('&', this.smp.fileManager.cfg.getString("messages.leave"));
        e.setQuitMessage(Smp.pr + quitMessage.replaceAll("%player%", p.getName()));
    }
}
