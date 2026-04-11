package org.eternity.adventure.game.worldmap;

import java.util.List;
import java.util.Optional;
import org.eternity.adventure.game.item.Carrier;
import org.eternity.adventure.game.item.Item;

public class WorldMap implements Carrier{
    private Size size;
    private Room[] rooms;
    
    public WorldMap(Size size, Room... rooms) {
        this.size = size;
        this.rooms = new Room[size.area()];
        for(Room room : rooms) {
            this.rooms[size.indexOf(room.position())] = room;
        }
    }

    public boolean isBlocked(Position position) {
        return isExcluded(position) 
            || roomAt(position) == null;
    }

    public boolean isExcluded(Position position) {
        return !size.contains(position);
    }

    public Room roomAt(Position position) {
        return rooms[size.indexOf(position)];
    }

    @Override
    public List<Item> items() {
        return List.of();
    }

    @Override
    public Optional<Item> find(String itemName) {
        return Optional.empty();
    }

    // 랜덤하게 선택된 위치에 아이템 추가
    @Override
    public void add(Item item) {
        Position position = size.anyPosition();
        if(isBlocked(position)) {
            return;
        }

        roomAt(position).add(item);
    }

    @Override
    public void remove(Item item) {
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public boolean hasItems() {
        throw new UnsupportedOperationException("Unimplemented method 'hasItems'");
    }
}
