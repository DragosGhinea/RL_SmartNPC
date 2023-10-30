package ro.smartnpc.commands.agent.routes;

import org.bukkit.command.CommandSender;
import ro.smartnpc.commands.CommandRoute;
import ro.smartnpc.environment.Environment;
import ro.smartnpc.npc.actions.Action;
import ro.smartnpc.npc.actions.ActionType;

import java.util.List;

public class ActionRoute implements CommandRoute {
    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§cNot enough arguments!");
            return false;
        }

        Environment environment = Environment.getRunningInstance();
        if (environment == null)
            return false;

        Action action;
        try{
            ActionType actionType = ActionType.valueOf(args[1]);
            action = Environment.actionList.stream()
                    .filter(action1 -> action1.getActionType().equals(actionType))
                    .findAny().orElse(null);
        } catch (IllegalArgumentException e) {
            sender.sendMessage("§cInvalid action!");
            return false;
        }

        if (action == null){
            sender.sendMessage("§cInvalid action!");
            return false;
        }

        environment.executeAction(action);
        sender.sendMessage("§aAction executed!");

        return false;
    }

    @Override
    public boolean isConsoleAllowed() {
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return Environment.actionList.stream().map(action -> action.getActionType().name()).toList();
    }
}
