package org.eternity.adventure;

import org.eternity.adventure.game.command.Command;
import org.eternity.adventure.game.command.CommandParser;
import org.eternity.adventure.game.world.World;

public class Game {
    private World world;
    private CommandParser commandParser;
    private boolean running;
    private InputOutput io;

    public Game(World world, CommandParser commandParser, InputOutput io) {
        this.world = world;
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
        world.showRoom();
        showHelp();
    }

    private void showGreetings() {
        io.showLine("환영합니다!");
    }

    private void showHelp() {
        io.showLine(commandParser.help());
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
            case Command.Move move -> world.tryMove(move.direction());
            case Command.Look() -> world.showRoom();
            case Command.Help() -> showHelp();
            case Command.Quit() -> stop();
            case Command.Unknown() -> showUnknownCommand();
            case Command.Inventory() -> world.showInventory();
            case Command.Take take -> world.takeItem(take.item()); // item()은 Command.Take record의 필드입니다.
            case Command.Drop drop -> world.dropItem(drop.item());
            case Command.Destory destory -> world.destoryItem(destory.item());
            case Command.Throw throwCommand -> world.throwItem(throwCommand.item());
        }
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
}
