package org.eternity.adventure;

import org.eternity.adventure.console.Console;

public class Main {
    public static void main(String[] args) {
        Game game = new Game(new Console());
        game.run();
    }
}
