package com.graphics_2d.world;

import java.util.Map;
import java.util.Set;

public class UseEffect {
    private final Set<Integer> triggerObjects;
    private final Map<Integer, Integer> resultObjects;
    private final int usesBeforeDestroy; // 0 is infinite

    public UseEffect(Set<Integer> triggerObjects, Map<Integer, Integer> resultObjects, int usesBeforeDestroy) {
        this.triggerObjects = triggerObjects;
        this.resultObjects = resultObjects;
        this.usesBeforeDestroy = usesBeforeDestroy;
    }

    public boolean isTriggerObject(Integer objectId) {
        return triggerObjects.contains(objectId);
    }

    public Map<Integer, Integer> use() {
        return resultObjects;
    }

    public int getUsesBeforeDestroy() {
        return usesBeforeDestroy;
    }
}
