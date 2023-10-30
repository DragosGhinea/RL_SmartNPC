package ro.smartnpc.commands.environment.routes;

import org.bukkit.command.CommandSender;
import ro.smartnpc.SmartNPC;
import ro.smartnpc.commands.CommandRoute;

import java.util.List;

public class LoadRoute implements CommandRoute {
    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§cNot enough arguments!");
            return false;
        }

        SmartNPC.getInstance().getWorldUtils().loadWorld(args[1]);
        sender.sendMessage("§aWorld loaded!");
        return false;
    }

    @Override
    public boolean isConsoleAllowed() {
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
