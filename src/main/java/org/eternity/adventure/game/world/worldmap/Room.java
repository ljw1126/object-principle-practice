package org.eternity.adventure.game.world.worldmap;

import org.eternity.adventure.game.world.item.Carrier;
import org.eternity.adventure.game.world.item.ForwardingCarrier;
import org.eternity.adventure.game.world.item.Inventory;
import org.eternity.adventure.game.world.item.ItemFormatter;

public class Room extends ForwardingCarrier {
    private String name;
    private String description;
    private Position position;

    public Room(Position position, String name, String description) {
        this(position, name, description, new Inventory());
    }

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

    public String itemsDescription() {
        return ItemFormatter.format("아이템", items());
    }
}
