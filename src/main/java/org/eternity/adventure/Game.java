package org.eternity.adventure;

import org.eternity.adventure.game.command.Command;
import org.eternity.adventure.game.command.CommandParser;
import org.eternity.adventure.game.item.Source;
import org.eternity.adventure.game.item.Target;
import org.eternity.adventure.game.player.Player;
import org.eternity.adventure.game.worldmap.Direction;

public class Game {
    private Player player;
    private CommandParser commandParser;
    private boolean running;
    private InputOutput io;

    public Game(Player player, CommandParser commandParser, InputOutput io) {
        this.player = player;
        this.commandParser = commandParser;
        this.io = io;
    }

    public void run() {
        welcome();
        play();
        farewell();
    }

    private void welcome() {
        showGreetings();
        showRoom();
        showHelp();
    }

    private void showGreetings() {
        io.showLine("환영합니다!");
    }

    private void showHelp() {
        io.showLine("다음 명령어를 사용할 수 있습니다.");
        io.showLine("go {north|east|south|west} - 이동, look - 보기, help - 도움말, quit - 게임 종료");
    }

    private void play() {
        start();
        
        while (isRunning()) {
            String input = inputCommand();
            Command command = commandParser.parseCommand(input);
            executeCommand(command);
        }
    }

    private String inputCommand() {
        showPrompt();
        return io.input();
    }

    private void executeCommand(Command command) {
        switch (command) {
            case Command.Move move -> tryMove(move.direction());
            case Command.Look() -> showRoom();
            case Command.Help() -> showHelp();
            case Command.Quit() -> stop();
            case Command.Unknown() -> showUnknownCommand();
            case Command.Inventory() -> showInventory();
            case Command.Take take -> takeItem(take.item()); // item()은 Command.Take record의 필드입니다.
            case Command.Drop drop -> dropItem(drop.item());
            case Command.Destory destory -> destoryItem(destory.item());
            case Command.Throw throwCommand -> throwItem(throwCommand.item());
        }
    }

    public void tryMove(Direction direction) {
        if(player.canMove(direction)) {
            player.move(direction);
            showRoom();
            return;
        } 

         showBlocked();
    }

    private void showPrompt() {
        io.show("> ");
    }

    private void showUnknownCommand() {
        io.showLine("이해할 수 없는 명령어입니다.");
    }

    private void start() {
        this.running = true;
    }

    private boolean isRunning() {
        return this.running;
    }

    private void stop() {
        this.running = false;
    }

    private void farewell() {
        io.showLine("\n게임을 종료합니다.");
    }

    public void showBlocked() {
        io.showLine("이동할 수 없습니다.");
    }

    public void showRoom() {
        io.showLine("당신은 [" + player.currentRoomName() + "]에 있습니다.");
        io.showLine(player.currentRoomDescription());
        if(player.currentRoomHasItems()) {
            io.showLine(player.currentRoomItemsDescription());
        }
    }

    private void showInventory() {
        io.showLine(player.inventoryDescription());
    }

    private void takeItem(String itemName) {
        transfer(player.currentRoom(), 
                 player, 
                 itemName, 
                 itemName + "을(를) 얻었습니다.", 
                 itemName + "을(를) 얻을 수 없습니다.");
    }

    private void dropItem(String itemName) {
        transfer(player, 
                player.currentRoom(), 
                itemName, 
                itemName + "을(를) 버렸습니다.", 
                itemName + "을(를) 버릴 수 없습니다.");
    }

    private void throwItem(String itemName) {
        transfer(player, 
                player.worldMap(), 
                itemName, 
                itemName + "을(를) 어딘가로 던졌습니다.",
                itemName + "을(를) 던질 수 없습니다.");
    }

    private void transfer(Source source, Target target, 
        String itemName, String successMessage, String failureMessage) {
        if (new Transfer(source, target, itemName).transfer()) {
            io.showLine(successMessage);
            return;
        } 

        io.showLine(failureMessage);
    }

    private void destoryItem(String itemName) {
        Destroy destroy = new Destroy(player, player.currentRoom(), itemName);

        if(destroy.isPossible()) {
            destroy.perform();
            io.showLine(itemName + "이(가) 파괴되었습니다.");
            return;
        }

        io.showLine(itemName + "을(를) 파괴할 수 없습니다.");
    }
}
