package latham.manhuntextended;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class ManHuntExtended extends JavaPlugin implements Listener {
    private Set<UUID> hunters;

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, (Plugin)this);
        for (String command : getDescription().getCommands().keySet())
            getServer().getPluginCommand(command).setExecutor(this);
        this.hunters = new HashSet<>();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("hunter")) {
            if (args.length != 2) {
                sendInvalid(sender);
                return false;
            }
            Player player = Bukkit.getPlayer(args[1]);
            if (player == null) {
                sender.sendMessage(ChatColor.RED + "Player not found.");
                return false;
            }
            if (args[0].equalsIgnoreCase("add")) {
                this.hunters.add(player.getUniqueId());
                sender.sendMessage(ChatColor.GREEN + player.getName() + " is now a hunter.");
                player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.COMPASS) });
            } else if (args[0].equalsIgnoreCase("remove")) {
                this.hunters.remove(player.getUniqueId());
                sender.sendMessage(ChatColor.GREEN + player.getName() + " is no longer a hunter.");
                player.getInventory().remove(new ItemStack(Material.COMPASS));
            } else {
                sendInvalid(sender);
            }
        }
        return false;
    }

    private void sendInvalid(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "Invalid usage. Please use:");
        sender.sendMessage(ChatColor.RED + "/hunter add <name>");
        sender.sendMessage(ChatColor.RED + "/hunter remove <name>");
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (this.hunters.contains(player.getUniqueId()) && event.hasItem() && event.getItem().getType() == Material.COMPASS && (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)) {
            Player nearest = null;
            double distance = Double.MAX_VALUE;
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.equals(player) || !onlinePlayer.getWorld().equals(player.getWorld()) || this.hunters.contains(onlinePlayer.getUniqueId()))
                    continue;
                double distanceSquared = onlinePlayer.getLocation().distanceSquared(player.getLocation());
                if (distanceSquared < distance) {
                    distance = distanceSquared;
                    nearest = onlinePlayer;
                }
            }
            if (nearest == null) {
                player.sendMessage(ChatColor.RED + "No players to track!");
                return;
            }
            player.setCompassTarget(nearest.getLocation());
            player.sendMessage(ChatColor.GREEN + "Compass is now pointing to " + nearest.getName() + ".");
        }
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        if (this.hunters.contains(event.getEntity().getUniqueId()))
            event.getDrops().removeIf(next -> (next.getType() == Material.COMPASS));
    }

    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        if (this.hunters.contains(event.getPlayer().getUniqueId()) && event.getItemDrop().getItemStack().getType() == Material.COMPASS)
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (this.hunters.contains(player.getUniqueId()))
            player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.COMPASS) });
    }
}