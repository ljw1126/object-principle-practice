package org.eternity.adventure;

import static org.assertj.core.api.Assertions.assertThat;
import org.eternity.adventure.game.command.CommandParser;
import org.eternity.adventure.game.world.FakeInputOutput;
import org.eternity.adventure.game.world.World;
import org.eternity.adventure.game.world.item.Inventory;
import org.eternity.adventure.game.world.item.Item;
import org.eternity.adventure.game.world.player.Player;
import org.eternity.adventure.game.world.worldmap.Position;
import org.eternity.adventure.game.world.worldmap.Room;
import org.eternity.adventure.game.world.worldmap.Size;
import org.eternity.adventure.game.world.worldmap.WorldMap;
import org.junit.jupiter.api.Test;

public class GameTest {
    @Test
    public void contains_welcome() {
        FakeInputOutput io = new FakeInputOutput("quit");
        
        CuiGame game = createGame(io);
        game.run();

        assertThat(io.outputs()).containsSequence(
                "환영합니다!",
                "당신은 [언덕]에 있습니다.",
                "저 멀리 성이 보이고 언덕 아래로 좁은 길이 나 있습니다.",
                "다음 명령어를 사용할 수 있습니다.",
                "go {north|east|south|west} - 이동, look - 보기, inventory - 인벤토리, take {item} - 줍기, drop {item} - 버리기, destroy {item} - 파괴하기, throw {item} - 던지기, help - 도움말, quit - 게임 종료");
    }

