package ro.smartnpc.environment;

import org.bukkit.Bukkit;
import org.bukkit.World;
import ro.smartnpc.SmartNPC;
import ro.smartnpc.map.Schematic;
import ro.smartnpc.map.SchematicUtils;

public class EnvironmentWorld {
    private World world;
    private String worldName;
    private boolean schematicLoaded;

    private Schematic schematic;

    public EnvironmentWorld(String worldName, Schematic schematic) {
        this.worldName = worldName;
        this.schematic = schematic;
    }

    public void loadWorldAndMap() {
        if (world != null)
            return;

        world = SmartNPC.getInstance().getWorldUtils().loadWorld(worldName);

        Bukkit.getScheduler().runTaskAsynchronously(SmartNPC.getInstance(), () -> {
            schematicLoaded = SchematicUtils.loadSchematic(schematic, world.getSpawnLocation());
        });
    }

    public boolean isMapLoaded(){
        return schematicLoaded;
    }

    public String getWorldName() {
        return worldName;
    }

    public World getWorld() {
        return world;
    }

    public Schematic getSchematic() {
        return schematic;
    }
}