package hu.oliverr.duels.utility;

import hu.oliverr.duels.Duels;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Chat {

    private final Duels plugin = Duels.getInstance();
    private boolean usePrefix;
    private String prefix;

    public void sendMessage(Player p, String msg) {
        usePrefix = plugin.getMessagesConfig().getBoolean("use-prefix");
        if(usePrefix) {
            prefix = plugin.getMessagesConfig().getString("prefix");
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + msg));
        } else {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
        }
    }

    public void noPerms(Player p) {
        usePrefix = plugin.getMessagesConfig().getBoolean("use-prefix");
        if(usePrefix) {
            prefix = plugin.getMessagesConfig().getString("prefix");
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + plugin.getMessagesConfig().getString("no-permission")));
        } else {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessagesConfig().getString("no-permission")));
        }
    }

}
