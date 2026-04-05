package org.eternity.adventure;

import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import org.eternity.adventure.constant.Direction;
import org.eternity.adventure.vo.Position;
import org.junit.jupiter.api.Test;

public class RoomTest {
    @Test
    void upcast_error() {
        Player player = new Room(Position.of(0, 0), "(0,0)", "(0,0) 설명", new Item("key"));

        assertThatNullPointerException()
            .isThrownBy(() -> player.move(Direction.EAST));
    }
}
