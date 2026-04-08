package org.eternity.adventure.game.command;

import org.eternity.adventure.game.worldmap.Direction;

public class CommandParser {
    public Command parseCommand(String input) {
        return parseCommand(split(input));
    }

    private String[] split(String input) {
        return input.toLowerCase().trim().split("\\s+");
    }

    private Command parseCommand(String[] commands) {
        if (commands.length == 0 || commands[0].isEmpty()) {
            return new Command.Unknown();
        }

        return switch (commands[0]) {
            case "go" -> {
                if (commands.length < 2) {
                    yield new Command.Unknown();
                }
                yield switch (commands[1]) {
                    case "north" -> new Command.Move(Direction.NORTH);
                    case "south" -> new Command.Move(Direction.SOUTH);
                    case "east" -> new Command.Move(Direction.EAST);
                    case "west" -> new Command.Move(Direction.WEST);
                    default -> new Command.Unknown();
                };
            }
            case "inventory" -> new Command.Inventory();
            case "take" -> commands.length > 1 ? new Command.Take(commands[1]) : new Command.Unknown();
            case "drop" -> commands.length > 1 ? new Command.Drop(commands[1]) : new Command.Unknown();
            case "look" -> new Command.Look();
            case "help" -> new Command.Help();
            case "quit" -> new Command.Quit();
            default -> new Command.Unknown();
        };
    }
}
