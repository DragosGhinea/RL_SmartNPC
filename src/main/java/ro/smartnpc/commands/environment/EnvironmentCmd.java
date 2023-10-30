package ro.smartnpc.commands.environment;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ro.smartnpc.commands.CommandRoute;
import ro.smartnpc.commands.environment.routes.LoadRoute;
import ro.smartnpc.commands.environment.routes.TpRoute;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnvironmentCmd implements TabExecutor {

    Map<String, CommandRoute> routes = new HashMap<>() {{
        put("load", new LoadRoute());
        put("tp", new TpRoute());
    }};

    private EnvironmentCmd() {}

    private static EnvironmentCmd instance;

    public static synchronized EnvironmentCmd getInstance() {
        if (instance == null) {
            instance = new EnvironmentCmd();
        }

        return instance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0) {
            commandSender.sendMessage("§cNot enough arguments!");
            return false;
        }

        CommandRoute route = routes.getOrDefault(args[0], null);
        if (route == null) {
            commandSender.sendMessage("§cInvalid argument!");
            return false;
        }

        return route.onCommand(commandSender, args);
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> toReturn = null;
        String arg = args[0].toLowerCase();

        if (args.length == 1)
            return routes.keySet().stream().filter(s1 -> s1.startsWith(arg)).toList();


        final CommandRoute route = routes.getOrDefault(arg, null);

        if (route != null) {
            toReturn = route.onTabComplete(commandSender, args);
        }

        return toReturn == null ? Collections.emptyList() : toReturn;
    }
}
