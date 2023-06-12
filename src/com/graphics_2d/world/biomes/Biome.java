package com.graphics_2d.world.biomes;

import com.graphics_2d.world.GameObject;
import com.graphics_2d.world.Tile;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Biome {
    private final String name;
    private final Random random = new Random();
    private final List<Tile> tilesByWeight = new ArrayList<>();
    private final List<GameObject> objectsByWeight = new ArrayList<>();

    private final int id;
    private final Color mapColor;

    private static int nextId = 0;

    Biome(String name, Map<Tile, Integer> tileWeights, Map<GameObject, Integer> objectWeights, Color mapColor) {
        this.mapColor = mapColor;
        this.name = name;
        this.id = nextId++;

        for (Tile tile : tileWeights.keySet().toArray(new Tile[0])) {
            int weight = tileWeights.get(tile);
            for (int i = 0; i < weight; i++) {
                tilesByWeight.add(tile);
            }
        }
        for (GameObject obj : objectWeights.keySet().toArray(new GameObject[0])) {
            int weight = objectWeights.get(obj);
            for (int i = 0; i < weight; i++) {
                objectsByWeight.add(obj);
            }
        }
    }

    public Color getMapColor() {
        return mapColor;
    }

    public int getBiomeId() {
        return id;
    }

    public int getRandomTileIndex() {
        return tilesByWeight.get(random.nextInt(tilesByWeight.size())).getId();
    }

    public GameObject getRandomObject() {
        if (objectsByWeight.size() > 0) {
            return objectsByWeight.get(random.nextInt(objectsByWeight.size()));
        } else {
            return null;
        }
    }
}
