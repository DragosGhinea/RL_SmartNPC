package ro.smartnpc.npc.actions.movement;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import ro.smartnpc.npc.actions.Action;
import ro.smartnpc.npc.actions.ActionType;
import ro.smartnpc.npc.EnvironmentNPCDebug;

public class ActionBackward implements Action {

        @Override
        public ActionType getActionType() {
            return ActionType.MOVE_BACKWARD;
        }

        @Override
        public void execute(EnvironmentNPCDebug envNPC) {
            NPC npc = envNPC.getNPC();

            Entity entity = npc.getEntity();
            entity.setVelocity(entity.getVelocity().add(entity.getLocation().getDirection().multiply(-0.5)));

            entity.setRotation(entity.getLocation().getYaw() - 180, entity.getLocation().getPitch());
        }
}
