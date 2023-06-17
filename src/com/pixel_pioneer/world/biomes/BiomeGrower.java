package com.pixel_pioneer.world.biomes;

import com.pixel_pioneer.world.LocationInfo;

public interface BiomeGrower {
    void growBiomes(LocationInfo[][] locations, Integer[] growOnly);
}
