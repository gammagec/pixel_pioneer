package com.pixel_pioneer.ai;

import com.pixel_pioneer.clock.Clock;
import com.pixel_pioneer.clock.TickHandler;
import com.pixel_pioneer.sound.SoundEngine;
import com.pixel_pioneer.world.World;
import com.pixel_pioneer.world.entities.Mob;
import com.pixel_pioneer.world.entities.MobInstance;
import com.pixel_pioneer.world.entities.Mobs;


public class AiEngine implements TickHandler {

    private final World world;
    private final SoundEngine soundEngine;

    public AiEngine(World world, SoundEngine soundEngine, Clock clock) {
        this.world = world;
        this.soundEngine = soundEngine;
        clock.addTickHandler(this);
    }

    public void populateMobs() {
        Mobs.initialize();
        world.removeAllMobs();
        for (int i = 0; i < 2000; i++) {
            Mob mob = Mob.getRandomMob();
            MobInstance mobInst = new MobInstance(mob.getId(), world.randomSpawnPoint(mob.getBiomes(), mob.isCanSwim()));
            world.addMob(mobInst);
        }
    }

    @Override
    public void onTick(int time) {
        for (MobInstance mobInst : world.getMobs()) {
            Mob mob = Mob.MOBS_BY_ID.get(mobInst.getMobId());
            mob.update(world, mobInst, soundEngine);
        }
        world.worldUpdated();
    }
}
