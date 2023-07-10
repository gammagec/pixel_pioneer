package com.pixel_pioneer.ai;

import com.pixel_pioneer.clock.Clock;
import com.pixel_pioneer.clock.TickHandler;
import com.pixel_pioneer.actions.KeyboardHandler;
import com.pixel_pioneer.sound.SoundEngine;
import com.pixel_pioneer.util.PointI;
import com.pixel_pioneer.world.World;
import com.pixel_pioneer.world.entities.Mob;
import com.pixel_pioneer.world.entities.MobInstance;
import com.pixel_pioneer.world.entities.Mobs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class AiEngine implements TickHandler {

    private final World world;
    private final SoundEngine soundEngine;
    private KeyboardHandler keyboardHandler;

    public AiEngine(World world, SoundEngine soundEngine, Clock clock) {
        this.world = world;
        this.soundEngine = soundEngine;
        clock.addTickHandler(this);

    }

    public void setKeyboardHandler(KeyboardHandler keyboardHandler){
        this.keyboardHandler = keyboardHandler;
    }

    public void populateMobs() {
        Mobs.initialize();
        world.removeAllMobs();
        Map<Integer, List<PointI>> mobValidPoints = new HashMap<>();
        for (int i = 0; i < 2000; i++) {
            Mob mob = Mob.getRandomMob();
            List<PointI> points = mobValidPoints.get(mob.getId());
            if (points == null) {
                points = world.getValidSpawnPoints(mob.getBiomes(), mob.isCanSwim());
                mobValidPoints.put(mob.getId(), points);
            }
            if (points.size() > 0) {
                PointI spawn = points.get(new Random().nextInt(points.size()));
                MobInstance mobInst = new MobInstance(mob.getId(), spawn);
                world.addMob(mobInst);
            }
        }
        world.setDirty();
    }

    @Override
    public void onTick(int time) {
        for (MobInstance mobInst : world.getMobs()) {
            Mob mob = Mob.MOBS_BY_ID.get(mobInst.getMobId());
            mob.update(world, mobInst, soundEngine, keyboardHandler);
        }
        world.setDirty();
        world.worldUpdated();
    }
}