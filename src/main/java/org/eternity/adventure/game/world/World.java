package org.eternity.adventure.game.world;

import org.eternity.adventure.game.Output;
import org.eternity.adventure.game.world.item.Source;
import org.eternity.adventure.game.world.item.Target;
import org.eternity.adventure.game.world.player.Player;
import org.eternity.adventure.game.world.worldmap.Direction;

public class World {
    private Player player;
    private Output output;

    public World(Player player, Output output) {
        this.player = player;
        this.output = output;
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
        output.showLine("당신은 [" + player.currentRoomName() + "]에 있습니다.");
        output.showLine(player.currentRoomDescription());
        if(player.currentRoomHasItems()) {
            output.showLine(player.currentRoomItemsDescription());
        }
    }

    private void showBlocked() {
        output.showLine("이동할 수 없습니다.");
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
            output.showLine(successMessage);
            return;
        } 

        output.showLine(failureMessage);
    }

    // TODO. rename
    public void destoryItem(String itemName) {
        Destroy destroy = new Destroy(player, player.currentRoom(), itemName);

        if(destroy.isPossible()) {
            destroy.perform();
            output.showLine(itemName + "이(가) 파괴되었습니다.");
            return;
        }

        output.showLine(itemName + "을(를) 파괴할 수 없습니다.");
    }

    public void showInventory() {
        output.showLine(player.inventoryDescription());
    }
}
