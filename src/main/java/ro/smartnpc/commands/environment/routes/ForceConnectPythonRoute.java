package ro.smartnpc.commands.environment.routes;

import org.bukkit.command.CommandSender;
import ro.smartnpc.algorithms.python.PythonConnection;
import ro.smartnpc.commands.CommandRoute;
import ro.smartnpc.environment.Environment;

public class ForceConnectPythonRoute implements CommandRoute {

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        sender.sendMessage("§aTrying to create a connection...");

        if (PythonConnection.getInstance().getOutputStream() != null) {
            sender.sendMessage("§cConnection already established!");
            return false;
        }

        if(PythonConnection.getInstance().connect()){
            sender.sendMessage("§aConnection established!");
        } else {
            sender.sendMessage("§cConnection failed!");
        }

        return false;
    }

    @Override
    public boolean isConsoleAllowed() {
        return false;
    }

}
