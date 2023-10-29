package ro.smartnpc;

import org.bukkit.plugin.java.JavaPlugin;

public class SmartNPC extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("SmartNPC has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("SmartNPC has been disabled!");
    }

}
