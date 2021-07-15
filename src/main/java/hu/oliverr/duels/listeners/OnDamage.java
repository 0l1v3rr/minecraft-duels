package hu.oliverr.duels.listeners;

import hu.oliverr.duels.Duels;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class OnDamage implements Listener {

    private final Duels plugin = Duels.getInstance();

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if(!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();

        if(plugin.getConfig().getBoolean("blood-effect")) {
            Objects.requireNonNull(p.getLocation().getWorld()).spawnParticle(Particle.ITEM_CRACK, p.getLocation().add(0, 0.5, 0), 15, 0.1, 0.1, 0.1, 0.1, new ItemStack(Material.REDSTONE_BLOCK));
        }
    }

}
