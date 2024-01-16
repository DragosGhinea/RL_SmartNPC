package ro.smartnpc.commands.environment.routes;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ro.smartnpc.commands.CommandRoute;

public class DebugRoute implements CommandRoute {
    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        Location location = p.getLocation();

        float v1 = Float.parseFloat(args[1]);
        float v2 = Float.parseFloat(args[2]);
        float v3 = Float.parseFloat(args[3]);
        float speed = Float.parseFloat(args[4]);

        location.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, location, 0, v1, v2, v3, speed);
        return false;
    }

    @Override
    public boolean isConsoleAllowed() {
        return false;
    }
}
