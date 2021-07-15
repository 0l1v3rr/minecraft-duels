package hu.oliverr.duels.listeners;

import hu.oliverr.duels.utility.OldPvP;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {

    private final OldPvP oldPvp = new OldPvP();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        oldPvp.attackSpeed(p);
    }

}
