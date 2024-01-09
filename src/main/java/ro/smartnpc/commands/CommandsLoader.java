package ro.smartnpc.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import ro.smartnpc.commands.debug.agent.AgentDebugCmd;
import ro.smartnpc.commands.debug.environment.EnvironmentDebugCmd;
import ro.smartnpc.commands.environment.EnvironmentCmd;
import ro.smartnpc.commands.world.WorldCmd;

public class CommandsLoader {

    private static void associate(String command, CommandExecutor executor){
        PluginCommand cmd = Bukkit.getPluginCommand(command);

        if (cmd != null)
            cmd.setExecutor(executor);
    }

    public static void init(){
        associate("smartnpc_world", WorldCmd.getInstance());
        associate("smartnpc_environment", EnvironmentCmd.getInstance());

        associate("smartnpc_environment_debug", EnvironmentDebugCmd.getInstance());
        associate("smartnpc_agent_debug", AgentDebugCmd.getInstance());
    }
}
