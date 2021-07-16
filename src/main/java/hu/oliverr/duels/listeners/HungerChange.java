package hu.oliverr.duels.listeners;

import hu.oliverr.duels.Duels;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerChange implements Listener {

    private final Duels plugin = Duels.getInstance();

    @EventHandler
    public void onHungerChange(FoodLevelChangeEvent e) {
        if(e.getEntity() instanceof Player) {
            plugin.getGames().forEach((name, game) -> {
                if (game.getPlayersInGame().contains((Player)e.getEntity()))
                    e.setCancelled(!game.isHunger());
            });
        }
    }

}
