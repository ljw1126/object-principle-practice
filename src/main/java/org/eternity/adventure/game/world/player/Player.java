package org.eternity.adventure.game.world.player;

import org.eternity.adventure.game.world.item.Carrier;
import org.eternity.adventure.game.world.item.ForwardingCarrier;
import org.eternity.adventure.game.world.item.Inventory;
import org.eternity.adventure.game.world.item.ItemFormatter;
import org.eternity.adventure.game.world.item.Target;
import org.eternity.adventure.game.world.worldmap.Direction;
import org.eternity.adventure.game.world.worldmap.Position;
import org.eternity.adventure.game.world.worldmap.Room;
import org.eternity.adventure.game.world.worldmap.WorldMap;

public class Player extends ForwardingCarrier {
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

    public boolean currentRoomHasItems() {
        return currentRoom().hasItems();
    }

    // 플레이어에게 현재 방의 아이템 설명을 물어봄 (Ask가 아닌 Tell)
    public String currentRoomItemsDescription() {
        return currentRoom().itemsDescription();
    }

    // 플레이어에게 현재 인벤토리 목록 설명을 물어봄
    // "자신을 설명하는 문자열을 반환"할 뿐, UI 관련 인터페이스에 의존하지 않는다.
    public String inventoryDescription() { 
        return ItemFormatter.format("인벤토리 목록", items());
    }

    public Target worldMap() {
        return worldMap;
    }
}
