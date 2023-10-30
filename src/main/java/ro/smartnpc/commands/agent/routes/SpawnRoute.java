package ro.smartnpc.commands.agent.routes;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ro.smartnpc.SmartNPC;
import ro.smartnpc.commands.CommandRoute;
import ro.smartnpc.environment.Environment;
import ro.smartnpc.environment.EnvironmentWorld;
import ro.smartnpc.map.Schematic;

public class SpawnRoute implements CommandRoute {
    @Override
    public boolean onCommand(CommandSender sender, String[] args)  {
        Environment environment = Environment.getRunningInstance();
        if (environment == null)
            return false;

        environment.getEnvironmentNPC().getOrSpawnNPC();
        sender.sendMessage("§aNPC spawned!");

        return false;
    }

    @Override
    public boolean isConsoleAllowed() {
        return true;
    }
}