    @Test
    public void move_north_passed() {
        FakeInputOutput io = new FakeInputOutput("go north", "quit");
        
        CuiGame game = createGame(io) ;
        game.run();

        assertThat(io.outputs()).containsSequence(
                "> 당신은 [다리]에 있습니다.",
                "큰 강 위에 돌로 만든 커다란 다리가 있습니다.",
                "아이템: [sword]",
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void move_north_blocked() {
        FakeInputOutput io = new FakeInputOutput("go north", "go north", "go north", "quit");
        
        CuiGame game = createGame(io);
        game.run();

        assertThat(io.outputs()).containsSequence(
                "> 당신은 [다리]에 있습니다.",
                "큰 강 위에 돌로 만든 커다란 다리가 있습니다.",
                "아이템: [sword]",
                "> 당신은 [샘]에 있습니다.",
                "아름다운 샘물이 흐르는 곳입니다. 이곳에서 휴식을 취할 수 있습니다.",
                "> 이동할 수 없습니다.",
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void move_east_passed() {
        FakeInputOutput io = new FakeInputOutput("go east", "quit");
        
        CuiGame game = createGame(io);
        game.run();

        assertThat(io.outputs()).containsSequence(
                "> 당신은 [동굴]에 있습니다.",
                "어둠에 잠긴 동굴 안에 작은 화톳불이 피어 있습니다.",
                "아이템: [gem]",
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void move_east_blocked() {
        FakeInputOutput io = new FakeInputOutput("go east", "go east", "quit");
        
        CuiGame game = createGame(io);
        game.run();

        assertThat(io.outputs()).containsSequence(
                "> 당신은 [동굴]에 있습니다.",
                "어둠에 잠긴 동굴 안에 작은 화톳불이 피어 있습니다.",
                "아이템: [gem]",
                "> 이동할 수 없습니다.",
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void move_south_passed() {
        FakeInputOutput io = new FakeInputOutput("go north", "go south", "quit");
        
        CuiGame game = createGame(io);
        game.run();

        assertThat(io.outputs()).containsSequence(
                "> 당신은 [다리]에 있습니다.",
                "큰 강 위에 돌로 만든 커다란 다리가 있습니다.",
                "아이템: [sword]",
                "> 당신은 [언덕]에 있습니다.",
                "저 멀리 성이 보이고 언덕 아래로 좁은 길이 나 있습니다.",
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void move_south_blocked() {
        FakeInputOutput io = new FakeInputOutput("go south", "quit");
        
        CuiGame game = createGame(io);
        game.run();

        assertThat(io.outputs()).containsSequence(
                "> 이동할 수 없습니다.",
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void move_west_passed() {
        FakeInputOutput io = new FakeInputOutput("go east", "go west", "quit");
        
        CuiGame game = createGame(io);
        game.run();

        assertThat(io.outputs()).containsSequence(
                "> 당신은 [동굴]에 있습니다.",
                "어둠에 잠긴 동굴 안에 작은 화톳불이 피어 있습니다.",
                "아이템: [gem]",
                "> 당신은 [언덕]에 있습니다.",
                "저 멀리 성이 보이고 언덕 아래로 좁은 길이 나 있습니다.",
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void move_west_blocked() {
        FakeInputOutput io = new FakeInputOutput("go west", "quit");

        CuiGame game = createGame(io);
        game.run();

        assertThat(io.outputs()).containsSequence(
                "> 이동할 수 없습니다.",
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void move_empty() {
        FakeInputOutput io = new FakeInputOutput("go north", "go north", "go east", "quit");

        CuiGame game = createGame(io);
        game.run();

        assertThat(io.outputs()).containsSequence(
                "> 당신은 [다리]에 있습니다.",
                "큰 강 위에 돌로 만든 커다란 다리가 있습니다.",
                "아이템: [sword]",
                "> 당신은 [샘]에 있습니다.",
                "아름다운 샘물이 흐르는 곳입니다. 이곳에서 휴식을 취할 수 있습니다.",
                "> 이동할 수 없습니다.",
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void look_command() {
        FakeInputOutput io = new FakeInputOutput("look", "quit");

        CuiGame game = createGame(io);
        game.run();

        assertThat(io.outputs()).containsSequence(
                "> 당신은 [언덕]에 있습니다.",
                "저 멀리 성이 보이고 언덕 아래로 좁은 길이 나 있습니다.",
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void help_command() {
        FakeInputOutput io = new FakeInputOutput("help", "quit");

        CuiGame game = createGame(io);
        game.run();

        assertThat(io.outputs()).containsSequence(
                "> 다음 명령어를 사용할 수 있습니다.",
                "go {north|east|south|west} - 이동, look - 보기, inventory - 인벤토리, take {item} - 줍기, drop {item} - 버리기, destroy {item} - 파괴하기, throw {item} - 던지기, help - 도움말, quit - 게임 종료",
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void inventory_command() {
        FakeInputOutput io = new FakeInputOutput("inventory", "quit");

        CuiGame game = createGame(io);
        game.run();

        assertThat(io.outputs()).containsSequence(
                "> 인벤토리 목록: [key]",
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void take_item_success() {
        FakeInputOutput io = new FakeInputOutput("go east", "take gem", "inventory", "quit");

        CuiGame game = createGame(io);
        game.run();

        assertThat(io.outputs()).containsSequence(
                "> 당신은 [동굴]에 있습니다.",
                "어둠에 잠긴 동굴 안에 작은 화톳불이 피어 있습니다.",
                "아이템: [gem]",
                "> gem을(를) 얻었습니다.",
                "> 인벤토리 목록: [key, gem]",
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void take_item_failure() {
        FakeInputOutput io = new FakeInputOutput("take sword", "quit");

        CuiGame game = createGame(io);
        game.run();

        assertThat(io.outputs()).containsSequence(
                "> sword을(를) 얻을 수 없습니다.",
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void drop_item_success() {
        FakeInputOutput io = new FakeInputOutput("drop key", "look", "inventory", "quit");

        CuiGame game = createGame(io);
        game.run();

        assertThat(io.outputs()).containsSequence(
                "> key을(를) 버렸습니다.",
                "> 당신은 [언덕]에 있습니다.",
                "저 멀리 성이 보이고 언덕 아래로 좁은 길이 나 있습니다.",
                "아이템: [key]",
                "> ", // 인벤토리 목록 empty string
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void drop_item_failure() {
        FakeInputOutput io = new FakeInputOutput("drop sword", "quit");

        CuiGame game = createGame(io);
        game.run();

        assertThat(io.outputs()).containsSequence(
                "> sword을(를) 버릴 수 없습니다.",
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void destroy_item_success() {
        FakeInputOutput io = new FakeInputOutput("destory key", "inventory", "quit");

        CuiGame game = createGame(io);
        game.run();

        assertThat(io.outputs()).containsSequence(
                "> key이(가) 파괴되었습니다.",
                "> ", // 인벤토리 목록 empty string
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void destroy_item_failure() {
        FakeInputOutput io = new FakeInputOutput("destory sword", "quit");

        CuiGame game = createGame(io);
        game.run();

        assertThat(io.outputs()).containsSequence(
                "> sword을(를) 파괴할 수 없습니다.",
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void throw_item_success() {
        FakeInputOutput io = new FakeInputOutput("throw key", "inventory", "quit");

        CuiGame game = createGame(io);
        game.run();

        assertThat(io.outputs()).containsSequence(
                "> key을(를) 어딘가로 던졌습니다.",
                "> ", // 인벤토리 목록 empty string
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void throw_item_failure() {
        FakeInputOutput io = new FakeInputOutput("throw sword", "quit");

        CuiGame game = createGame(io);
        game.run();

        assertThat(io.outputs()).containsSequence(
                "> sword을(를) 던질 수 없습니다.",
                "> ",
                "게임을 종료합니다.");
    }

    @Test
    public void unknown_command() {
        FakeInputOutput io = new FakeInputOutput("dance", "quit");

        CuiGame game = createGame(io);
        game.run();

        assertThat(io.outputs()).containsSequence(
                "> 이해할 수 없는 명령어입니다.",
                "> ",
                "게임을 종료합니다.");
    }

    private CuiGame createGame(FakeInputOutput io) {
        Player player = new Player(
            new WorldMap(
                Size.with(2, 3), 
                new Room(Position.of(0, 0), "샘", "아름다운 샘물이 흐르는 곳입니다. 이곳에서 휴식을 취할 수 있습니다."),
                new Room(Position.of(0, 1), "다리", "큰 강 위에 돌로 만든 커다란 다리가 있습니다.", new Inventory(new Item("sword"))),
                new Room(Position.of(1, 1), "성", "용왕이 살고 있는 성에 도착했습니다.", new Inventory(new Item("potion"), new Item("key"))),
                new Room(Position.of(0, 2), "언덕", "저 멀리 성이 보이고 언덕 아래로 좁은 길이 나 있습니다."),
                new Room(Position.of(1, 2), "동굴", "어둠에 잠긴 동굴 안에 작은 화톳불이 피어 있습니다.", new Inventory(new Item("gem")))
            ), 
            Position.of(0, 2), 
            new Inventory(new Item("key")));

        World world = new World(player, io);
        CommandParser commandParser = new CommandParser(io);
        
        return new CuiGame(world, commandParser, io);
    }
}
