package com.pixel_pioneer.world;

import com.pixel_pioneer.Const;
import com.pixel_pioneer.clock.Clock;
import com.pixel_pioneer.sound.SoundEngine;
import com.pixel_pioneer.util.PointI;
import com.pixel_pioneer.world.biomes.*;
import com.pixel_pioneer.world.entities.Mob;
import com.pixel_pioneer.world.entities.MobInstance;
import com.pixel_pioneer.world.entities.Player;

import java.util.*;
import java.util.stream.Collectors;

public class World {
    private final Player player;
    private WorldUpdateHandler worldUpdateHandler = null;

    private final LocationInfo[][] locations = new LocationInfo[Const.WORLD_SIZE][Const.WORLD_SIZE];

    private final List<MobInstance> mobs = new ArrayList<>();
    private final Random random = new Random();

    // private final BiomeGenerator biomeGenerator = new OriginalBiomeGenerator();
    private final BiomeGenerator biomeGenerator = new PerlinBiomeGenerator();
    private final BiomeGrower biomeGrower = new DefaultBiomeGrower();

    //private PerlinNoiseGenerator perlinNoiseGenerator = new PerlinNoiseGenerator(random.nextGaussian());
    private final double[][] variantMap = new double[Const.WORLD_SIZE][Const.WORLD_SIZE];

    private final ObjectGrower objectGrower = new DefaultObjectGrower();

    public World(Clock clock, SoundEngine soundEngine) {
        player = new Player(clock, soundEngine, this);
        ImageAssets.initialize();

        for (int y = 0; y < Const.WORLD_SIZE; y++) {
            for (int x = 0; x < Const.WORLD_SIZE; x++) {
                locations[y][x] = new LocationInfo();
                //variantMap[y][x] = perlinNoiseGenerator.noise(x, y, 32);
                variantMap[y][x] = random.nextDouble(2) - 1;
            }
        }
        generateBiomes();
        generateMap();

        for (int i = 0; i < Const.STARTING_GROWTH_CYCLES; i++) {
            objectGrower.growObjects(this);
        }

        player.reset(this);
    }

    public void addMob(MobInstance mob) {
        this.mobs.add(mob);
    }

    public MobInstance getMobAt(PointI loc) {
        for (MobInstance mob : mobs) {
            PointI p = mob.getLocation();
            if (Objects.equals(p.getX(), loc.getX()) && Objects.equals(p.getY(), loc.getY())) {
                return mob;
            }
        }
        return null;
    }

