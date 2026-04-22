package org.eternity.adventure.game.world.worldmap;

import org.eternity.adventure.game.world.item.Item;
import org.eternity.adventure.game.world.item.Target;

public class WorldMap implements Target{
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
        return at(position).isBlocked();
    }

    public boolean isExcluded(Position position) {
        return !size.contains(position);
    }

    public Room roomAt(Position position) {
        return rooms[size.indexOf(position)];
    }

    // 랜덤하게 선택된 위치에 아이템 추가
    @Override
    public void add(Item item) {
        at(size.anyPosition()).add(item);
    }

    public Location at(Position position) {
        if (isExcluded(position)) {
            return new Location(null);
        }
        return new Location(roomAt(position));
    }

    public record Location(Room room) {
        public boolean isBlocked() {
            return room == null;
        }

        public void add(Item item) {
            if (!isBlocked()) {
                room.add(item);
            }
        }
    }
    }

