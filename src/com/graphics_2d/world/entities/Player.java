package com.graphics_2d.world.entities;

import com.graphics_2d.Const;
import com.graphics_2d.world.GameObject;
import com.graphics_2d.world.Recipe;
import com.graphics_2d.world.World;

import java.util.HashMap;
import java.util.Map;

public class Player extends Entity {

    private int health = Const.MAX_HEALTH;
    private int hunger = Const.MAX_HUNGER;

    private int thirst = Const.MAX_THIRST;

    private int stamina = Const.MAX_STAMINA;

    private int building = 0;
    private boolean eating = false;
    private boolean flying = false;
    private final Map<Integer, Integer> objects = new HashMap<>();

    public Player() {
    }

    public void reset(World world) {
        health = Const.MAX_HEALTH;
        stamina = Const.MAX_STAMINA;
        thirst = Const.MAX_THIRST;
        hunger = Const.MAX_HUNGER;
        setLocation(world.randomSpawnPoint());
    }

    public Integer[] getObjects() {
        return objects.keySet().toArray(new Integer[0]);
    }

    public void takeDamage(int damage) {
        health = health - damage;
        if (health < 0) {
            health = 0;
        }
    }

    public Integer getObjectCount(Integer objId) {
        return objects.get(objId);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getBuildingIndex() {
        return building - 1;
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

    public void giveObject(GameObject object) {
        int index = object.getId();
        if (objects.containsKey(index)) {
            int amt = objects.get(index);
            objects.put(index, amt + 1);
        } else {
            objects.put(index, 1);
        }
    }

    public Integer getBuildingObjectIndex() {
        if (building > 0) {
            return (Integer) objects.keySet().toArray()[building - 1];
        } else {
            return null;
        }
    }

    public void removeObject(int objIndex) {
        if (objects.containsKey(objIndex)) {
            int amt = objects.get(objIndex);
            if (amt > 1) {
                objects.put(objIndex, amt - 1);
            } else {
                objects.remove(objIndex);
            }
        }
    }

    public Integer getObjectIdFromHotbarIndex(int hotbarIndex) {
        if (hotbarIndex <= objects.keySet().size() && hotbarIndex > 0) {
            return (Integer) objects.keySet().toArray()[hotbarIndex - 1];
        } else {
            return null;
        }
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
            Integer count = objects.get(e.getKey());
            if (count != null && count < e.getValue()) {
                return;
            }
        }
        for (Map.Entry<Integer, Integer> e : selectedRecipe.getRequiredObjects().entrySet()) {
            Integer count = objects.get(e.getKey());
            if (count > e.getValue()) {
                objects.put(e.getKey(), count - e.getValue());
            } else {
                objects.remove(e.getKey());
            }
        }
        if (objects.containsKey(selectedRecipe.getOutputObjectId())) {
            objects.put(
                    selectedRecipe.getOutputObjectId(),
                    objects.get(selectedRecipe.getOutputObjectId()) + selectedRecipe.getAmount());
        } else {
            objects.put(selectedRecipe.getOutputObjectId(), selectedRecipe.getAmount());
        }
    }
}
