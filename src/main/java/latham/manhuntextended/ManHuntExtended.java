package latham.manhuntextended;

import latham.manhuntextended.commands.Hunter;
import latham.manhuntextended.commands.HunterDebug;
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

        getCommand("hunter").setExecutor(new Hunter());
        getCommand("hunterdebug").setExecutor(new HunterDebug());

        //getServer().getPluginCommand(command).setExecutor(this);
        // this.hunters = new HashSet<>();
    }
}
