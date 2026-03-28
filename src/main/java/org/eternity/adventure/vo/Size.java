package org.eternity.adventure.vo;

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
        return position.x() >= 0 && position.x() < width
                && position.y() >= 0 && position.y() < height;
    }

    public int indexOf(Position position) {
        return position.x() + position.y() * width;
    }
}
