package org.eternity.adventure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.eternity.adventure.constant.Direction;
import org.eternity.adventure.item.Carrier;
import org.eternity.adventure.item.Item;
import org.eternity.adventure.vo.Position;

public class Player implements Carrier{
    private WorldMap worldMap;
    private Position position;
    private List<Item> items = new ArrayList<>();

    public Player(WorldMap worldMap, Position position, Item... items) {
        this.worldMap = worldMap;
        this.position = position;
        this.items.addAll(List.of(items));
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

    public void move(Direction direction) {
        if(!canMove(direction)) {
            throw new IllegalArgumentException();
        }

        this.position = position.shift(direction);
    }

    public boolean canMove(Direction direction) {
        Position nextPosition = position.shift(direction);
        return !worldMap.isBlocked(nextPosition);
    }

    public Position position() {
        return position;
    }

    public Room currentRoom() {
        return worldMap.roomAt(position);
    }

    public String currentRoomName() {
        return currentRoom().name();
    }

    public String currentRoomDescription() {
        return currentRoom().description(); 
    }
}
