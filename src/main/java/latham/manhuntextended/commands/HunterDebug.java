package latham.manhuntextended.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HunterDebug implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("hunterdebug")) {
            if (args[0].equalsIgnoreCase("plist")){
                System.out.println(Bukkit.getOnlinePlayers());
            }
        }
        return false;
    }
}
