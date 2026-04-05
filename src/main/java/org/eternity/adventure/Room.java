package org.eternity.adventure;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.eternity.adventure.item.Carrier;
import org.eternity.adventure.item.Item;
import org.eternity.adventure.vo.Position;

public class Room implements Carrier {
    private String name;
    private String description;
    private Position position;
    private List<Item> items;

    public Room(Position position, String name, String description, Item... items) {
        this.position = position;
        this.name = name;
        this.description = description;
        this.items = List.of(items);
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

    @Override
    public List<Item> items() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public void add(Item item) {
        items.add(item);
    }

    @Override
    public Optional<Item> find(String itemName) {
        return items.stream()
            .filter(item -> item.name().equalsIgnoreCase(itemName))
            .findFirst();
    }

    @Override
    public void remove(Item item) {
        items.remove(item);
    }
}
