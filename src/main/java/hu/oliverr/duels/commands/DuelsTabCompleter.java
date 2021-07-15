package hu.oliverr.duels.commands;

import hu.oliverr.duels.Duels;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DuelsTabCompleter implements TabCompleter {

    private final Duels plugin = Duels.getInstance();

    private List<String> arguments = new ArrayList<>();
    private Set<String> arenas = new HashSet<>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if(arguments.isEmpty()) {
            arguments.add("create");
            arguments.add("remove");
            arguments.add("edit");
            arguments.add("reload");
            arguments.add("createkit");
            arguments.add("deletekit");
        }

        if(plugin.getConfig().getString("arenas") != null) {
            for(String key : plugin.getConfig().getConfigurationSection("arenas").getKeys(false)) {
                arenas.add(key);
            }
        }

        List<String> result = new ArrayList<String >();
        if(args.length == 1) {
            for(String a : arguments) {
                if(a.toLowerCase().startsWith(args[0].toLowerCase())) {
                    result.add(a);
                }
            }
            return result;
        }

        List<String> result2 = new ArrayList<String>();
        if(args.length == 2 && (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("edit"))) {
            for(String a : arenas) {
                if(a.toLowerCase().startsWith(args[1].toLowerCase())) {
                    result2.add(a);
                }
            }
            return result2;
        }

        return null;
    }

    public void addToArenas(String name) {
        arenas.add(name);
    }
    public void removeFromArenas(String name) {
        if(arenas.contains(name)) {
            arenas.remove(name);
        }
    }

}
