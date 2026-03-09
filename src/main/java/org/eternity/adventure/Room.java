package org.eternity.adventure;

public class Room {
     private int x, y;
    private String name;
    private String description;

    public Room(int x, int y, String name, String description) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.description = description;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }
}
