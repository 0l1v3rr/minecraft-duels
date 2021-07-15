package hu.oliverr.duels.utility;

import hu.oliverr.duels.Duels;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class OldPvP {

    private final Duels plugin = Duels.getInstance();

    public void attackSpeed(Player p) {
        double attackSpeed;
        if(plugin.getConfig().getBoolean("old-pvp")) attackSpeed = 16;
        else attackSpeed = 4;

        AttributeInstance attribute = p.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        if(attribute == null) return;

        double value = attribute.getBaseValue();

        if(value != attackSpeed){
            attribute.setBaseValue(attackSpeed);
            p.saveData();
        }
    }

}
