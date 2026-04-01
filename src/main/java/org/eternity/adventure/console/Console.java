package org.eternity.adventure.console;

import java.util.Scanner;
import org.eternity.adventure.InputOutput;

public class Console implements InputOutput {
    private Scanner scanner;

    public Console() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String input() {
        return scanner.nextLine().toLowerCase().trim();
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
