package org.eternity.adventure;

import static org.assertj.core.api.Assertions.assertThat;
import org.eternity.adventure.vo.Position;
import org.eternity.adventure.vo.Size;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    
    @Test
    public void move() {
        WorldMap worldMap = new WorldMap(
                Size.with(2, 2),
                new Room(Position.of(0, 0), "(0,0)", "방 (0,0)"),
                new Room(Position.of(1, 0), "(1,0)", "방 (1,0)"),
                new Room(Position.of(1, 1), "(1,1)", "방 (1,1)"));
        Player player = new Player(worldMap, Position.of(0, 0));

        player.move(Position.of(1, 0));
        assertThat(player.position()).isEqualTo(Position.of(1,0));
    }
    
}
