package ro.smartnpc.algorithms.actions.movement;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import ro.smartnpc.algorithms.actions.Action;
import ro.smartnpc.algorithms.actions.ActionType;
import ro.smartnpc.npc.EnvironmentNPC;

public class ActionBackward implements Action {

        @Override
        public ActionType getActionType() {
            return ActionType.MOVE_BACKWARD;
        }

        @Override
        public void execute(EnvironmentNPC envNPC) {
            NPC npc = envNPC.getNPC();

            Entity entity = npc.getEntity();
            entity.setVelocity(entity.getVelocity().add(entity.getLocation().getDirection().multiply(-0.5)));

            entity.setRotation(entity.getLocation().getYaw() - 180, entity.getLocation().getPitch());
        }
}
