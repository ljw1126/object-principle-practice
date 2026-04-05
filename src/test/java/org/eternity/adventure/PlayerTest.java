package org.eternity.adventure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
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

        assertThat(player.canMove(Direction.EAST)).isTrue();

        player.move(Direction.EAST);
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

            assertThat(player.canMove(Direction.WEST)).isFalse();
            assertThatIllegalArgumentException().isThrownBy(() -> player.move(Direction.WEST));
    }

    @Test
    public void can_not_move_outside() {
            WorldMap worldMap = new WorldMap(
                    Size.with(2, 2),
                    new Room(Position.of(0, 0), "", ""),
                    new Room(Position.of(1, 0), "", ""),
                    new Room(Position.of(1, 1), "", ""));
            Player player = new Player(worldMap, Position.of(1, 1));

            assertThat(player.canMove(Direction.EAST)).isFalse(); // (2,1) Room 없음
            assertThatIllegalArgumentException().isThrownBy(() -> player.move(Direction.EAST));
    }
}