    public void killMob(MobInstance mob) {
        mobs.remove(mob);
        PointI loc = mob.getLocation();
        ObjectInstance drop = Mob.MOBS_BY_ID.get(mob.getMobId()).getDrop();
        if (drop != null) {
            putObject(loc, drop);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void generateBiomes() {
        biomeGenerator.generateBiomes(locations, this);

        for(int i = 0; i < Const.DEFAULT_GROW_BIOMES; i++) {
            growBiomes();
        }
        /*
        for (int i = 0; i < random.nextInt(Const.MAX_RIVER_GENS); i++) {
            generateRiver();
        }*/
    }

    public PointI randomSpawnPoint() {
        return randomSpawnPoint(
                Arrays.stream(Biomes.ALL_BIOMES).map(Biome::getBiomeId).collect(Collectors.toSet()), false);
    }

    public PointI randomSpawnPoint(Set<Integer> biomes, boolean includeSwim) {
        while(true) {
            PointI loc = new PointI(random.nextInt(Const.WORLD_SIZE), random.nextInt(Const.WORLD_SIZE));
            Tile tile = getTileAt(loc);
            ObjectInstance obj = getObjectAt(loc);
            boolean objBlocking = false;
            if (obj != null) {
                GameObject gObj = GameObject.OBJECTS_BY_ID.get(obj.getObjectId());
                objBlocking = gObj.isBlocking();
            }
            Integer biome = getBiomeAt(loc).getBiomeId();
            if ((includeSwim || !tile.isSwim()) &&
                    !tile.isBlocking() &&
                    tile.getDamage() == 0 &&
                    !objBlocking &&
                    biomes.contains(biome)) {
                return loc;
            }
        }
    }

    public List<MobInstance> getMobs() {
        return mobs;
    }

    public void growBiomes() {
        biomeGrower.growBiomes(locations, new Integer[] {});
    }

    public void growBiomes(Integer[] biomes) {
        biomeGrower.growBiomes(locations, biomes);
    }

    public void removeObject(int x, int y) {
        locations[y][x].setObjectInstance(null);
    }

    public void pickupObject(int x, int y) {
        ObjectInstance obj = locations[y][x].getObjectInstance();
        if (obj != null) {
            player.giveObject(obj);
            locations[y][x].setObjectInstance(null);
        }
    }

    public Tile getTileAt(PointI loc) {
        if (inBounds(loc)) {
            return Tiles.TILES_BY_ID.get(locations[loc.getY()][loc.getX()].getTileId());
        } else {
            return null;
        }
    }

    public Biome getBiomeAt(PointI loc) {
        if (inBounds(loc)) {
            int biomeId = locations[loc.getY()][loc.getX()].getBiomeId();
            return Biomes.BIOMES_BY_ID.get(biomeId);
        }
        return null;
    }

    public ObjectInstance getObjectAt(PointI loc) {
        if (inBounds(loc)) {
            return locations[loc.getY()][loc.getX()].getObjectInstance();
        } else {
            return null;
        }
    }

    public void generateMap() {
        // This is the map generation!
        for(int i = 0; i < Const.WORLD_SIZE; i++) {
            for (int j = 0; j < Const.WORLD_SIZE; j++) {
                int biomeId = locations[i][j].getBiomeId();
                Biome b = Biomes.BIOMES_BY_ID.get(biomeId);
                int tileId = b.getRandomTileIndex();
                locations[i][j].setTileId(tileId);
            }
        }
        for (int i = 0; i < Const.WORLD_SIZE; i++) {
            for (int j = 0; j < Const.WORLD_SIZE; j++) {
                int biomeId = locations[i][j].getBiomeId();
                Biome b = Biomes.BIOMES_BY_ID.get(biomeId);
                GameObject obj = b.getRandomObject();
                if (obj != GameObjects.NO_OBJECT && obj != null) {
                    locations[i][j].setObjectInstance(new ObjectInstance(obj.getId(), obj.getUses()));
                } else {
                    locations[i][j].setObjectInstance(null);
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
                        if (inBounds(new PointI(x, y + i))) {
                            locations[y + i][x].setBiomeId(Biomes.WATER.getBiomeId());
                        }
                    }
                }
                case 1, 3 -> { // south, north
                    for (int i = 0; i < width; i++) {
                        if (inBounds(new PointI(x - i, y))) {
                            locations[y][x - i].setBiomeId(Biomes.WATER.getBiomeId());
                        }
                    }
                }
                case 2 -> { // west
                    for (int i = 0; i < width; i++) {
                        if (inBounds(new PointI(x, y - i))) {
                            locations[y - i][x].setBiomeId(Biomes.WATER.getBiomeId());
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

    public boolean inBounds(PointI loc) {
        return loc.inBounds(0, 0, Const.WORLD_SIZE, Const.WORLD_SIZE);
    }

    public boolean getBlocking(int x, int y) {
        ObjectInstance obj = locations[y][x].getObjectInstance();
        if (obj != null && GameObject.OBJECTS_BY_ID.get(obj.getObjectId()).isBlocking()) {
            return true;
        }
        return Tiles.TILES_BY_ID.get(locations[y][x].getTileId()).isBlocking();
    }

    public void setWorldUpdateHandler(WorldUpdateHandler worldUpdateHandler) {
        this.worldUpdateHandler = worldUpdateHandler;
    }

    public void playerUpdated() {
        if (this.worldUpdateHandler != null) {
            this.worldUpdateHandler.playerUpdated();
        }
    }

    public void worldUpdated() {
        if(this.worldUpdateHandler != null) {
            this.worldUpdateHandler.worldUpdated();
        }
    }

    public void putObject(PointI loc, ObjectInstance obj) {
        ObjectInstance existingObj = locations[loc.getY()][loc.getX()].getObjectInstance();
        if (existingObj == null) {
            locations[loc.getY()][loc.getX()].setObjectInstance(obj);
        }
    }

    public double getVariantAt(PointI loc) {
        return variantMap[loc.getY()][loc.getX()];
    }

    public void removeAllMobs() {
        this.mobs.clear();
    }
}
