package org.eternity.adventure;

import static org.assertj.core.api.Assertions.assertThat;
import org.eternity.adventure.constant.Direction;
import org.eternity.adventure.vo.Position;
import org.eternity.adventure.vo.Size;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    
    @Test
    public void move_east() {
        WorldMap worldMap = new WorldMap(
                Size.with(2, 2),
                new Room(Position.of(0, 0), "", ""),
                new Room(Position.of(1, 0), "", ""),
                new Room(Position.of(1, 1), "", ""));
        Player player = new Player(worldMap, Position.of(0, 0));

        assertThat(player.move(Direction.EAST)).isTrue();
        assertThat(player.position()).isEqualTo(Position.of(1, 0));
    }

    @Test
    public void can_not_move_west() {
        WorldMap worldMap = new WorldMap(
                Size.with(2, 2),
                new Room(Position.of(0, 0), "", ""),
                new Room(Position.of(1, 0), "", ""),
                new Room(Position.of(1, 1), "", ""));
        Player player = new Player(worldMap, Position.of(1, 1));

        assertThat(player.move(Direction.WEST)).isFalse();
        assertThat(player.position()).isEqualTo(Position.of(1, 1));
    }

    @Test
    public void can_not_move_outside() {
        WorldMap worldMap = new WorldMap(
                Size.with(2, 2),
                new Room(Position.of(0, 0), "", ""),
                new Room(Position.of(1, 0), "", ""),
                new Room(Position.of(1, 1), "", ""));
        Player player = new Player(worldMap, Position.of(1, 1));

        assertThat(player.move(Direction.EAST)).isFalse();
        assertThat(player.position()).isEqualTo(Position.of(1, 1));
    }

    @Test
    public void move_throws_exception() {
        WorldMap worldMap = new WorldMap(
                Size.with(2, 2),
                new Room(Position.of(0, 0), "", ""),
                new Room(Position.of(1, 0), "", ""),
                new Room(Position.of(1, 1), "", ""));
        Player player = new Player(worldMap, Position.of(1, 1));

        assertThat(player.move(Direction.EAST)).isFalse();
    }

    @Test
    public void move_passed() {
        WorldMap worldMap = new WorldMap(
                Size.with(2, 2),
                new Room(Position.of(0, 0), "", ""),
                new Room(Position.of(1, 0), "", ""),
                new Room(Position.of(1, 1), "", ""));
        Player player = new Player(worldMap, Position.of(0, 0));

        player.move(Direction.EAST);

        assertThat(player.position()).isEqualTo(Position.of(1, 0));
    }
}
