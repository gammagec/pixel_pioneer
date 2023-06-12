package com.graphics_2d.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PointI extends Point<Integer> {
    public PointI(Integer x, Integer y) {
        super(x, y);
    }


    PointI[] getNeighbors(int x, int y) {
        List<PointI> neighbors = new ArrayList<>() {{
            add(new PointI(x - 1, y)); // w
            add(new PointI(x - 1, y - 1)); // nw
            add(new PointI(x, y - 1)); // n
            add(new PointI(x + 1, y - 1)); // ne
            add(new PointI(x + 1, y)); // e
            add(new PointI(x + 1, y + 1)); // se
            add(new PointI(x, y + 1)); // s
            add(new PointI(x - 1, y + 1)); // sw
        }};
        return neighbors.toArray(PointI[]::new);
    }
}
