package de.relativv.smp.commands;

import de.relativv.smp.main.Smp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ManageLives implements CommandExecutor {

    private Smp smp;

    public ManageLives(Smp smp) {
        this.smp = smp;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;

            if(p.isOp() || p.hasPermission("*")) {
                
            } else {
                p.sendMessage(Smp.pr + Smp.noPerm);
            }
        }
        return true;
    }
}
