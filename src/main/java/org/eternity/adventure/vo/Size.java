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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int area() {
        return width * height;
    }

    public boolean contains(Position position) {
        return position.getY() >= 0 
            && position.getY() < height 
            && position.getX() < width
            && position.getX() >= 0;
    }

    public int indexOf(Position position) {
        return position.getX() + position.getY() * width;
    }
}
