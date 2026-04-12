package org.eternity.adventure.game.command;

import static org.assertj.core.api.Assertions.assertThat;
import org.eternity.adventure.game.world.FakeInputOutput;
import org.eternity.adventure.game.world.worldmap.Direction;
import org.junit.jupiter.api.Test;

public class CommandParserTest {
    @Test
    public void go() {
        CommandParser parser = new CommandParser(new FakeInputOutput());
        assertThat(parser.parseCommand("go north")).isEqualTo(new Command.Move(Direction.NORTH));
        assertThat(parser.parseCommand("go south")).isEqualTo(new Command.Move(Direction.SOUTH));
        assertThat(parser.parseCommand("go east")).isEqualTo(new Command.Move(Direction.EAST));
        assertThat(parser.parseCommand("go west")).isEqualTo(new Command.Move(Direction.WEST));
    }

    @Test
    public void go_north() {
        CommandParser parser = new CommandParser(new FakeInputOutput());
        assertThat(parser.parseCommand("go north")).isEqualTo(new Command.Move(Direction.NORTH));
        assertThat(parser.parseCommand("Go North")).isEqualTo(new Command.Move(Direction.NORTH));
        assertThat(parser.parseCommand("go     north")).isEqualTo(new Command.Move(Direction.NORTH));
        assertThat(parser.parseCommand("  go     north  ")).isEqualTo(new Command.Move(Direction.NORTH));
    }

    @Test
    public void look() {
        CommandParser parser = new CommandParser(new FakeInputOutput());
        assertThat(parser.parseCommand("look")).isEqualTo(new Command.Look());
    }

    @Test
    public void stop() {
        CommandParser parser = new CommandParser(new FakeInputOutput());
        assertThat(parser.parseCommand("quit")).isEqualTo(new Command.Quit());
    }

    @Test
    public void help() {
        CommandParser parser = new CommandParser(new FakeInputOutput());
        assertThat(parser.parseCommand("help")).isEqualTo(new Command.Help());
    }

    @Test
    public void go_without_direction() {
        CommandParser parser = new CommandParser(new FakeInputOutput());
        assertThat(parser.parseCommand("go")).isEqualTo(new Command.Unknown());
    }

    @Test
    public void unknown() {
        CommandParser parser = new CommandParser(new FakeInputOutput());
        assertThat(parser.parseCommand("hello")).isEqualTo(new Command.Unknown());
    }
}
