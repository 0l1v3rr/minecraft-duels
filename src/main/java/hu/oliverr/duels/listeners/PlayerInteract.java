package hu.oliverr.duels.listeners;

import hu.oliverr.duels.Duels;
import hu.oliverr.duels.game.Game;
import hu.oliverr.duels.utility.Chat;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {

    private final Duels plugin = Duels.getInstance();
    private final Chat chat = new Chat();

    @EventHandler
    public void playerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(e.getClickedBlock().getType().equals(Material.OAK_SIGN) || e.getClickedBlock().getType().equals(Material.OAK_WALL_SIGN)) {
                Sign sign = (Sign) e.getClickedBlock().getState();
                if(sign.getLine(0).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&8[&bDuels&8]"))) {
                    String firstLine = sign.getLine(1).replace(ChatColor.translateAlternateColorCodes('&', "&e"), "");
                    if(plugin.getGame(firstLine) == null) {
                        sign.setLine(0, ChatColor.DARK_RED+"[Duels]");
                        sign.setLine(1, ChatColor.DARK_RED+firstLine);
                        sign.setLine(2, " ");
                        chat.sendMessage(p, plugin.getMessagesConfig().getString("arena-doesnt-exist"));
                    } else {
                        Game game = plugin.getGame(firstLine);
                        game.joinGame(p);
                        String size = game.getPlayersInGame().size() + "";
                        sign.setLine(2, ChatColor.translateAlternateColorCodes('&', "&a"+size+"&7/&a2"));
                    }
                }
            }
        }
    }

}
