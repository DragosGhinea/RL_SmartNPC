package ro.smartnpc.algorithms;

import ro.smartnpc.algorithms.actions.Action;
import ro.smartnpc.algorithms.actions.movement.ActionBackward;
import ro.smartnpc.algorithms.actions.movement.ActionForward;
import ro.smartnpc.algorithms.actions.movement.ActionLeft;
import ro.smartnpc.algorithms.actions.movement.ActionRight;
import ro.smartnpc.npc.EnvironmentNPC;

import java.util.ArrayList;
import java.util.List;

public class TestAlgorithm implements Algorithm{
    private final List<Action> actions = new ArrayList<>(){
        {
            add(new ActionForward());
            add(new ActionBackward());
            add(new ActionLeft());
            add(new ActionRight());
        }
    };

    private Action randomSelectAction() {
        int randomIndex = (int) (Math.random() * actions.size());
        return actions.get(randomIndex);
    }
    private EnvironmentNPC environmentNPC;

    public TestAlgorithm() {

    }

    @Override
    public boolean step() {
        Action action = randomSelectAction();
        action.execute(environmentNPC);
        return true;
    }

    @Override
    public void setEnvironmentNPC(EnvironmentNPC npc) {
        environmentNPC = npc;
    }

    @Override
    public void train(int numberOfEpisodes, int numberOfStepsPerEpisode) {
        for (int i = 0; i < numberOfEpisodes; i++) {
            runEpisode(numberOfStepsPerEpisode);
        }
    }

    @Override
    public void forceStopTesting() {

    }

    @Override
    public void test() {

    }

    @Override
    public void reset() {

    }
}
