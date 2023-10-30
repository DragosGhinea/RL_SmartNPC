package ro.smartnpc.world.classic;

import org.bukkit.World;
import org.bukkit.entity.Player;
import ro.smartnpc.world.WorldUtils;

import java.util.HashSet;
import java.util.Set;

public class WorldUtilsClassic implements WorldUtils {

    private Set<String> loadedWorldsCache = new HashSet<>();

    private World createWorld(String worldName) {
        WorldGeneratorClassic worldGeneratorClassic = new WorldGeneratorClassic();
        return worldGeneratorClassic.generateWorld(worldName);
    }

    private World loadWorldIntern(String worldName) {
        return null;
    }

    @Override
    public Set<String> getLoadedWorldsCache() {
        return loadedWorldsCache;
    }

    @Override
    public void deleteWorld(String worldName) {

    }

    @Override
    public World loadWorld(String worldName) {
        loadedWorldsCache.add(worldName);

        World loadAttempt = loadWorldIntern(worldName);
        if (loadAttempt != null) {
            return loadAttempt;
        }

        return createWorld(worldName);
    }
}
