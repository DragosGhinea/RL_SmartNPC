package ro.smartnpc.commands.environment.routes;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ro.smartnpc.Utils;
import ro.smartnpc.commands.CommandRoute;

public class DebugRoute implements CommandRoute {
    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        Location source = p.getLocation();
        Location target = p.getLocation().add(1, 0, 0);
        sender.sendMessage("§aTest " + Utils.getFacingResult(source, target));

        return false;
    }

    @Override
    public boolean isConsoleAllowed() {
        return false;
    }
}
