package com.graphics_2d.util;

abstract public class Point<T> {
    private final T x;
    private final T y;

    public Point(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public abstract Point<T> delta(T dx, T dy);

    public T getX() {
        return x;
    }

    public T getY() {
        return y;
    }
}
