package ro.smartnpc.algorithms;

import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerTeleportEvent;
import ro.smartnpc.SmartNPC;
import ro.smartnpc.Utils;
import ro.smartnpc.algorithms.actions.Action;
import ro.smartnpc.algorithms.actions.movement.ActionBackward;
import ro.smartnpc.algorithms.actions.movement.ActionForward;
import ro.smartnpc.algorithms.actions.movement.ActionLeft;
import ro.smartnpc.algorithms.actions.movement.ActionRight;
import ro.smartnpc.algorithms.states.RelativeCoordinatesState;
import ro.smartnpc.algorithms.states.State;
import ro.smartnpc.npc.EnvironmentNPC;

import java.io.File;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class QLearningAlgorithm implements Algorithm {
    private final List<Action> actions = new ArrayList<>() {
        {
            add(new ActionForward());
            add(new ActionBackward());
            add(new ActionLeft());
            add(new ActionRight());
        }
    };
    private static final double ALPHA = 0.1;  // Learning rate
    private static final double GAMMA = 0.9;  // Discount factor
    private static final double EPSILON = 0.1;  // Exploration-exploitation trade-off

    private Map<State, Map<Integer, Double>> Q = new HashMap<>();

    private EnvironmentNPC environmentNPC;

    private double score = 0.0;

    @Override
    public void setEnvironmentNPC(EnvironmentNPC npc) {
        environmentNPC = npc;
    }

    private int epsilonGreedyPolicy(State state) {
        Map<Integer, Double> actionValues = Q.get(state);

        if (actionValues == null || Math.random() < EPSILON) {
            return getRandomAction();
        } else {
            return argmaxAction(actionValues);
        }
    }

    private int getActionTesting(State state) {
        Map<Integer, Double> actionValues = Q.get(state);
        if (actionValues == null) {
            return -1;
        } else {
            return argmaxAction(actionValues);
        }
    }

    private int getRandomAction() {
        return new Random().nextInt(actions.size());
    }

    private int argmaxAction(Map<Integer, Double> actionValues) {
        int maxAction = 0;
        double maxValue = Double.NEGATIVE_INFINITY;
        for (Map.Entry<Integer, Double> entry : actionValues.entrySet()) {
            if (entry.getValue() > maxValue) {
                maxAction = entry.getKey();
                maxValue = entry.getValue();
            }
        }
        return maxAction;
    }

    private boolean reachedTarget(State state) {
        return state.isFinalState(environmentNPC);
    }

    private State takeAction(State currentState, int actionIndex) {
        Action action = actions.get(actionIndex);
        CompletableFuture<Void> future = new CompletableFuture<>();
        Bukkit.getScheduler().runTask(SmartNPC.getInstance(),
                () -> action.execute(environmentNPC).thenRun(() -> future.complete(null))
        );

        try {
            future.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            SmartNPC.getInstance().getLogger().warning("Error while executing action " + action.getActionType().name() + " for " + environmentNPC.getName());
        } catch (TimeoutException e) {
            SmartNPC.getInstance().getLogger().warning("Timeout while executing action " + action.getActionType().name() + " for " + environmentNPC.getName());
            return null;
        }

        return processState();
    }

    private double getReward(State previousState, State state) {
        return state.getReward(previousState, environmentNPC);
    }

    private void updateQValue(State state, int action, double reward, State nextState) {
        // Q-learning update rule
        double currentQValue = Q.getOrDefault(state, new HashMap<>()).getOrDefault(action, 0.0);
        double maxNextQValue = maxQValue(nextState);
        double updatedQValue = currentQValue + ALPHA * (reward + GAMMA * maxNextQValue - currentQValue);

        // Update Q-value in the map
        Q.computeIfAbsent(state, k -> new HashMap<>()).put(action, updatedQValue);
    }

    private double maxQValue(State state) {
        // Find the maximum Q-value for the given state.
        Map<Integer, Double> actionValues = Q.get(state);
        if (actionValues == null) {
            return 0.0;  // If no Q-values are available, assume 0.
        }

        return actionValues.values().stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
    }

    private State processState() {
        return new RelativeCoordinatesState(environmentNPC.getNPC().getEntity().getLocation(), environmentNPC.getEnvironment().getTarget());
    }

    private double totalReward = 0.0;

    @Override
    public boolean step() {
        State currentState = processState();
        if (reachedTarget(currentState))
            return false;

        if (testing) {
            int action = getActionTesting(currentState);
            if (action == -1) {
                SmartNPC.getInstance().getLogger().info("No action found for state " + currentState);
                return false;
            }
            takeAction(currentState, action);
            return true;
        }

        int action = epsilonGreedyPolicy(currentState);
        State nextState = takeAction(currentState, action);

        double reward = getReward(currentState, nextState);

        totalReward += reward;
        // Q-learning update rule
        updateQValue(currentState, action, reward, nextState);
        return true;
    }

    @Override
    public void runEpisode(int numberOfSteps) {
        for (int i = 0; i < numberOfSteps; i++) {
            if (forceStopTraining)
                break;

            if (step())
                continue;

            SmartNPC.getInstance().getLogger().info("[" + environmentNPC.getName() + "] Reached target!");
            break;
        }
    }

    @Override
    public void reset() {
        totalReward = 0.0;
        CompletableFuture<Void> future = new CompletableFuture<>();
        Bukkit.getScheduler().runTask(SmartNPC.getInstance(), () -> {
            try {
                environmentNPC.getNPC().teleport(environmentNPC.getEnvironment().getEnvironmentWorld().getWorld().getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                environmentNPC.getNPC().getNavigator().cancelNavigation();
            }catch(Exception x){
                x.printStackTrace();
            }

            future.complete(null);
        });

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean forceStopTraining = false;

    @Override
    public void train(int numberOfEpisodes, int numberOfStepsPerEpisode) {
        forceStopTraining = false;

        double trainingScore = 0;
        for (int i = 0; i < numberOfEpisodes; i++) {
            runEpisode(numberOfStepsPerEpisode);
            if (forceStopTraining) {
                break;
            }

            double episodeReward = totalReward / numberOfStepsPerEpisode;
            SmartNPC.getInstance().getLogger().info("[" + environmentNPC.getName() + "] Reward average episode " + i + ": " + (episodeReward));
            trainingScore += episodeReward;
            reset();
        }

        score += trainingScore / numberOfEpisodes;
    }

    @Override
    public void forceStopTraining() {
        forceStopTraining = true;
        Bukkit.getScheduler().runTask(SmartNPC.getInstance(), () -> {
            environmentNPC.getNPC().teleport(environmentNPC.getEnvironment().getEnvironmentWorld().getWorld().getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
            environmentNPC.getNPC().getNavigator().cancelNavigation();
        });
    }

    private boolean testing = false;

    @Override
    public void test() {
        testing = true;
        while (testing && step()) ;
        testing = false;
        reset();
    }

    public void forceStopTesting() {
        testing = false;
    }

    @Override
    public void destroy() {
        forceStopTraining();
        forceStopTesting();
        Q.clear();

        for (Action action : actions) {
            action.destroy();
        }
        actions.clear();
    }

    @Override
    public void saveCurrentData(File whereToSave) {
        Utils.serialize(Q, whereToSave);
    }

    @Override
    public void loadCurrentData(File fromWhereToLoad) {
        score = 0;
        Q = Utils.deserializeQ(fromWhereToLoad);
        if (Q == null)
            Q = new HashMap<>();
    }

    @Override
    public double getScore() {
        return score;
    }
}
