package ro.smartnpc.npc.actions;

import ro.smartnpc.npc.EnvironmentNPC;

public interface Action {

    ActionType getActionType();

    void execute(EnvironmentNPC npc);
}
