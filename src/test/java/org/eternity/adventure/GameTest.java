package org.eternity.adventure;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameTest {
    @Test
    void move_north_passed() {
        // given
        OutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        System.setIn(new ByteArrayInputStream("go north\nquit\n".getBytes()));
    
        // when
        Game game = new Game();
        game.run();
        
        // then
        Assertions.assertThat(output.toString().split("\n"))
            .containsSequence(
                "> 당신은 [다리]에 있습니다.",
                "큰 강 위에 돌로 만든 커다란 다리가 있습니다.",
                "> ",
                "게임을 종료합니다."
            );
    }
}
