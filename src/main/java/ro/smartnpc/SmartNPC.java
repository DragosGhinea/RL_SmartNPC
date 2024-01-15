package ro.smartnpc;

import com.infernalsuite.aswm.api.SlimePlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import ro.smartnpc.commands.CommandsLoader;
import ro.smartnpc.environment.Environment;
import ro.smartnpc.listeners.ListenerManager;
import ro.smartnpc.world.WorldUtils;
import ro.smartnpc.world.classic.WorldUtilsClassic;
import ro.smartnpc.world.slime.WorldUtilsSlime;

public class SmartNPC extends JavaPlugin {

    private static SmartNPC instance;

    public static SmartNPC getInstance() {
        return instance;
    }

    private WorldUtils worldUtils;

    public WorldUtils getWorldUtils() {
        return worldUtils;
    }

    @Override
    public void onEnable() {
        instance = this;

        worldUtils = new WorldUtilsSlime();
        //worldUtils = new WorldUtilsClassic();

        CommandsLoader.init();
        ListenerManager.registerListeners();
        getLogger().info("SmartNPC has been enabled!");
    }

    @Override
    public void onDisable() {
        if (Environment.getRunningInstance() != null) {
            getLogger().info("Unloading environment...");
            Environment.getRunningInstance().unload();
            getLogger().info("Environment unloaded!");
        }

        HandlerList.unregisterAll(this);
        getLogger().info("SmartNPC has been disabled!");
    }

}
