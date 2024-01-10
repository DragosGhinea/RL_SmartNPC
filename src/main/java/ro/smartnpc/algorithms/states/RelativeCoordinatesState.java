package ro.smartnpc.algorithms.states;

import org.bukkit.Location;
import ro.smartnpc.Utils;
import ro.smartnpc.npc.EnvironmentNPC;

import java.io.Serializable;
import java.util.Objects;

public class RelativeCoordinatesState implements State, Serializable {

    private final int x;
    private final int y;
    private final int z;

    // 0 - facing target
    // 1 - target is on the left
    // 2 - target is on the right
    // 3 - target is behind
    private final int direction;

    public RelativeCoordinatesState(Location source, Location target) {
        x = target.getBlockX() - source.getBlockX();
        y = target.getBlockY() - source.getBlockY();
        z = target.getBlockZ() - source.getBlockZ();

        direction = Utils.getFacingResult(source, target);
    }

    @Override
    public boolean isFinalState(EnvironmentNPC environmentNPC) {
        if (x >= 2)
            return false;

        if (y >= 2)
            return false;

        if (z >= 2)
            return false;

        return true;
    }

    @Override
    public double getReward(EnvironmentNPC environmentNPC) {
        long distanceToTarget = (long) x * x + (long) y * y + (long) z * z;
        double rewardDistance = -0.01 * distanceToTarget;
        return rewardDistance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelativeCoordinatesState state = (RelativeCoordinatesState) o;
        return state.x == x &&
                state.y == y &&
                state.z == z &&
                state.direction == direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, direction);
    }

    @Override
    public String toString() {
        return "RelativeCoordinatesState{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", direction=" + direction +
                '}';
    }
}
