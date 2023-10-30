package ro.smartnpc.environment;

import org.bukkit.Bukkit;
import org.bukkit.World;
import ro.smartnpc.SmartNPC;
import ro.smartnpc.map.Schematic;
import ro.smartnpc.npc.EnvironmentNPC;
import ro.smartnpc.npc.actions.Action;
import ro.smartnpc.npc.actions.movement.ActionBackward;
import ro.smartnpc.npc.actions.movement.ActionForward;
import ro.smartnpc.npc.actions.movement.ActionLeft;
import ro.smartnpc.npc.actions.movement.ActionRight;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Environment {

    private static Environment instance;

    public static Environment getRunningInstance() {
        return instance;
    }

    public static List<Action> actionList = List.of(
            new ActionLeft(),
            new ActionRight(),
            new ActionForward(),
            new ActionBackward()
    );

    private EnvironmentWorld environmentWorld;
    private EnvironmentNPC environmentNPC;

    public Environment(EnvironmentWorld environmentWorld) {
        instance = this;
        this.environmentWorld = environmentWorld;
        this.environmentNPC = new EnvironmentNPC(this);
    }

    public CompletableFuture<World> init(){
        environmentWorld.loadWorldAndMap();

        final CompletableFuture<World> toReturn = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(SmartNPC.getInstance(), () -> {
            try {
                while (!environmentWorld.isMapLoaded()) {
                    Thread.sleep(300);
                }
                toReturn.complete(environmentWorld.getWorld());
            }catch(InterruptedException x) {
                toReturn.completeExceptionally(x);
            }
        });

        return toReturn;
    }

    public void reset(){

    }

    public void step(){

    }

    public void executeAction(Action action){
        action.execute(environmentNPC);
    }

    public EnvironmentWorld getEnvironmentWorld() {
        return environmentWorld;
    }

    public EnvironmentNPC getEnvironmentNPC() {
        return environmentNPC;
    }
}
