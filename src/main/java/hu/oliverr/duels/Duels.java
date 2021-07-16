package hu.oliverr.duels;

import hu.oliverr.duels.commands.DuelsCommand;
import hu.oliverr.duels.commands.DuelsTabCompleter;
import hu.oliverr.duels.game.Game;
import hu.oliverr.duels.invenotry.InventoryEvent;
import hu.oliverr.duels.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public final class Duels extends JavaPlugin {

    private static Duels instance;
    public static Duels getInstance() { return instance; }
    public static void setInstance(Duels instance) { Duels.instance = instance; }

    private Map<String, Game> games;
    public void addToGame(String name, Game game) { if(!games.containsKey(name)) games.put(name, game); }
    public void removeFromGame(String name) { if(games.containsKey(name)) games.remove(name); }
    public Game getGame(String name) { return games.get(name); }
    public Map<String, Game> getGames() { return games; }

    private final File messagesFile = new File(getDataFolder(), "messages.yml");
    public File getMessagesFile() { return messagesFile; }
    private final FileConfiguration messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    public FileConfiguration getMessagesConfig() { return messagesConfig; }

    @Override
    public void onEnable() {
        setInstance(this);
        loadConfig();
        loadMessages();

        this.getCommand("duels").setExecutor(new DuelsCommand());
        this.getCommand("duels").setTabCompleter(new DuelsTabCompleter());

        Bukkit.getPluginManager().registerEvents(new InventoryEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnJoin(), this);
        Bukkit.getPluginManager().registerEvents(new OnDamage(), this);
        Bukkit.getPluginManager().registerEvents(new SignChange(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteract(), this);
        Bukkit.getPluginManager().registerEvents(new OnDrop(), this);
        Bukkit.getPluginManager().registerEvents(new HungerChange(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(), this);

        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GREEN+"SkyPvP"+ChatColor.DARK_GRAY+"] "+ChatColor.AQUA+"+-------------------------------------+");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GREEN+"SkyPvP"+ChatColor.DARK_GRAY+"] "+ChatColor.DARK_AQUA+""+ChatColor.BOLD+"Duels");
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GREEN+"SkyPvP"+ChatColor.DARK_GRAY+"] "+ChatColor.YELLOW+"Version: "+ChatColor.WHITE+"1.0");
        games = setupGames();
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GREEN+"SkyPvP"+ChatColor.DARK_GRAY+"] "+ChatColor.AQUA+"+-------------------------------------+");
    }

    @Override
    public void onDisable() {
        setInstance(null);
        games = null;
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void loadMessages() {
        messagesConfig.options().copyDefaults(true);
        if(!messagesFile.exists()) {
            saveResource("messages.yml", false);
        }
        else {
            Reader stream = null;
            try {
                stream = new InputStreamReader(this.getResource("messages.yml"), "UTF8");
                if (stream != null) {
                    YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(stream);
                    messagesConfig.setDefaults(defConfig);
                }
            } catch(UnsupportedEncodingException e) {
                getLogger().log(Level.SEVERE, "Could not load messages.yml");
            }
        }
    }

    private HashMap<String, Game> setupGames() {
        HashMap<String, Game> games = new HashMap<>();
        try {
            if(this.getConfig().getString("arenas") != null)
                for(String key : this.getConfig().getConfigurationSection("arenas").getKeys(false))
                    games.put(key.toLowerCase(), new Game(key.toLowerCase()));
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GREEN+"SkyPvP"+ChatColor.DARK_GRAY+"] "+ChatColor.GREEN+"Arenas loaded!");
        } catch(Exception e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GRAY+"["+ChatColor.GREEN+"SkyPvP"+ChatColor.DARK_GRAY+"] "+ChatColor.RED+"Failed to load the arenas.");
        }
        return games;
    }

}
