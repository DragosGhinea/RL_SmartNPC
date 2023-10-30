package ro.smartnpc.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import ro.smartnpc.commands.environment.EnvironmentCmd;

public class CommandsLoader {

    public static void init(){
        PluginCommand smartNpcCmd = Bukkit.getPluginCommand("smartnpc_environment");

        if (smartNpcCmd != null)
            smartNpcCmd.setExecutor(EnvironmentCmd.getInstance());
    }
}
