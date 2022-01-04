package de.relativv.smp.commands;

import de.relativv.smp.main.Smp;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LifeTransfer implements CommandExecutor {

    private Smp smp;

    public LifeTransfer(Smp smp) {
        this.smp = smp;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            int amount = 0;

            if(args.length == 2) {
                try {
                    amount = Integer.parseInt(args[0]);
                } catch(NumberFormatException ex ) {
                    p.sendMessage(Smp.pr + "§cYou need to set a number as amount!");
                    p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                    return true;
                }

                int playerLives = this.smp.lifeManager.getLives(p.getUniqueId().toString());
                int newLives = playerLives - amount;
                if(newLives <= 0) {
                    p.sendMessage(Smp.pr + "§cYou can't transfer that much lives. Your lives would fall to 0.");
                    p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                    return true;
                }

                String name = args[1];
                OfflinePlayer target = Bukkit.getOfflinePlayer(name);
                int currentLives = this.smp.lifeManager.getLives(target.getUniqueId().toString());
                int newLivesTarget = currentLives + amount;
                if(newLivesTarget > 8) {
                    p.sendMessage(Smp.pr + "§cYou can't transfer that much lives. The targets lives would be larger than 8.");
                    p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
                    return true;
                }

                this.smp.lifeManager.removeLives(p, amount);
                this.smp.lifeManager.addLives(target.getUniqueId().toString(), amount);
                this.smp.fileManager.saveLifeFile();
                p.setPlayerListName(p.getName() + " (" + this.smp.lifeManager.getLifesAsString(p) + ")");

                String senderMsg = ChatColor.translateAlternateColorCodes('&', this.smp.fileManager.cfg.getString("messages.lifetransfersender"));
                p.sendMessage(Smp.pr + senderMsg.replaceAll("%amount%", String.valueOf(amount)).replaceAll("%target%", target.getName()));
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 1, 2);

                if(target.isOnline()) {
                    Player targetPlayer = Bukkit.getPlayer(target.getUniqueId());
                    String targetMsg = ChatColor.translateAlternateColorCodes('&', this.smp.fileManager.cfg.getString("messages.lifetransfertarget"));
                    targetPlayer.sendMessage(Smp.pr + targetMsg.replaceAll("%amount%", String.valueOf(amount)).replaceAll("%sender%", p.getName()));
                    targetPlayer.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 1, 2);
                    targetPlayer.setPlayerListName(targetPlayer.getName() + " (" + this.smp.lifeManager.getLifesAsString(targetPlayer) + ")");
                }

            } else {
                p.sendMessage(Smp.pr + "§c/lifetransfer <Amount> <Player>");
            }
        }
        return true;
    }
}
