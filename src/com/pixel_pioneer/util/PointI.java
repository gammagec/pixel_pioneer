package com.pixel_pioneer.util;

import java.util.ArrayList;
import java.util.List;

public class PointI extends Point<Integer> {
    public PointI(Integer x, Integer y) {
        super(x, y);
    }

    @Override
    public PointI delta(Integer dx, Integer dy) {
        return new PointI(getX() + dx, getY() + dy);
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

    public PointI bound(int minX, int minY, int maxX, int maxY) {
        int newX = Math.max(minX, getX());
        int newY = Math.max(minY, getY());
        newX = Math.min(newX, maxX - 1);
        newY = Math.min(newY, maxY - 1);
        return new PointI(newX, newY);
    }

    public boolean inBounds(int mx, int my, int width, int height) {
        return getX() >= mx && getY() >= my && getX() < width && getY() < height;
    }
}
