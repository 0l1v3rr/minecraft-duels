package hu.oliverr.duels.invenotry;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class EditInventory {

    public void newInvenotry(String arenaName, Player player, boolean isHunger, boolean isFallDamage) {
        Inventory inventory = player.getServer().createInventory(null, 27, "Edit arena");

        ItemStack glassPane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta gpmeta = glassPane.getItemMeta();
        gpmeta.setDisplayName(ChatColor.DARK_GRAY + "*");
        glassPane.setItemMeta(gpmeta);

        ItemStack powder = new ItemStack(Material.GRAY_DYE);
        ItemMeta pmeta = powder.getItemMeta();
        pmeta.setDisplayName(ChatColor.DARK_GRAY + "*");
        powder.setItemMeta(pmeta);

        ItemStack lobbySpawn = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta lobbyMeta = lobbySpawn.getItemMeta();
        lobbyMeta.setDisplayName(ChatColor.DARK_AQUA + "Lobby Spawn");
        ArrayList<String> lobbyLore = new ArrayList<>();
        lobbyLore.add(ChatColor.YELLOW + "Right Click");
        lobbyLore.add(ChatColor.translateAlternateColorCodes('&', "&7Arena: " + arenaName));
        lobbyMeta.setLore(lobbyLore);
        lobbySpawn.setItemMeta(lobbyMeta);

        ItemStack firstSpawn = new ItemStack(Material.COMPASS);
        ItemMeta firstSpawnItemMeta = firstSpawn.getItemMeta();
        firstSpawnItemMeta.setDisplayName(ChatColor.GOLD + "First Player Spawn");
        ArrayList<String> firstSpawnLore = new ArrayList<>();
        firstSpawnLore.add(ChatColor.YELLOW + "Right Click");
        firstSpawnLore.add(ChatColor.translateAlternateColorCodes('&', "&7Arena: " + arenaName));
        firstSpawnItemMeta.setLore(firstSpawnLore);
        firstSpawn.setItemMeta(firstSpawnItemMeta);

        ItemStack secondSpawn = new ItemStack(Material.CLOCK);
        ItemMeta secondSpawnItemMeta = secondSpawn.getItemMeta();
        secondSpawnItemMeta.setDisplayName(ChatColor.GOLD + "Second Player Spawn");
        ArrayList<String> secondSpawnLore = new ArrayList<>();
        secondSpawnLore.add(ChatColor.YELLOW + "Right Click");
        secondSpawnLore.add(ChatColor.translateAlternateColorCodes('&', "&7Arena: " + arenaName));
        secondSpawnItemMeta.setLore(secondSpawnLore);
        secondSpawn.setItemMeta(secondSpawnItemMeta);

        ItemStack hunger = new ItemStack(Material.ROTTEN_FLESH);
        ItemMeta hungerItemMeta = hunger.getItemMeta();
        hungerItemMeta.setDisplayName(ChatColor.RED + "Hunger - " + (isHunger ? "True" : "False"));
        ArrayList<String> hungerLore = new ArrayList<>();
        hungerLore.add(ChatColor.YELLOW + "Right Click");
        hungerLore.add(ChatColor.translateAlternateColorCodes('&', "&7Arena: " + arenaName));
        hungerItemMeta.setLore(hungerLore);
        hunger.setItemMeta(hungerItemMeta);

        ItemStack fallDamage = new ItemStack(Material.ARROW);
        ItemMeta fdItemMeta = fallDamage.getItemMeta();
        fdItemMeta.setDisplayName(ChatColor.RED + "Fall Damage - " + (isFallDamage ? "True" : "False"));
        ArrayList<String> fdlore = new ArrayList<>();
        fdlore.add(ChatColor.YELLOW + "Right Click");
        fdlore.add(ChatColor.translateAlternateColorCodes('&', "&7Arena: " + arenaName));
        fdItemMeta.setLore(fdlore);
        fallDamage.setItemMeta(fdItemMeta);

        inventory.setItem(0, powder);
        inventory.setItem(1, glassPane);
        inventory.setItem(2, glassPane);
        inventory.setItem(3, glassPane);
        inventory.setItem(4, glassPane);
        inventory.setItem(5, glassPane);
        inventory.setItem(6, glassPane);
        inventory.setItem(7, glassPane);
        inventory.setItem(8, powder);

        inventory.setItem(9, glassPane);
        inventory.setItem(10, firstSpawn);
        inventory.setItem(11, secondSpawn);
        inventory.setItem(12, glassPane);
        inventory.setItem(13, lobbySpawn);
        inventory.setItem(14, glassPane);
        inventory.setItem(15, hunger);
        inventory.setItem(16, fallDamage);
        inventory.setItem(17, glassPane);

        inventory.setItem(18, powder);
        inventory.setItem(19, glassPane);
        inventory.setItem(20, glassPane);
        inventory.setItem(21, glassPane);
        inventory.setItem(22, glassPane);
        inventory.setItem(23, glassPane);
        inventory.setItem(24, glassPane);
        inventory.setItem(25, glassPane);
        inventory.setItem(26, powder);

        player.openInventory(inventory);
    }

}
