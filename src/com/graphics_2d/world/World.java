package com.graphics_2d.world;

import com.graphics_2d.Const;
import com.graphics_2d.world.entities.Player;

import java.io.IOException;
import java.util.*;
import java.util.List;

import static java.lang.System.exit;

public class World {

    private final Player player = new Player();
    private WorldUpdateHandler worldUpdateHandler = null;

    private final int[][] tiles = new int[Const.WORLD_SIZE][Const.WORLD_SIZE];
    private final int[][] objects = new int[Const.WORLD_SIZE][Const.WORLD_SIZE];
    private final int[][] tileBiomes = new int[Const.WORLD_SIZE][Const.WORLD_SIZE];
    Random random = new Random();

    public World() {
        for (ImageAsset imageAsset : ImageAssets.ALL_ASSETS) {
            try {
                imageAsset.initialize();
            } catch (IOException e) {
                System.out.println("Couldn't load " + imageAsset.getFileName());
                exit(1);
            }
        }

        generateBiomes();
        generateMap();
    }

    public Player getPlayer() {
        return player;
    }

    public void generateBiomes() {
        int numBiomesXorY = Const.WORLD_SIZE / Const.BIOME_SIZE;
        for(int i = 0; i < numBiomesXorY; i++) {
            for (int j = 0; j < numBiomesXorY; j++) {
                int biomeId = random.nextInt(Biomes.ALL_BIOMES.length);
                for (int bx = 0; bx < Const.BIOME_SIZE; bx++) {
                    for (int by = 0; by < Const.BIOME_SIZE; by++) {
                        int x = i * Const.BIOME_SIZE + bx;
                        int y = j * Const.BIOME_SIZE + by;
                        tileBiomes[y][x] = biomeId;
                    }
                }
            }
        }

        for(int i = 0; i < 10; i++) {
            growBiomes();
        }
        for (int i = 0; i < random.nextInt(Const.MAX_RIVER_GENS); i++) {
            generateRiver();
        }
    }

    public void pickupObject(int x, int y) {
        player.giveObject(GameObjects.OBJECTS_BY_ID.get(objects[y][x]));
        objects[y][x] = -1;
    }

    public Tile getTileAt(int x, int y) {
        return Tiles.TILES_BY_ID.get(tiles[y][x]);
    }

    public Biome getBiomeAt(int x, int y) {
        if (inBounds(x, y)) {
            int biomeId = tileBiomes[y][x];
            return Biomes.BIOMES_BY_ID.get(biomeId);
        }
        return null;
    }

    public GameObject getObjectAt(int x, int y) {
        if (inBounds(x, y)) {
            int objId = objects[y][x];
            if (objId == -1) {
                return null;
            } else {
                return GameObjects.OBJECTS_BY_ID.get(objects[y][x]);
            }
        } else {
            return null;
        }
    }

    public void generateMap() {
        // This is the map generation!
        for(int i = 0; i < Const.WORLD_SIZE; i++) {
            for (int j = 0; j < Const.WORLD_SIZE; j++) {
                int biomeId = tileBiomes[i][j];
                Biome b = Biomes.BIOMES_BY_ID.get(biomeId);
                int tileId = b.getRandomTileIndex();
                tiles[i][j] = tileId;
            }
        }
        for (int i = 0; i < Const.WORLD_SIZE; i++) {
            for (int j = 0; j < Const.WORLD_SIZE; j++) {
                int biomeId = tileBiomes[i][j];
                Biome b = Biomes.BIOMES_BY_ID.get(biomeId);
                GameObject obj = b.getRandomObject();
                if (obj != GameObjects.NO_OBJECT && obj != null) {
                    objects[i][j] = obj.getId();
                } else {
                    objects[i][j] = -1;
                }
            }
        }
    }

