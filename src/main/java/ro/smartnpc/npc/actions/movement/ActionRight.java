package ro.smartnpc.npc.actions.movement;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import ro.smartnpc.npc.actions.Action;
import ro.smartnpc.npc.actions.ActionType;
import ro.smartnpc.npc.EnvironmentNPCDebug;

public class ActionRight implements Action {

        @Override
        public ActionType getActionType() {
            return ActionType.MOVE_RIGHT;
        }

        @Override
        public void execute(EnvironmentNPCDebug envNPC) {
            NPC npc = envNPC.getNPC();

            Vector direction = npc.getEntity().getLocation().getDirection();
            Vector rightDirection = new Vector(-direction.getZ(), direction.getY(), direction.getX());

            Entity entity = npc.getEntity();
            entity.setVelocity(entity.getVelocity().add(rightDirection.multiply(0.5)));

            entity.setRotation(entity.getLocation().getYaw() + 90, entity.getLocation().getPitch());
        }
}
