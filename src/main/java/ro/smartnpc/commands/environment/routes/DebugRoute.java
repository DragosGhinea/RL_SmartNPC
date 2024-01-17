package ro.smartnpc.commands.environment.routes;

import org.bukkit.command.CommandSender;
import ro.smartnpc.algorithms.python.DeepQLearningProxy;
import ro.smartnpc.commands.CommandRoute;

public class DebugRoute implements CommandRoute {
    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        try {
            DeepQLearningProxy.init(10, 20);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean isConsoleAllowed() {
        return false;
    }
}
