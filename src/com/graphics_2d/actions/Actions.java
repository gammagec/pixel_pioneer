package com.graphics_2d.actions;

import com.graphics_2d.Const;
import com.graphics_2d.sound.SoundEngine;
import com.graphics_2d.ui.Hud;
import com.graphics_2d.ui.Inventory;
import com.graphics_2d.world.GameObject;
import com.graphics_2d.world.GameObjects;
import com.graphics_2d.world.Tile;
import com.graphics_2d.world.World;
import com.graphics_2d.world.entities.Player;

public class Actions {

    private final Player player;
    private final World world;
    private final Hud hud;
    private final Inventory inventory;
    private final SoundEngine soundEngine;

    public Actions(World world, Hud hud, Inventory inventory, SoundEngine soundEngine) {
        this.world = world;
        player = world.getPlayer();
        this.hud = hud;
        this.inventory = inventory;
        this.soundEngine = soundEngine;
    }

    public void onEat() {
        System.out.println("Eating");
        player.setEating(true);
    }

    public void onGenerateBiomes() {
        world.generateBiomes();
        world.generateMap();
        world.worldUpdated();
    }

    public void onGrowBiomes() {
        world.growBiomes();
        world.generateMap();
        world.worldUpdated();
    }

    public void onFly() {
        player.toggleFlying();
        world.worldUpdated();
    }

    public void onReset() {
        player.reset();
        hud.update();
        world.worldUpdated();
        soundEngine.playNextSong();
    }

    public void onHotBar(int index) {
        if (player.isEating()) {
            player.setEating(false);
            Integer objId = player.getObjectIndexFromHotbarIndex(index);
            System.out.println("hotbar obj " + objId + " at " + index);
            if (objId != null) {
                GameObject obj = GameObjects.OBJECTS_BY_ID.get(objId);
                if (obj.isCanEat()) {
                    player.eatObject(obj);
                    player.removeObject(obj.getId());
                    hud.update();
                    world.worldUpdated();
                }
            }
        } else {
            player.setBuildingIndex(index);
            hud.update();
            world.worldUpdated();
        }
    }

    public void onUp() {
        moveBuildOrDrop(0, -1);
    }

    public void onDown() {
        moveBuildOrDrop(0, 1);
    }

    public void onLeft() {
        moveBuildOrDrop(-1, 0);
    }

    public void onRight() {
        moveBuildOrDrop(1, 0);
    }

    private void move(int dx, int dy) {
        int px = player.getX();
        int py = player.getY();
        if(canMoveHere(px + dx, py + dy) && player.isAlive()) {
            player.setLocation(px + dx, py + dy);
            afterMove();
        }
    }

    private void buildOrDrop(int dx, int dy) {
        int px = player.getX();
        int py = player.getY();
        if (canMoveHere(px + dx, py + dy)) {
            maybePutObject(px + dx, py + dy);
        }
    }

    private void maybePutObject(int x, int y) {
        Player player = world.getPlayer();
        if (world.getObjectAt(x, y) == null) {
            Integer objIndex = player.getBuildingObjectIndex();
            if (objIndex != null) {
                player.removeObject(objIndex);
                world.putObject(x, y, objIndex);
            }
        }
        player.setBuildingIndex(0);
        player.setEating(false);
        hud.update();
        world.worldUpdated();
    }

    private void moveBuildOrDrop(int dx, int dy) {
        if (player.isBuilding()) {
            buildOrDrop(dx, dy);
        } else {
            move(dx, dy);
        }
    }

    void afterMove() {
        boolean needsHudUpdate = false;
        boolean needsInventoryUpdate = false;
        int px = player.getX();
        int py = player.getY();
        Tile tile = world.getTileAt(px, py);
        int damage = 0;
        if (!player.isFlying()) {
            damage += tile.getDamage();
        }
        if (player.isExhausted() && tile.isSwim() && !player.isFlying()) {
            damage += 1;
        }
        if (tile.isSwim() && !player.isExhausted() && !player.isFlying()) {
            player.takeStamina(1);
            needsHudUpdate = true;
        }
        GameObject obj = world.getObjectAt(px, py);
        if (obj != null && !player.isFlying()) {
            damage += obj.getDamage();
            if (obj.isCanPickup()) {
                world.pickupObject(px, py);
                needsInventoryUpdate = true;
                needsHudUpdate = true;
            }
        }
        if (damage > 0) {
            needsHudUpdate = true;
            player.takeDamage(damage);
            if (!player.isAlive()) {
                soundEngine.playDeadSong();
            } else {
                soundEngine.playOw();
            }
        } else if (!tile.isSwim() && !player.isStaminaFull()) {
            player.regenStamina(1);
            needsHudUpdate = true;
        }
        if (needsHudUpdate) {
            hud.update();
        }
        if (needsInventoryUpdate) {
            inventory.update();
        }
        player.setEating(false);
        world.worldUpdated();
    }

    boolean canMoveHere(int x, int y) {
        if (x < 0) {
            return false;
        }
        if (y < 0) {
            return false;
        }
        if (x > Const.WORLD_SIZE) {
            return false;
        }
        if (y > Const.WORLD_SIZE) {
            return false;
        }
        return !this.world.getBlocking(x, y) || player.isFlying();
    }

    public void inventory() {
        inventory.toggleOpen();
        world.worldUpdated();
    }
}
