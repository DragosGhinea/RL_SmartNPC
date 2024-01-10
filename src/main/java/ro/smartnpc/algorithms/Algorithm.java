package ro.smartnpc.algorithms;

import ro.smartnpc.npc.EnvironmentNPC;

public interface Algorithm {

    void setEnvironmentNPC(EnvironmentNPC npc);

    /**
     * @return true if step was successful, false if the algorithm should stop
     */
    boolean step();

    void reset();

    void test();

    void forceStopTesting();

    default void runEpisode(int numberOfSteps) {
        for (int i = 0; i < numberOfSteps; i++)
            step();

        reset();
    }

    void train(int numberOfEpisodes, int numberOfStepsPerEpisode);


}
