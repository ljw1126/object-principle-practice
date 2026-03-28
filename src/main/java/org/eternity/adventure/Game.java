package org.eternity.adventure;

import java.util.Scanner;
import org.eternity.adventure.vo.Position;
import org.eternity.adventure.vo.Size;

public class Game {
    private Player player;
    private CommandParser commandParser;
    private boolean running;
    
    public Game() {
        this.player = new Player(
            new WorldMap(
                Size.with(2, 3), 
                new Room(Position.of(0, 0), "샘", "아름다운 샘물이 흐르는 곳입니다. 이곳에서 휴식을 취할 수 있습니다."),
                new Room(Position.of(0, 1), "다리", "큰 강 위에 돌로 만든 커다란 다리가 있습니다."),
                new Room(Position.of(1, 1), "성", "용왕이 살고 있는 성에 도착했습니다."),
                new Room(Position.of(0, 2), "언덕", "저 멀리 성이 보이고 언덕 아래로 좁은 길이 나 있습니다."),
                new Room(Position.of(1, 2), "동굴", "어둠에 잠긴 동굴 안에 작은 화톳불이 피어 있습니다.")
            ), 
            Position.of(0, 2));
        this.commandParser = new CommandParser();
    }

    public void run() {
        welcome();
        play();
        farewell();
    }

    private void welcome() {
        showGreetings();
        player.showRoom();
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
            case Command.Move move -> player.tryMove(move.direction());
            case Command.Look() -> player.showRoom();
            case Command.Help() -> showHelp();
            case Command.Quit() -> stop();
            case Command.Unknown() -> showUnknownCommand();
        }
    }

    private void showPrompt() {
        System.out.print("> ");
    }

    private void showUnknownCommand() {
        System.out.println("이해할 수 없는 명령어입니다.");
    }

    private void start() {
        this.running = true;
    }

    private boolean isRunning() {
        return this.running == true;
    }

    private void stop() {
        this.running = false;
    }

    private void farewell() {
        System.out.println("\n게임을 종료합니다.");
    }
}
