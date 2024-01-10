package ro.smartnpc.commands.environment.routes;

import org.bukkit.command.CommandSender;
import ro.smartnpc.commands.CommandRoute;
import ro.smartnpc.environment.Environment;

public class TrainRoute implements CommandRoute {
    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("§cNot enough arguments! Specify number of episodes and steps");
            return false;
        }

        int episodes;
        try{
            episodes = Integer.parseInt(args[1]);
        }catch (NumberFormatException e) {
            sender.sendMessage("§cInvalid argument!");
            return false;
        }

        int steps;
        try{
            steps = Integer.parseInt(args[2]);
        }catch (NumberFormatException e) {
            sender.sendMessage("§cInvalid argument!");
            return false;
        }

        sender.sendMessage("§aRunning training with "+ episodes + " episodes and " + steps + " steps...");

        final Environment environment = Environment.getRunningInstance();

        environment.train(episodes, steps);

        return false;
    }

    @Override
    public boolean isConsoleAllowed() {
        return false;
    }
}
