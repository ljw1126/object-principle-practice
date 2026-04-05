package org.eternity.adventure.game.player;

import org.eternity.adventure.game.item.Carrier;
import org.eternity.adventure.game.item.ForwardingCarrier;
import org.eternity.adventure.game.item.Inventory;
import org.eternity.adventure.game.worldmap.Direction;
import org.eternity.adventure.game.worldmap.Position;
import org.eternity.adventure.game.worldmap.Room;
import org.eternity.adventure.game.worldmap.WorldMap;

public class Player extends ForwardingCarrier{
    private WorldMap worldMap;
    private Position position;

    public Player(WorldMap worldMap, Position position) {
        this(worldMap, position, new Inventory());
    }

    public Player(WorldMap worldMap, Position position, Carrier carrier) {
         super(carrier);
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
