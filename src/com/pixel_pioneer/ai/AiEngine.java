package com.pixel_pioneer.ai;

import com.pixel_pioneer.actions.KeyboardHandler;
import com.pixel_pioneer.sound.SoundEngine;
import com.pixel_pioneer.world.World;
import com.pixel_pioneer.world.entities.Mob;
import com.pixel_pioneer.world.entities.MobInstance;
import com.pixel_pioneer.world.entities.Mobs;


public class AiEngine {

    private final World world;
    private final SoundEngine soundEngine;
    private KeyboardHandler keyboardHandler;

    private boolean paused = false;

    public AiEngine(World world, SoundEngine soundEngine) {
        this.world = world;
        this.soundEngine = soundEngine;

    }
    public void setKeyboardHandler(KeyboardHandler keyboardHandler){
        this.keyboardHandler = keyboardHandler;
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

    public void updateMobs() {
        if (!paused) {
            for (MobInstance mobInst : world.getMobs()) {
                Mob mob = Mob.MOBS_BY_ID.get(mobInst.getMobId());
                mob.update(world, mobInst, soundEngine, keyboardHandler);
            }
            world.worldUpdated();
        }
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
