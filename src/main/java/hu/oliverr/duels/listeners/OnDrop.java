package hu.oliverr.duels.listeners;

import hu.oliverr.duels.Duels;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class OnDrop implements Listener {

    private final Duels plugin = Duels.getInstance();

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        plugin.getGames().forEach((name, game) -> {
            if(game.getPlayersInGame().contains(e.getPlayer())) e.setCancelled(true);
        });
    }

}
