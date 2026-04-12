package org.eternity.adventure.console;

import java.util.Scanner;
import org.eternity.adventure.game.Input;
import org.eternity.adventure.game.Output;

public class Console implements Input, Output {
    private Scanner scanner;

    public Console() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String input() {
        return scanner.nextLine();
    }

    @Override
    public void showLine(String text) {
        System.out.println(text);
    }

    @Override
    public void show(String text) {
        System.out.print(text);
    }
   
}
