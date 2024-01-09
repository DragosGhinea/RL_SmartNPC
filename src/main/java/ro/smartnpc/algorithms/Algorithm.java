package ro.smartnpc.algorithms;

import ro.smartnpc.npc.EnvironmentNPC;

public interface Algorithm {

    void setEnvironmentNPC(EnvironmentNPC npc);

    void step();
}
