package latham.manhuntextended.commands;

import latham.manhuntextended.ManHuntExtended;
import latham.manhuntextended.events.PlayerMove;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;


public class Hunter implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("hunter")) {
            if (args.length != 2) {
                invalidMessage(sender);
                return false;
            }
            Player player = Bukkit.getPlayer(args[1]);
            if (player == null) {
                sender.sendMessage(ChatColor.RED + "Player not found");
                return false;
            }
            if (args[0].equalsIgnoreCase("player")) {
                System.out.println(player.getPlayerListName());
            }
            if (args[0].equalsIgnoreCase("add")) {
                // Gives player a compass -- later add that they can only have one compass
                sender.sendMessage(ChatColor.GREEN + player.getName() + " is now a hunter.");
                player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.COMPASS) });
                PlayerMove playerMove = new PlayerMove();
                playerMove.targetName = args[1];
            } else if (args[0].equalsIgnoreCase("remove")) {

                sender.sendMessage(ChatColor.GREEN + player.getName() + "is no longer a hunter");
            } else {
                invalidMessage(sender);
            }
        }
        return false;
    }
    private void invalidMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "Invalid usage. Please use:");
        sender.sendMessage(ChatColor.RED + "/hunter add <name>");
        sender.sendMessage(ChatColor.RED + "/hunter remove <name>");
    }
    private Set<UUID> hunters;
}



