package ro.smartnpc.world.classic;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

public class WorldGeneratorClassic {

    public static World generateWorld(String worldName){
        WorldCreator wc = new WorldCreator(worldName);
        wc.generator(new EmptyChunkGenerator()); // let's create a class for generator of void
        wc.generateStructures(false);
        wc.type(WorldType.FLAT);
        return wc.createWorld();
    }

    public static World loadWorld(String worldName) {
        WorldCreator w = new WorldCreator(worldName);
        w.environment(World.Environment.NORMAL);
        return Bukkit.getServer().createWorld(w);
    }
}
