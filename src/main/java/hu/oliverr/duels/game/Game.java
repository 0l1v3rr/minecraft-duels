package hu.oliverr.duels.game;

import hu.oliverr.duels.Duels;
import hu.oliverr.duels.utility.Chat;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.Set;

public class Game {

    private final Duels plugin = Duels.getInstance();
    private final Chat chat = new Chat();

    private String displayName;
    private Location firstPlayerSpawn;
    private Location secondPlayerSpawn;
    private Location lobbySpawn;
    private String starterKit;
    private int maxTimeInMin;
    private boolean fallDamage;
    private boolean hunger;

    private boolean isStarted;
    private Set<Player> playersInGame;
    private Set<Player> spectators;

    private int lobbyCountdown = 10;

    public Game() {

    }

    public Game(String name) {
        if(plugin.getConfig().getString("arenas."+name.toLowerCase()) != null) {
            this.setDisplayName(plugin.getConfig().getString("arenas."+name.toLowerCase()+".displayName"));
            this.setStarterKit(plugin.getConfig().getString("arenas."+name.toLowerCase()+".starterKit"));
            this.setMaxTimeInMin(plugin.getConfig().getInt("arenas."+name.toLowerCase()+".arenaTime"));
            this.setFallDamage(plugin.getConfig().getBoolean("arenas."+name.toLowerCase()+".fallDamage"));
            this.setHunger(plugin.getConfig().getBoolean("arenas."+name.toLowerCase()+".hunger"));

            this.setFirstPlayerSpawn(plugin.getConfig().getLocation("arenas."+name.toLowerCase()+".firstPlayerSpawn"));
            this.setSecondPlayerSpawn(plugin.getConfig().getLocation("arenas."+name.toLowerCase()+".secondPlayerSpawn"));
            this.setLobbySpawn(plugin.getConfig().getLocation("arenas."+name.toLowerCase()+".lobbySpawn"));
        }
    }

    public void joinGame(Player player) {
        if(playersInGame.size() >= 2) {
            chat.sendMessage(player, plugin.getMessagesConfig().getString("arena-full"));
            return;
        }

        if(isStarted) {
            chat.sendMessage(player, plugin.getMessagesConfig().getString("arena-started"));
            return;
        }

        playersInGame.add(player);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setGameMode(GameMode.SURVIVAL);
        player.setDisplayName(null);
        player.getInventory().clear();

        if(playersInGame.size() == 1)
            if(firstPlayerSpawn != null)
                player.teleport(firstPlayerSpawn);
        else if(playersInGame.size() == 2)
            if(secondPlayerSpawn != null)
                player.teleport(secondPlayerSpawn);

        sendMessageToEveryInGamePlayer(Objects.requireNonNull(plugin.getMessagesConfig().getString("join-message")).replace("{PLAYER}", player.getName()).replace("{CURRENT}", playersInGame.size() + ""));

        if(playersInGame.size() == 2) {
            startCountdown();
        }
    }

    public void leavePlayer(Player player) {
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setGameMode(GameMode.SURVIVAL);
        player.setDisplayName(null);
        player.getInventory().clear();
        if(lobbySpawn != null) player.teleport(lobbySpawn);
        if(playersInGame.contains(player)) {
            playersInGame.remove(player);
            if(isStarted && playersInGame.size() == 1) {
                playersInGame.forEach(this::playerWin);
            }
        }
        if(spectators.contains(player)) spectators.remove(player);
    }

    public void switchToSpectator(Player player) {
        if(playersInGame.contains(player)) playersInGame.remove(player);
        spectators.add(player);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setGameMode(GameMode.SPECTATOR);
        player.setDisplayName(null);
        player.getInventory().clear();
    }

    public void startCountdown() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(lobbyCountdown > 0) {
                    if(playersInGame.size() == 2) {
                        lobbyCountdown--;
                        sendMessageToEveryInGamePlayer(Objects.requireNonNull(plugin.getMessagesConfig().getString("lobby-countdown")).replace("{SECONDS}", lobbyCountdown+""));
                        for(Player online : playersInGame) {
                            online.playSound(online.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, 2);
                        }
                    } else {
                        this.cancel();
                        lobbyCountdown = 10;
                    }
                } else {
                    gameStart();
                    sendMessageToEveryInGamePlayer(plugin.getMessagesConfig().getString("game-start"));
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    public void gameStart() {
        if(playersInGame.size() == 2) {
            isStarted = true;
            int n = 1;
            for(Player p : playersInGame) {
                if(n == 1 && firstPlayerSpawn != null) p.teleport(firstPlayerSpawn);
                if(n == 2 && secondPlayerSpawn != null) p.teleport(secondPlayerSpawn);
                n++;
            }
            n = 1;
        }
    }

    public void playerWin(Player player) {
        sendMessageToEveryInGamePlayer(Objects.requireNonNull(plugin.getMessagesConfig().getString("win-message")).replace("{PLAYER}", player.getName()));
    }

    public void playerDeath(Player player) {
        switchToSpectator(player);
    }

    public void sendMessageToEveryInGamePlayer(String msg) {
        for(Player p : playersInGame) {
            chat.sendMessage(p, msg);
        }

        for(Player p : spectators) {
            chat.sendMessage(p, msg);
        }
    }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public Location getFirstPlayerSpawn() { return firstPlayerSpawn; }
    public void setFirstPlayerSpawn(Location firstPlayerSpawn) { this.firstPlayerSpawn = firstPlayerSpawn; }

    public Location getSecondPlayerSpawn() { return secondPlayerSpawn; }
    public void setSecondPlayerSpawn(Location secondPlayerSpawn) { this.secondPlayerSpawn = secondPlayerSpawn; }

    public Location getLobbySpawn() { return lobbySpawn; }
    public void setLobbySpawn(Location lobbySpawn) { this.lobbySpawn = lobbySpawn; }

    public String getStarterKit() { return starterKit; }
    public void setStarterKit(String starterKit) { this.starterKit = starterKit; }

    public int getMaxTimeInMin() { return maxTimeInMin; }
    public void setMaxTimeInMin(int maxTimeInMin) { this.maxTimeInMin = maxTimeInMin; }

    public boolean isFallDamage() { return fallDamage; }
    public void setFallDamage(boolean fallDamage) { this.fallDamage = fallDamage; }

    public boolean isHunger() { return hunger; }
    public void setHunger(boolean hunger) { this.hunger = hunger; }

    public boolean isStarted() { return isStarted; }
    public void setStarted(boolean started) { isStarted = started; }

    public Set<Player> getPlayersInGame() { return playersInGame; }
    public void addToPlayersInGame(Player player) { playersInGame.add(player); }
    public void removeFromPlayersInGame(Player player) { if(playersInGame.contains(player)) playersInGame.remove(player); }

    public Set<Player> getSpectators() { return spectators; }
    public void addToSpectators(Player player) { spectators.add(player); }
    public void removeFromSpectators(Player player) { if(spectators.contains(player)) playersInGame.remove(player); }

}
