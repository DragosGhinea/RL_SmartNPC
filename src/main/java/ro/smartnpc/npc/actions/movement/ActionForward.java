package ro.smartnpc.npc.actions.movement;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import ro.smartnpc.npc.EnvironmentNPC;
import ro.smartnpc.npc.actions.Action;
import ro.smartnpc.npc.actions.ActionType;

public class ActionForward implements Action {

        @Override
        public ActionType getActionType() {
            return ActionType.MOVE_FORWARD;
        }

        @Override
        public void execute(EnvironmentNPC envNPC) {
            NPC npc = envNPC.getNPC();

            Entity entity = npc.getEntity();
            entity.setVelocity(entity.getVelocity().add(entity.getLocation().getDirection().multiply(0.5)));
        }
}
