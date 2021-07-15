package hu.oliverr.duels.commands;

import hu.oliverr.duels.Duels;
import hu.oliverr.duels.game.Game;
import hu.oliverr.duels.invenotry.EditInventory;
import hu.oliverr.duels.utility.Chat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DuelsCommand implements CommandExecutor {

    private final Duels plugin = Duels.getInstance();
    private final Chat chat = new Chat();
    private final DuelsTabCompleter dtc = new DuelsTabCompleter();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("duels")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessagesConfig().getString("only-players")));
                return false;
            }

            Player player = (Player) sender;

            if(args.length == 0) {
                invalidArgs(player);
                return false;
            }

            if(args[0].equalsIgnoreCase("create")) {
                if(!player.hasPermission("duels.admin")) {
                    chat.noPerms(player);
                    return false;
                }
                if(args.length == 1) {
                    invalidArgs(player);
                    return false;
                }

                if(plugin.getConfig().getString("arenas."+args[1].toLowerCase()) != null) {
                    chat.sendMessage(player, plugin.getMessagesConfig().getString("arena-exist"));
                    return false;
                }

                plugin.getConfig().set("arenas."+args[1].toLowerCase()+".displayName", args[1]);
                plugin.getConfig().set("arenas."+args[1].toLowerCase()+".starterKit", "sample");
                plugin.getConfig().set("arenas."+args[1].toLowerCase()+".arenaTime", 10);
                plugin.getConfig().set("arenas."+args[1].toLowerCase()+".fallDamage", false);
                plugin.getConfig().set("arenas."+args[1].toLowerCase()+".hunger", true);
                plugin.saveConfig();

                Game game = new Game();
                game.setDisplayName(args[1]);
                game.setStarterKit("sample");
                game.setMaxTimeInMin(10);
                game.setFallDamage(false);
                game.setHunger(true);

                plugin.addToGame(args[1].toLowerCase(), game);
                dtc.addToArenas(args[1].toLowerCase());
                chat.sendMessage(player, plugin.getMessagesConfig().getString("arena-created"));
                return false;
            }

            if(args[0].equalsIgnoreCase("remove")) {
                if(!player.hasPermission("duels.admin")) {
                    chat.noPerms(player);
                    return false;
                }
                if(args.length == 1) {
                    invalidArgs(player);
                    return false;
                }

                if(plugin.getConfig().getString("arenas."+args[1].toLowerCase()) == null) {
                    chat.sendMessage(player, plugin.getMessagesConfig().getString("arena-doesnt-exist"));
                    return false;
                }

                plugin.getConfig().set("arenas."+args[1].toLowerCase(), null);
                plugin.saveConfig();
                dtc.removeFromArenas(args[1].toLowerCase());
                chat.sendMessage(player, plugin.getMessagesConfig().getString("arena-removed"));
                plugin.removeFromGame(args[1].toLowerCase());
                return false;
            }

            if(args[0].equalsIgnoreCase("edit")) {
                if(!player.hasPermission("duels.admin")) {
                    chat.noPerms(player);
                    return false;
                }
                if(args.length == 1) {
                    invalidArgs(player);
                    return false;
                }

                if(plugin.getConfig().getString("arenas."+args[1].toLowerCase()) == null) {
                    chat.sendMessage(player, plugin.getMessagesConfig().getString("arena-doesnt-exist"));
                    return false;
                }

                EditInventory editInvenotry = new EditInventory();
                editInvenotry.newInvenotry(args[1].toLowerCase(), player, plugin.getConfig().getBoolean("arenas."+args[1].toLowerCase()+".hunger"), plugin.getConfig().getBoolean("arenas."+args[1].toLowerCase()+".fallDamage"));
                return false;
            }

            invalidArgs(player);
            return false;
        }

        return false;
    }

    private void invalidArgs(Player p) {
        chat.sendMessage(p, plugin.getMessagesConfig().getString("no-args"));
    }
}
