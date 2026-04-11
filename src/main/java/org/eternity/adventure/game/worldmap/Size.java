package org.eternity.adventure.game.worldmap;

import java.util.Random;

public class Size {
    private final int width;
    private final int height;

    private Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static Size with(int width, int height) {
        return new Size(width, height);
    }

    public int area() {
        return width * height;
    }

    public boolean contains(Position position) {
        return position.isInside(width, height);
    }

    public int indexOf(Position position) {
        return position.toIndex(width);
    }

    public Position anyPosition() {
        Random random = new Random();
        return Position.of(random.nextInt(width), random.nextInt(height));
    }
}
