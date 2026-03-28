package org.eternity.adventure;

import org.eternity.adventure.vo.Position;
import org.eternity.adventure.vo.Size;

public class WorldMap {
    private Size size;
    private Room[] rooms;
    
    public WorldMap(Size size, Room... rooms) {
        this.size = size;
        this.rooms = new Room[size.area()];
        for(Room room : rooms) {
            this.rooms[room.indexIn(size)] = room;
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
}
