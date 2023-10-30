package ro.smartnpc.npc.actions.movement;

import net.citizensnpcs.api.npc.NPC;
import ro.smartnpc.npc.EnvironmentNPC;
import ro.smartnpc.npc.actions.Action;
import ro.smartnpc.npc.actions.ActionType;

public class ActionBackward implements Action {

        @Override
        public ActionType getActionType() {
            return ActionType.MOVE_BACKWARD;
        }

        @Override
        public void execute(EnvironmentNPC envNPC) {
            NPC npc = envNPC.getNPC();
            npc.getEntity().setVelocity(npc.getEntity().getLocation().getDirection().multiply(-0.5));
        }
}
