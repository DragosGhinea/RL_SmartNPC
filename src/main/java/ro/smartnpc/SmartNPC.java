package ro.smartnpc;

import com.infernalsuite.aswm.api.SlimePlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ro.smartnpc.commands.CommandsLoader;
import ro.smartnpc.world.WorldUtils;
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

        CommandsLoader.init();
        getLogger().info("SmartNPC has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("SmartNPC has been disabled!");
    }

}
