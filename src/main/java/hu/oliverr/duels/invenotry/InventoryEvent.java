package hu.oliverr.duels.invenotry;

import hu.oliverr.duels.Duels;
import hu.oliverr.duels.utility.Chat;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

public class InventoryEvent implements Listener {

    private final Duels plugin = Duels.getInstance();
    private final Chat chat = new Chat();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Inventory opened = e.getInventory();

        if(opened == null) return;

        if(e.getView().getTitle().equalsIgnoreCase("Edit arena")) {
            e.setCancelled(true);

            if(e.getCurrentItem().getType() == Material.DIAMOND_SWORD) {
                String arena = Objects.requireNonNull(e.getCurrentItem().getItemMeta()).getLore().get(1);
                arena = arena.replace(ChatColor.translateAlternateColorCodes('&', "&7Arena: "), "").toLowerCase();
                plugin.getConfig().set("arenas."+arena+".lobbySpawn", player.getLocation());
                plugin.saveConfig();
                plugin.getGame(arena).setLobbySpawn(player.getLocation());
                chat.sendMessage(player, plugin.getMessagesConfig().getString("spawnpoint-set"));
                return;
            }

            if(e.getCurrentItem().getType() == Material.COMPASS) {
                String arena = Objects.requireNonNull(e.getCurrentItem().getItemMeta()).getLore().get(1);
                arena = arena.replace(ChatColor.translateAlternateColorCodes('&', "&7Arena: "), "").toLowerCase();
                plugin.getConfig().set("arenas."+arena+".firstPlayerSpawn", player.getLocation());
                plugin.saveConfig();
                plugin.getGame(arena).setFirstPlayerSpawn(player.getLocation());
                chat.sendMessage(player, plugin.getMessagesConfig().getString("spawnpoint-set"));
                return;
            }

            if(e.getCurrentItem().getType() == Material.CLOCK) {
                String arena = Objects.requireNonNull(e.getCurrentItem().getItemMeta()).getLore().get(1);
                arena = arena.replace(ChatColor.translateAlternateColorCodes('&', "&7Arena: "), "").toLowerCase();
                plugin.getConfig().set("arenas."+arena+".secondPlayerSpawn", player.getLocation());
                plugin.saveConfig();
                plugin.getGame(arena).setSecondPlayerSpawn(player.getLocation());
                chat.sendMessage(player, plugin.getMessagesConfig().getString("spawnpoint-set"));
                return;
            }

            if(e.getCurrentItem().getType() == Material.ROTTEN_FLESH) {
                String arena = Objects.requireNonNull(e.getCurrentItem().getItemMeta()).getLore().get(1);
                arena = arena.replace(ChatColor.translateAlternateColorCodes('&', "&7Arena: "), "").toLowerCase();
                boolean status = e.getCurrentItem().getItemMeta().getDisplayName().replace("Hunger - ", "").equalsIgnoreCase("true");
                if(status) plugin.getConfig().set("arenas."+arena+".hunger", false);
                else plugin.getConfig().set("arenas."+arena+".hunger", true);
                plugin.saveConfig();
                plugin.getGame(arena).setHunger(!status);
                chat.sendMessage(player, plugin.getMessagesConfig().getString("updated-changes"));
                e.getWhoClicked().closeInventory();
                return;
            }

            if(e.getCurrentItem().getType() == Material.ARROW) {
                String arena = Objects.requireNonNull(e.getCurrentItem().getItemMeta()).getLore().get(1);
                arena = arena.replace(ChatColor.translateAlternateColorCodes('&', "&7Arena: "), "").toLowerCase();
                boolean status = e.getCurrentItem().getItemMeta().getDisplayName().replace("Fall Damage - ", "").equalsIgnoreCase("true");
                if(status)  plugin.getConfig().set("arenas."+arena+".fallDamage", false);
                else plugin.getConfig().set("arenas."+arena+".fallDamage", true);
                plugin.saveConfig();
                plugin.getGame(arena).setFallDamage(!status);
                chat.sendMessage(player, plugin.getMessagesConfig().getString("updated-changes"));
                e.getWhoClicked().closeInventory();
                return;
            }

        }
    }

}
