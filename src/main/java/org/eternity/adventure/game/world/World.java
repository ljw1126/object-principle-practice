package org.eternity.adventure.game.world;

import org.eternity.adventure.InputOutput;
import org.eternity.adventure.game.world.item.Source;
import org.eternity.adventure.game.world.item.Target;
import org.eternity.adventure.game.world.player.Player;
import org.eternity.adventure.game.world.worldmap.Direction;

public class World {
    private Player player;
    private InputOutput io;

    public World(Player player, InputOutput io) {
        this.player = player;
        this.io = io;
    }

    public void tryMove(Direction direction) {
        if(player.canMove(direction)) {
            player.move(direction);
            showRoom();
            return;
        } 

         showBlocked();
    }

    public void showRoom() {
        io.showLine("당신은 [" + player.currentRoomName() + "]에 있습니다.");
        io.showLine(player.currentRoomDescription());
        if(player.currentRoomHasItems()) {
            io.showLine(player.currentRoomItemsDescription());
        }
    }

    private void showBlocked() {
        io.showLine("이동할 수 없습니다.");
    }

    public void takeItem(String itemName) {
        transfer(player.currentRoom(), 
                 player, 
                 itemName, 
                 itemName + "을(를) 얻었습니다.", 
                 itemName + "을(를) 얻을 수 없습니다.");
    }

    public void dropItem(String itemName) {
        transfer(player, 
                player.currentRoom(), 
                itemName, 
                itemName + "을(를) 버렸습니다.", 
                itemName + "을(를) 버릴 수 없습니다.");
    }

    public void throwItem(String itemName) {
        transfer(player, 
                player.worldMap(), 
                itemName, 
                itemName + "을(를) 어딘가로 던졌습니다.",
                itemName + "을(를) 던질 수 없습니다.");
    }

    public void transfer(Source source, Target target, 
        String itemName, String successMessage, String failureMessage) {
        if (new Transfer(source, target, itemName).transfer()) {
            io.showLine(successMessage);
            return;
        } 

        io.showLine(failureMessage);
    }

    public void destoryItem(String itemName) {
        Destroy destroy = new Destroy(player, player.currentRoom(), itemName);

        if(destroy.isPossible()) {
            destroy.perform();
            io.showLine(itemName + "이(가) 파괴되었습니다.");
            return;
        }

        io.showLine(itemName + "을(를) 파괴할 수 없습니다.");
    }

    public void showInventory() {
        io.showLine(player.inventoryDescription());
    }
}
