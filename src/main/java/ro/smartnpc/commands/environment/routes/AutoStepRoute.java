package ro.smartnpc.commands.environment.routes;

import org.bukkit.command.CommandSender;
import ro.smartnpc.commands.CommandRoute;
import ro.smartnpc.environment.Environment;

public class AutoStepRoute implements CommandRoute {
    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§cNot enough arguments! Specify number of steps");
            return false;
        }

        int steps;
        try{
            steps = Integer.parseInt(args[1]);
        }catch (NumberFormatException e) {
            sender.sendMessage("§cInvalid argument!");
            return false;
        }

        sender.sendMessage("§aRunning " + steps + " steps...");

        final Environment environment = Environment.getRunningInstance();

        environment.autoStep(steps);

        return false;
    }

    @Override
    public boolean isConsoleAllowed() {
        return false;
    }
}
