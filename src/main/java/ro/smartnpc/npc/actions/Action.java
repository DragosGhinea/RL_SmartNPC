package ro.smartnpc.npc.actions;

import ro.smartnpc.npc.EnvironmentNPCDebug;

public interface Action {

    ActionType getActionType();

    void execute(EnvironmentNPCDebug npc);

    default void executeContinuous(EnvironmentNPCDebug npc, double... args) {
        execute(npc);
    }
}
