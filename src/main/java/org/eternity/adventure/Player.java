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

    public boolean move(Direction direction) {
        Position nextPosition = position.shift(direction);
        if(worldMap.isBlocked(nextPosition)) {
            return false;
        }

        this.position = nextPosition;
        return true;
    }
    
    public Position position() {
        return position;
    }

    public Room currentRoom() {
        return worldMap.roomAt(position);
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
