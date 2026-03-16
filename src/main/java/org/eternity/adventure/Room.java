package org.eternity.adventure;

import org.eternity.adventure.vo.Position;

public class Room {
    private Position position;
    private String name;
    private String description;

    public Room(int x, int y, String name, String description) {
        this.position = Position.of(x, y);
        this.name = name;
        this.description = description;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public Position position() {
        return position;
    }
}
