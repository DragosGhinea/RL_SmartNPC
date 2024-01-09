package ro.smartnpc.commands.debug.agent.routes;

import org.bukkit.command.CommandSender;
import ro.smartnpc.commands.CommandRoute;
import ro.smartnpc.environment.EnvironmentForDebug;

public class SpawnRoute implements CommandRoute {
    @Override
    public boolean onCommand(CommandSender sender, String[] args)  {
        EnvironmentForDebug environmentForDebug = EnvironmentForDebug.getRunningInstance();
        if (environmentForDebug == null)
            return false;

        environmentForDebug.getEnvironmentNPC().getOrSpawnNPC();
        sender.sendMessage("§aNPC spawned!");

        return false;
    }

    @Override
    public boolean isConsoleAllowed() {
        return true;
    }
}
