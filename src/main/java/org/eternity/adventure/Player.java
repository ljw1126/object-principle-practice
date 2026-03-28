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

    public void tryMove(Direction direction) {
        Position nexPosition = position.shift(direction);
        if(worldMap.isBlocked(nexPosition)) {
            showBlocked();
        } else {
            this.position = nexPosition;
            showRoom();
        }
    }

    private void showBlocked() {
        System.out.println("이동할 수 없습니다.");
    }

    public void showRoom() {
        var room = worldMap.roomAt(position);
        System.out.println("당신은 [" + room.name() + "]에 있습니다.");
        System.out.println(room.description());
    }

}
