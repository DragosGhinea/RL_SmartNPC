package ro.smartnpc.environment;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ro.smartnpc.SmartNPC;
import ro.smartnpc.algorithms.TestAlgorithm;
import ro.smartnpc.npc.EnvironmentNPC;
import ro.smartnpc.npc.actions.Action;
import ro.smartnpc.npc.actions.movement.ActionBackward;
import ro.smartnpc.npc.actions.movement.ActionForward;
import ro.smartnpc.npc.actions.movement.ActionLeft;
import ro.smartnpc.npc.actions.movement.ActionRight;

import java.util.ArrayList;
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
    private List<EnvironmentNPC> agents;

    public Environment(EnvironmentWorld environmentWorld, int numberOfAgents) {
        instance = this;
        this.environmentWorld = environmentWorld;
        agents = new ArrayList<>(numberOfAgents);
        registerAgents(numberOfAgents);
    }

    public void registerAgents(int numberOfAgents) {
        for (int i = 0; i < numberOfAgents; i++)
            agents.add(new EnvironmentNPC(this, new TestAlgorithm()));
    }

    public CompletableFuture<World> init(){
        environmentWorld.loadWorldAndMap();

        final CompletableFuture<World> toReturn = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(SmartNPC.getInstance(), () -> {
            try {
                while (!environmentWorld.isMapLoaded()) {
                    Thread.sleep(300);
                }

                Bukkit.getScheduler().runTask(SmartNPC.getInstance(), () -> {
                    for (EnvironmentNPC agent : agents) {
                        agent.getOrSpawnNPC();
                    }
                });

                toReturn.complete(environmentWorld.getWorld());
            }catch(InterruptedException x) {
                toReturn.completeExceptionally(x);
            }
        });

        return toReturn;
    }

    public void reset(){
        for (EnvironmentNPC agent : agents) {
            agent.getNPC().teleport(environmentWorld.getWorld().getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        }
    }

    public void step(){
        for (EnvironmentNPC agent : agents) {
            agent.getAlgorithm().step();
        }
    }

    private boolean isAutoStepping = false;
    public void autoStep(int numberOfSteps) {
        if (isAutoStepping)
            return;

        isAutoStepping = true;
        new BukkitRunnable() {
            int steps = 0;
            @Override
            public void run() {
                if (steps >= numberOfSteps) {
                    cancel();
                    isAutoStepping = false;
                    return;
                }
                step();
                steps++;
            }
        }.runTaskTimer(SmartNPC.getInstance(), 0, 5);
    }

    public EnvironmentWorld getEnvironmentWorld() {
        return environmentWorld;
    }

    public List<EnvironmentNPC> getAgents() {
        return agents;
    }
}
