package ro.smartnpc.algorithms.actions.movement;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import ro.smartnpc.algorithms.actions.Action;
import ro.smartnpc.algorithms.actions.ActionType;
import ro.smartnpc.npc.EnvironmentNPC;

public class ActionLeft implements Action {

        @Override
        public ActionType getActionType() {
            return ActionType.MOVE_LEFT;
        }

        @Override
        public void execute(EnvironmentNPC envNPC) {
            NPC npc = envNPC.getNPC();
            Vector direction = npc.getEntity().getLocation().getDirection();
            Vector leftDirection = new Vector(direction.getZ(), direction.getY(), -direction.getX());

            Entity entity = npc.getEntity();
            entity.setVelocity(entity.getVelocity().add(leftDirection.multiply(0.5)));

            entity.setRotation(entity.getLocation().getYaw() - 90, entity.getLocation().getPitch());
        }
}
