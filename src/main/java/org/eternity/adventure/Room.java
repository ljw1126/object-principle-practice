package org.eternity.adventure;

import org.eternity.adventure.item.Carrier;
import org.eternity.adventure.item.ForwardingCarrier;
import org.eternity.adventure.vo.Position;

public class Room extends ForwardingCarrier {
    private String name;
    private String description;
    private Position position;

    public Room(Position position, String name, String description, Carrier carrier) {
        super(carrier);
        this.position = position;
        this.name = name;
        this.description = description;
    }

    public Position position() {
        return position;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }
}
