package ro.smartnpc.commands.debug.environment.routes;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ro.smartnpc.SmartNPC;
import ro.smartnpc.commands.CommandRoute;
import ro.smartnpc.environment.EnvironmentForDebug;
import ro.smartnpc.environment.EnvironmentWorld;
import ro.smartnpc.map.Schematic;

public class InitRoute implements CommandRoute {
    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        sender.sendMessage("§aLoading world settings...");
        EnvironmentWorld environmentWorld = new EnvironmentWorld("movement", Schematic.MOVEMENT_ARENA1);
        final EnvironmentForDebug environmentForDebug = new EnvironmentForDebug(environmentWorld);

        sender.sendMessage("§aLoading world...");
        environmentForDebug.init().whenComplete((world, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
            }

            sender.sendMessage("§aTeleporting to world...");

            Bukkit.getScheduler().runTask(SmartNPC.getInstance(), () -> {
                player.teleport(world.getSpawnLocation());
            });

            sender.sendMessage("§aWorld loaded!");
        });

        return false;
    }

    @Override
    public boolean isConsoleAllowed() {
        return false;
    }
}
