package com.pixel_pioneer.world.entities;

import com.pixel_pioneer.Const;
import com.pixel_pioneer.world.*;

import java.util.*;

public class Player extends Entity {

    private int health = Const.MAX_HEALTH;
    private int hunger = Const.MAX_HUNGER;

    private int thirst = Const.MAX_THIRST;

    private int stamina = Const.MAX_STAMINA;

    private int building = 0;
    private boolean flying = false;

    private final ObjectInstance[][] inventory = new ObjectInstance[Const.INVENTORY_HEIGHT][Const.INVENTORY_WIDTH];

    public Player() {
        inventory[0][0] = new ObjectInstance(GameObjects.BASIC_SWORD.getId(), 1);
        inventory[0][1] = new ObjectInstance(GameObjects.BASIC_AXE.getId(), 1);
        inventory[0][2] = new ObjectInstance(GameObjects.BASIC_PICK_AXE.getId(), 1);
    }

    public void reset(World world) {
        health = Const.MAX_HEALTH;
        stamina = Const.MAX_STAMINA;
        thirst = Const.MAX_THIRST;
        hunger = Const.MAX_HUNGER;
        setLocation(world.randomSpawnPoint());
    }

    public void takeDamage(int damage) {
        health = health - damage;
        if (health <= 0) {
            health = 0;
            building = 0;
        }
    }

    public Integer getObjectCount(Integer objId) {
        int count = 0;
        for (int y = 0; y < Const.INVENTORY_HEIGHT; y++) {
            for (int x = 0; x < Const.INVENTORY_WIDTH; x++) {
                ObjectInstance obj = inventory[y][x];
                if (obj != null && obj.getObjectId() == objId) {
                   count += obj.getCount();
                }
            }
        }
        return count;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getBuildingIndex() {
        return building;
    }

    public void setBuildingIndex(int buildingIndex) {
        building = buildingIndex;
    }

    public int getHealth() {
        return health;
    }

    public boolean isFlying() {
        return flying;
    }

    public int getHunger() {
        return hunger;
    }

    public int getThirst() {
        return thirst;
    }

    public int getStamina() {
        return stamina;
    }

    public boolean isExhausted() {
        return stamina == 0;
    }

    public boolean isStaminaFull() {
        return stamina == Const.MAX_STAMINA;
    }

    public void takeStamina(int amt) {
        stamina -= amt;
    }

    public void giveObject(ObjectInstance object) {
        boolean foundFirst = false;
        int sx = 0;
        int sy = 0;
        for (int y = 0; y < Const.INVENTORY_HEIGHT; y++) {
            for (int x = 0; x < Const.INVENTORY_WIDTH; x++) {
                ObjectInstance obj = inventory[y][x];
                if (obj != null) {
                    if (obj.same(object)) {
                        obj.addInstances(object.getCount());
                        return;
                    }
                } else if (!foundFirst){
                    foundFirst = true;
                    sx = x;
                    sy = y;
                }
            }
        }
        if (foundFirst) { // if not, inv full
            inventory[sy][sx] = object;
        }
    }

    public Integer getBuildingObjectIndex() {
        if (building > 0 && building < Const.INVENTORY_WIDTH + 1) {
            return inventory[0][building - 1].getObjectId();
        } else {
            return null;
        }
    }

    public void removeObject(int objIndex) {
        for (int y = 0; y < Const.INVENTORY_HEIGHT; y++) {
            for (int x = 0; x < Const.INVENTORY_WIDTH; x++) {
                ObjectInstance obj = inventory[y][x];
                if (obj != null && obj.getObjectId() == objIndex) {
                    if (obj.getCount() > 1) {
                        obj.reduceCount(1);
                    } else {
                        inventory[y][x] = null;
                    }
                }
            }
        }
    }

    public Integer getObjectIdFromHotbarIndex(int hotbarIndex) {
        if (hotbarIndex < Const.INVENTORY_WIDTH + 1 && hotbarIndex > 0) {
            ObjectInstance obj = inventory[0][hotbarIndex - 1];
            if (obj != null) {
                return obj.getObjectId();
            }
        }
        return null;
    }

    public void eatObject(GameObject obj) {
        if (health < Const.MAX_HEALTH ) {
            health++;
        }
    }

    public void toggleFlying() {
        flying = !flying;
    }

    public void regenStamina(int i) {
        stamina += i;
        if (stamina > Const.MAX_STAMINA) {
            stamina = Const.MAX_STAMINA;
        }
    }

    public void craftRecipe(Recipe selectedRecipe) {
        for (Map.Entry<Integer, Integer> e : selectedRecipe.getRequiredObjects().entrySet()) {
            Integer count = getObjectCount(e.getKey());
            if (count != null && count < e.getValue()) {
                return;
            }
        }
        for (Map.Entry<Integer, Integer> e : selectedRecipe.getRequiredObjects().entrySet()) {
            for (int i = 0; i < e.getValue(); i++) {
                removeObject(e.getKey());
            }
        }
        int objId = selectedRecipe.getOutputObjectId();
        GameObject gObj = GameObject.OBJECTS_BY_ID.get(objId);
        ObjectInstance objectInstance = new ObjectInstance(objId, gObj.getUses());
        giveObject(objectInstance);
    }

    public ObjectInstance getInventoryAt(int x, int y) {
        return inventory[y][x];
    }

    public void setInventoryAt(int x, int y, ObjectInstance o) {
        inventory[y][x] = o;
    }
}
