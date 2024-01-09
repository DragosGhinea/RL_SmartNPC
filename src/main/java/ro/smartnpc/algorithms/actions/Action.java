package ro.smartnpc.algorithms.actions;

import ro.smartnpc.npc.EnvironmentNPC;

public interface Action {

    ActionType getActionType();

    void execute(EnvironmentNPC npc);

    default void executeContinuous(EnvironmentNPC npc, double... args) {
        execute(npc);
    }
}
