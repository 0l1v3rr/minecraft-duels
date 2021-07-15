package hu.oliverr.duels.kits;

import hu.oliverr.duels.Duels;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AddKit {

    private final Duels plugin = Duels.getInstance();

    public void addKit(Player player, String kitName) {
        if(plugin.getConfig().getString("kits."+kitName) != null) {
            player.getInventory().clear();
            try {
                for(String key : plugin.getConfig().getConfigurationSection("kits."+kitName).getKeys(false)) {
                    Material material = Material.matchMaterial(plugin.getConfig().getString("kits."+kitName+"."+key+".material"));
                    int count = plugin.getConfig().getInt("kits."+kitName+"."+key+".amount");
                    int slot = plugin.getConfig().getInt("kits."+kitName+"."+key+".slot");

                    ItemStack item = new ItemStack(material, count);
                    ItemMeta meta = item.getItemMeta();
                    if(plugin.getConfig().getString("kits."+kitName+"."+key+".name") != null)
                        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("kits."+kitName+"."+key+".name")));
                    item.setItemMeta(meta);

                    player.getInventory().setItem(slot, item);
                }
            } catch(Exception ignored) { }
        }
    }

}
