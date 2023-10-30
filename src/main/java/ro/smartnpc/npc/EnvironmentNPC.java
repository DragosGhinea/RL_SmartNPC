package ro.smartnpc.npc;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.EntityType;
import ro.smartnpc.environment.Environment;

public class EnvironmentNPC {

    NPC npc;

    private final Environment environment;

    public EnvironmentNPC(Environment environment) {
        this.environment = environment;
    }

    public NPC createAgentNpc(){
        if (this.npc != null)
            return npc;

        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Agent");
        npc.spawn(environment.getEnvironmentWorld().getWorld().getSpawnLocation());

        this.npc = npc;
        return npc;
    }

    public NPC getNPC() {
        return npc;
    }

    public NPC getOrSpawnNPC(){
        if (npc == null)
            return createAgentNpc();
        return npc;
    }
}
