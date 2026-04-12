package org.eternity.adventure.console;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

public class ConsoleTest {
    @Test
    void input() {
        // given
        System.setIn(new ByteArrayInputStream("input\n".getBytes()));
        
        // when
        String command = new Console().input();
        
        // then
        assertThat(command).isEqualTo("input");
    }

    @Test
    void showLine() {
        // given
        OutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        
        // when
        new Console().showLine("showLine");
        
        // then
        assertThat(output.toString()).isEqualToIgnoringNewLines("showLine");
    }

    @Test
    void show() {
        // given
        OutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        
        // when
        new Console().show("show");
        
        // then
        assertThat(output.toString()).isEqualToIgnoringNewLines("show");
    }

}
