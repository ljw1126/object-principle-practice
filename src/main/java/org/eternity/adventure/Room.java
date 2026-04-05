package org.eternity.adventure;

import org.eternity.adventure.vo.Position;

public class Room extends Player {
    private String name;
    private String description;

    public Room(Position position, String name, String description, Item... items) {
        super(null, position, items);
        this.name = name;
        this.description = description;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }
}
