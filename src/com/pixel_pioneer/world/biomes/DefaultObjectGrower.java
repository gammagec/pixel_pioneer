package com.pixel_pioneer.world.biomes;

import com.pixel_pioneer.Const;
import com.pixel_pioneer.util.PointI;
import com.pixel_pioneer.world.GameObject;
import com.pixel_pioneer.world.GameObjects;
import com.pixel_pioneer.world.Tile;
import com.pixel_pioneer.world.World;

public class DefaultObjectGrower implements ObjectGrower {
    @Override
    public void growObjects(World world) {
        GameObjects.initialize();
        // Grow plants based on tile settings
        for (int y = 0; y < Const.WORLD_SIZE; y++) {
            for (int x = 0; x < Const.WORLD_SIZE; x++) {
                PointI loc = new PointI(x, y);
                if (world.getObjectAt(loc) == null) {
                    Tile tile = world.getTileAt(loc);
                    GameObject gameObject = tile.getGrowObject();
                    if (gameObject != null && gameObject != GameObjects.NO_OBJECT) {
                        world.putObject(loc, gameObject.getDefaultInstance());
                    }
                }
            }
        }
    }
}
