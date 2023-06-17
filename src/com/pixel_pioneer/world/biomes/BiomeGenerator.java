package com.pixel_pioneer.world.biomes;

import com.pixel_pioneer.world.LocationInfo;
import com.pixel_pioneer.world.World;

public interface BiomeGenerator {

    void generateBiomes(LocationInfo[][] locations, World world);
}
