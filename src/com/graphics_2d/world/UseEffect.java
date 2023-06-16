package com.graphics_2d.world;

import java.util.Map;
import java.util.Set;

public class UseEffect {
    private final Set<Integer> triggerObjects;
    private final Map<Integer, Integer> resultObjects;
    private final int usesConsumed;

    public UseEffect(Set<Integer> triggerObjects, Map<Integer, Integer> resultObjects, int usesConsumed) {
        this.triggerObjects = triggerObjects;
        this.resultObjects = resultObjects;
        this.usesConsumed = usesConsumed;
    }

    public boolean isTriggerObject(Integer objectId) {
        return triggerObjects.contains(objectId);
    }

    public int getUsesConsumed() {
        return usesConsumed;
    }

    public Map<Integer, Integer> use() {
        return resultObjects;
    }
}
