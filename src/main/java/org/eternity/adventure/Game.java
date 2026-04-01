package org.eternity.adventure;

import java.util.Scanner;
import org.eternity.adventure.constant.Direction;

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
        System.out.println("환영합니다!");
    }

    private void showHelp() {
        System.out.println("다음 명령어를 사용할 수 있습니다.");
        System.out.println("go {north|east|south|west} - 이동, look - 보기, help - 도움말, quit - 게임 종료");
    }

    private void play() {
        Scanner scanner = new Scanner(System.in);
        
        start();
        
        while (isRunning()) {
            String input = inputCommand(scanner);
            Command command = commandParser.parseCommand(input);
            executeCommand(command);
        }
    }

    private String inputCommand(Scanner scanner) {
        showPrompt();
        return input(scanner);
    }

    private String input(Scanner scanner) {
        return scanner.nextLine();
    }

    private void executeCommand(Command command) {
        switch (command) {
            case Command.Move move -> tryMove(move.direction());
            case Command.Look() -> showRoom();
            case Command.Help() -> showHelp();
            case Command.Quit() -> stop();
            case Command.Unknown() -> showUnknownCommand();
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
    }

}
