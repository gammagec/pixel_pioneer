package com.graphics_2d.world;

import java.util.List;
import java.util.Map;

public class Recipe {
    private Map<Integer, Integer> requiredObjects;
    private int outputObjectId;
    private int amount;

    public Recipe(Map<Integer, Integer> requiredObjects, int outputObjectId, int amount) {
        this.requiredObjects = requiredObjects;
        this.outputObjectId = outputObjectId;
        this.amount = amount;
    }

    public int getOutputObjectId() {
        return outputObjectId;
    }

    public int getAmount() {
        return amount;
    }

    public Map<Integer, Integer> getRequiredObjects() {
        return requiredObjects;
    }
}
