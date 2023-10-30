package ro.smartnpc.environment;

import org.bukkit.Bukkit;
import org.bukkit.World;
import ro.smartnpc.SmartNPC;
import ro.smartnpc.map.Schematic;

import java.util.concurrent.CompletableFuture;

public class Environment {
    private EnvironmentWorld environmentWorld;

    public Environment(EnvironmentWorld environmentWorld) {
        this.environmentWorld = environmentWorld;
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
}
