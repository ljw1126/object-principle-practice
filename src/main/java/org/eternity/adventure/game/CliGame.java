package org.eternity.adventure.game;

public class CliGame implements GameLoop{
    private Game game;
    private Input input;
    private Output output;
    private boolean running;
    
    public CliGame(Game game, Input input, Output output) {
        this.game = game;
        this.input = input;
        this.output = output;
    }

    public void run() {
        game.run();
    }

    @Override
    public void play() {
        start();
        
        while (isRunning()) {
            game.executeCommand(inputCommand());
        }
    }

    private String inputCommand() {
        showPrompt();
        return input();
    }

    private void showPrompt() {
        output.show("> ");
    }

    private String input() {
        return input.input();
    }

    @Override
    public void stop() {
        this.running = false;
    }

    private void start() {
        this.running = true;
    }

    private boolean isRunning() {
        return this.running;
    }
}
