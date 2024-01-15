package ro.smartnpc.commands.environment.routes;

import org.bukkit.command.CommandSender;
import ro.smartnpc.commands.CommandRoute;
import ro.smartnpc.environment.Environment;

public class UnloadRoute implements CommandRoute {

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (Environment.getRunningInstance() == null) {
            sender.sendMessage("§cEnvironment not running!");
            return false;
        }

        sender.sendMessage("§aUnloading environment...");

        final Environment environment = Environment.getRunningInstance();

        environment.unload();

        sender.sendMessage("§aEnvironment unloaded!");

        return false;
    }

    @Override
    public boolean isConsoleAllowed() {
        return false;
    }

}