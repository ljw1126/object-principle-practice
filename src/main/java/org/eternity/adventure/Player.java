package org.eternity.adventure;

import org.eternity.adventure.constant.Direction;
import org.eternity.adventure.vo.Position;

public class Player {
    private WorldMap worldMap;
    private Position position;

    public Player(WorldMap worldMap, Position position) {
        this.worldMap = worldMap;
        this.position = position;
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

    private Room currentRoom() {
        return worldMap.roomAt(position);
    }

    public String currentRoomName() {
        return currentRoom().name();
    }

    public String currentRoomDescription() {
        return currentRoom().description(); 
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((worldMap == null) ? 0 : worldMap.hashCode());
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Player other = (Player) obj;
        if (worldMap == null) {
            if (other.worldMap != null)
                return false;
        } else if (!worldMap.equals(other.worldMap))
            return false;
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        return true;
    }   

    
}
