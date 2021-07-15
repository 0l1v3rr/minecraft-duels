package hu.oliverr.duels.kits;

import hu.oliverr.duels.Duels;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CreateKit {

    private final Duels plugin = Duels.getInstance();

    public void createKit(Inventory inventory, String kitName) {
        if(plugin.getConfig().getString("kits."+kitName) == null) {
            int count = 0;
            int slot = 0;
            for(ItemStack i : inventory.getContents()) {
                if(i != null) {
                    ItemMeta meta = i.getItemMeta();
                    String n = count + "";
                    String material = String.valueOf(i.getType());
                    plugin.getConfig().set("kits." + kitName + "." + n + ".material", material);
                    plugin.getConfig().set("kits." + kitName + "." + n + ".name", meta.getDisplayName());
                    plugin.getConfig().set("kits." + kitName + "." + n + ".amount", i.getAmount());
                    plugin.getConfig().set("kits." + kitName + "." + n + ".slot", slot);
                    count++;
                }
                slot++;
            }
            plugin.saveConfig();
            slot = 0;
            count = 0;
        }
    }

}