    void generateRiver() {
        int x = random.nextInt(Const.WORLD_SIZE);
        int y = random.nextInt(Const.WORLD_SIZE);
        int width = random.nextInt(Const.MAX_RIVER_WIDTH);
        int facing = random.nextInt(4);
        while(x >= 0 && y >= 0 && x < Const.WORLD_SIZE && y < Const.WORLD_SIZE) {
            switch (facing) {
                case 0 -> { // east
                    for (int i = 0; i < width; i++) {
                        if (inBounds(x, y + i)) {
                            tileBiomes[y + i][x] = Biomes.WATER.getBiomeId();
                        }
                    }
                }
                case 1 -> { // south
                    for (int i = 0; i < width; i++) {
                        if (inBounds(x - i, y)) {
                            tileBiomes[y][x - i] = Biomes.WATER.getBiomeId();
                        }
                    }
                }
                case 2 -> { // west
                    for (int i = 0; i < width; i++) {
                        if (inBounds(x, y - i)) {
                            tileBiomes[y - i][x] = Biomes.WATER.getBiomeId();
                        }
                    }
                }
                case 3 -> { // north
                    for (int i = 0; i < width; i++) {
                        if (inBounds(x - i, y)) {
                            tileBiomes[y][x - i] = Biomes.WATER.getBiomeId();
                        }
                    }
                }
            }
            int rand = random.nextInt(100);
            if (rand > 75) {
                // forward
                switch (facing) {
                    case 0 -> x++;
                    case 1 -> y++;
                    case 2 -> x--;
                    case 3 -> y--;
                }
            } else if (rand > 13) {
                facing++;
                if (facing > 3) facing = 0;
            } else { // 12
                facing--;
                if (facing < 0) facing = 3;
            }
        }
    }

    public void growBiomes() {
        System.out.println("Grow biomes");
        for (int i = 0; i < Const.WORLD_SIZE; i++) {
            for (int j = 0; j < Const.WORLD_SIZE; j++) {
                int b = tileBiomes[i][j];
                int weight = 0;
                int validNeighbors = 0;
                int[][] neighbors = new int[][] {
                        {j - 1, i - 1},
                        {j,     i - 1},
                        {j + 1, i - 1},
                        {j - 1, i    },
                        {j + 1, i    },
                        {j - 1, i + 1},
                        {j,     i + 1},
                        {j + 1, i + 1},
                };
                List<List<Integer>> openNeighbors = new ArrayList<>();
                for (int[] neighbor : neighbors) {
                    int x = neighbor[1];
                    int y = neighbor[0];
                    if (inBounds(x, y)) {
                        validNeighbors++;
                        if (tileBiomes[y][x] == b) {
                            weight++;
                        } else {
                            openNeighbors.add(Arrays.asList(y, x));
                        }
                    }
                }
                int emptyNeighbors = openNeighbors.size();
                int invalidNeighbors = 8 - validNeighbors;
                if (emptyNeighbors == 0) {
                    // all same neighbors
                    continue;
                }
                int rand = random.nextInt(10 - invalidNeighbors);
                if (rand < weight) {
                    List<Integer> neighbor = openNeighbors.get(random.nextInt(openNeighbors.size()));
                    tileBiomes[neighbor.get(0)][neighbor.get(1)] = b;
                }
            }
        }
    }

    public boolean inBounds(int x, int y) {
        return !(x < 0 || y < 0 || x > Const.WORLD_SIZE - 1 || y > Const.WORLD_SIZE - 1);
    }

    public boolean getBlocking(int x, int y) {
        int objectIndex = objects[y][x];
        if (objectIndex != -1 && GameObjects.OBJECTS_BY_ID.get(objectIndex).isBlocking()) {
            return true;
        }
        return Tiles.TILES_BY_ID.get(tiles[y][x]).isBlocking();
    }

    public void setWorldUpdateHandler(WorldUpdateHandler worldUpdateHandler) {
        this.worldUpdateHandler = worldUpdateHandler;
    }

    public void worldUpdated() {
        if(this.worldUpdateHandler != null) {
            this.worldUpdateHandler.worldUpdated();
        }
    }

    public void putObject(int x, int y, int objIndex) {
        if (objects[y][x] == -1) {
            objects[y][x] = objIndex;
        }
    }
}
