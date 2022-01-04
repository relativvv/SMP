package de.relativv.smp.listener;

import de.relativv.smp.main.Smp;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerJoin implements Listener {

    private Smp smp;

    public PlayerJoin(Smp smp) {
        this.smp = smp;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        this.readyPlayer(p);

        String joinMessage = ChatColor.translateAlternateColorCodes('&', this.smp.fileManager.cfg.getString("messages.join"));
        e.setJoinMessage(Smp.pr + joinMessage.replaceAll("%player%", p.getName()));
    }

    private void readyPlayer(Player p) {
        int lives = this.smp.lifeManager.getLives(p.getUniqueId().toString());
        p.setMaxHealth(22 - (lives * 2));
        p.setPlayerListName(p.getName() + " (" + this.smp.lifeManager.getLifesAsString(p) + ")");
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        if(e.getPlayer().isBanned()) {
            e.disallow(PlayerLoginEvent.Result.KICK_BANNED, ChatColor.translateAlternateColorCodes('&', this.smp.fileManager.cfg.getString("messages.eliminated")));
        }
    }
}
