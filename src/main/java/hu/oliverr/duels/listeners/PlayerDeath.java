package hu.oliverr.duels.listeners;

import hu.oliverr.duels.Duels;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

    private final Duels plugin = Duels.getInstance();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        plugin.getGames().forEach((name, game) -> {
            if(game.getPlayersInGame().contains(e.getEntity())) {
                Player p = e.getEntity();
                Player killer = p.getKiller();
                game.playerWin(killer);
                game.leavePlayer(p);
                e.getDrops().clear();

                game.getSpectators().forEach(player -> game.leavePlayer(player));
                game.setStarted(false);
                e.setDeathMessage(null);
            }
        });
    }

}
