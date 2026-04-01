package org.eternity.adventure;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import org.eternity.adventure.console.Console;
import org.junit.jupiter.api.Test;

public class GameTest {
    @Test
    public void contains_welcome() {
        OutputStream output = new ByteArrayOutputStream();
        
        Game game = createGame(output, "quit\n");
        game.run();

        assertThat(output.toString().split("\n")).containsSequence(
                "환영합니다!",
                "당신은 [언덕]에 있습니다.",
                "저 멀리 성이 보이고 언덕 아래로 좁은 길이 나 있습니다.",
                "다음 명령어를 사용할 수 있습니다.",
                "go {north|east|south|west} - 이동, look - 보기, help - 도움말, quit - 게임 종료");
    }

    @Test
    public void move_north_passed() {
        OutputStream output = new ByteArrayOutputStream();
        
        Game game = createGame(output, "go north\nquit\n");
        game.run();

        assertThat(output.toString().split("\n")).containsSequence(
                "> 당신은 [다리]에 있습니다.",
                "큰 강 위에 돌로 만든 커다란 다리가 있습니다.",
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void move_north_blocked() {
        OutputStream output = new ByteArrayOutputStream();
        
        Game game = createGame(output, "go north\ngo north\ngo north\nquit\n");
        game.run();

        assertThat(output.toString().split("\n")).containsSequence(
                "> 당신은 [다리]에 있습니다.",
                "큰 강 위에 돌로 만든 커다란 다리가 있습니다.",
                "> 당신은 [샘]에 있습니다.",
                "아름다운 샘물이 흐르는 곳입니다. 이곳에서 휴식을 취할 수 있습니다.",
                "> 이동할 수 없습니다.",
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void move_east_passed() {
        OutputStream output = new ByteArrayOutputStream();
        
        Game game = createGame(output, "go east\nquit\n");
        game.run();

        assertThat(output.toString().split("\n")).containsSequence(
                "> 당신은 [동굴]에 있습니다.",
                "어둠에 잠긴 동굴 안에 작은 화톳불이 피어 있습니다.",
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void move_east_blocked() {
        OutputStream output = new ByteArrayOutputStream();
        
        Game game = createGame(output, "go east\ngo east\nquit\n");
        game.run();

        assertThat(output.toString().split("\n")).containsSequence(
                "> 당신은 [동굴]에 있습니다.",
                "어둠에 잠긴 동굴 안에 작은 화톳불이 피어 있습니다.",
                "> 이동할 수 없습니다.",
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void move_south_passed() {
        OutputStream output = new ByteArrayOutputStream();
        
        Game game = createGame(output, "go north\ngo south\nquit\n");
        game.run();

        assertThat(output.toString().split("\n")).containsSequence(
                "> 당신은 [다리]에 있습니다.",
                "큰 강 위에 돌로 만든 커다란 다리가 있습니다.",
                "> 당신은 [언덕]에 있습니다.",
                "저 멀리 성이 보이고 언덕 아래로 좁은 길이 나 있습니다.",
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void move_south_blocked() {
        OutputStream output = new ByteArrayOutputStream();
        
        Game game = createGame(output, "go south\nquit\n");
        game.run();

        assertThat(output.toString().split("\n")).containsSequence(
                "> 이동할 수 없습니다.",
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void move_west_passed() {
        OutputStream output = new ByteArrayOutputStream();
        
        Game game = createGame(output, "go east\ngo west\nquit\n");
        game.run();

        assertThat(output.toString().split("\n")).containsSequence(
                "> 당신은 [동굴]에 있습니다.",
                "어둠에 잠긴 동굴 안에 작은 화톳불이 피어 있습니다.",
                "> 당신은 [언덕]에 있습니다.",
                "저 멀리 성이 보이고 언덕 아래로 좁은 길이 나 있습니다.",
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void move_west_blocked() {
        OutputStream output = new ByteArrayOutputStream();

        Game game = createGame(output, "go west\nquit\n");
        game.run();

        assertThat(output.toString().split("\n")).containsSequence(
                "> 이동할 수 없습니다.",
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void move_empty() {
        OutputStream output = new ByteArrayOutputStream();

        Game game = createGame(output, "go north\ngo north\ngo east\nquit\n");
        game.run();

        assertThat(output.toString().split("\n")).containsSequence(
                "> 당신은 [다리]에 있습니다.",
                "큰 강 위에 돌로 만든 커다란 다리가 있습니다.",
                "> 당신은 [샘]에 있습니다.",
                "아름다운 샘물이 흐르는 곳입니다. 이곳에서 휴식을 취할 수 있습니다.",
                "> 이동할 수 없습니다.",
                "> ",
                "게임을 종료합니다.");
    }

    private Game createGame(OutputStream output, String input) {
        System.setOut(new PrintStream(output));
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        return new Game(new Console());
    }
}
