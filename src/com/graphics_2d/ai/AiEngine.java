package com.graphics_2d.ai;

import com.graphics_2d.util.PointI;
import com.graphics_2d.world.ImageAssets;
import com.graphics_2d.world.World;
import com.graphics_2d.world.entities.Mob;
import com.graphics_2d.world.entities.MobType;

import java.util.Random;

public class AiEngine {

    private final World world;
    private final Random random = new Random();

    public AiEngine(World world) {
        this.world = world;
    }

    public void populateMobs() {
        for (int i = 0; i < 2000; i++) {
            MobType type = MobType.values()[random.nextInt(MobType.values().length)];
            Mob mob = new Mob(world, type);
            mob.setLocation(world.randomSpawnPoint(Mob.getBiomesForMob(type), mob.isCanSwim()));
            world.addMob(mob);
        }
    }

    public void updateMobs() {
        for (Mob mob : world.getMobs()) {
            mob.update();
        }
        world.worldUpdated();
    }
}
