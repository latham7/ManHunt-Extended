package latham.manhuntextended.events;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {
    public static String targetName;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        if (player.getWorld().getEnvironment() == World.Environment.NORMAL){
            //System.out.println(Bukkit.getOnlinePlayers());
            String playerName = player.getPlayerListName();
            if (!playerName.equals(targetName)){
                for(Player p : Bukkit.getOnlinePlayers()){
                    p.setCompassTarget(player.getLocation());
                    //System.out.println(p.getCompassTarget());
                }
            }
        }
    }
}
