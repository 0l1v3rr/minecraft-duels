package hu.oliverr.duels.listeners;

import hu.oliverr.duels.Duels;
import hu.oliverr.duels.utility.Chat;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChange implements Listener {

    private final Duels plugin = Duels.getInstance();
    private final Chat chat = new Chat();

    @EventHandler
    public void onBlockPlace(SignChangeEvent e) {
        Player p = e.getPlayer();

        if(p.hasPermission("duels.admin")) {
            if(e.getLine(0).equalsIgnoreCase("[duels]")) {
                String firstLine = e.getLine(1).toLowerCase();
                if(plugin.getGame(firstLine) == null) {
                    e.setLine(0, ChatColor.DARK_RED+"[Duels]");
                    e.setLine(1, ChatColor.DARK_RED+firstLine);
                    e.setLine(2, " ");
                    chat.sendMessage(p, plugin.getMessagesConfig().getString("arena-doesnt-exist"));
                } else {
                    //plugin.getConfig().set("signs.", e.getBlock().getState().getLocation());
                    e.setLine(0, ChatColor.translateAlternateColorCodes('&', "&8[&bDuels&8]"));
                    e.setLine(1, ChatColor.translateAlternateColorCodes('&', "&e" + firstLine));
                    e.setLine(2, ChatColor.translateAlternateColorCodes('&', "&a0&7/&a2"));
                }
            }
        }
    }

}
