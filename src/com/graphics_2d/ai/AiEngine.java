package com.graphics_2d.ai;

import com.graphics_2d.world.World;
import com.graphics_2d.world.entities.Mob;
import com.graphics_2d.world.entities.MobInstance;
import com.graphics_2d.world.entities.Mobs;


public class AiEngine {

    private final World world;

    private boolean paused = false;

    public AiEngine(World world) {
        this.world = world;
    }

    public void populateMobs() {
        Mobs.initialize();
        for (int i = 0; i < 2000; i++) {
            Mob mob = Mob.getRandomMob();
            MobInstance mobInst = new MobInstance(mob.getId(), world.randomSpawnPoint(mob.getBiomes(), mob.isCanSwim()));
            world.addMob(mobInst);
        }
    }

    public void updateMobs() {
        if (!paused) {
            for (MobInstance mobInst : world.getMobs()) {
                Mob mob = Mob.MOBS_BY_ID.get(mobInst.getMobId());
                mob.update(world, mobInst);
            }
            world.worldUpdated();
        }
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
