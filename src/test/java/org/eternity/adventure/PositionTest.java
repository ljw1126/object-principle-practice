package org.eternity.adventure;

import static org.assertj.core.api.Assertions.assertThat;
import org.eternity.adventure.constant.Direction;
import org.eternity.adventure.vo.Position;
import org.junit.jupiter.api.Test;

public class PositionTest {
    @Test
    public void shift() {
        assertThat(Position.of(1, 1).shift(Direction.NORTH)).isEqualTo(Position.of(1, 0));
        assertThat(Position.of(1, 1).shift(Direction.EAST)).isEqualTo(Position.of(2, 1));
        assertThat(Position.of(1, 1).shift(Direction.SOUTH)).isEqualTo(Position.of(1, 2));
        assertThat(Position.of(1, 1).shift(Direction.WEST)).isEqualTo(Position.of(0, 1));
    }
}
