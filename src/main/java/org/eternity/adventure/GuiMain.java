package org.eternity.adventure;

import org.eternity.adventure.game.command.CommandParser;
import org.eternity.adventure.game.world.World;
import org.eternity.adventure.game.world.item.Inventory;
import org.eternity.adventure.game.world.item.Item;
import org.eternity.adventure.game.world.player.Player;
import org.eternity.adventure.game.world.worldmap.Position;
import org.eternity.adventure.game.world.worldmap.Room;
import org.eternity.adventure.game.world.worldmap.Size;
import org.eternity.adventure.game.world.worldmap.WorldMap;

public class GuiMain {
    public static void main(String[] args) {
        GuiGame game = new GuiGame();
        CommandParser commandParser = new CommandParser(game);
        World world = new World(
                new Player(
                    new WorldMap(
                            Size.with(2, 3),
                            new Room(Position.of(0, 0), "샘", "아름다운 샘물이 흐르는 곳입니다. 이곳에서 휴식을 취할 수 있습니다."),
                            new Room(Position.of(0, 1), "다리", "큰 강 위에 돌로 만든 커다란 다리가 있습니다.", new Inventory(new Item("sword"))),
                            new Room(Position.of(1, 1), "성", "용왕이 살고 있는 성에 도착했습니다.", new Inventory(new Item("potion"), new Item("key"))),
                            new Room(Position.of(0, 2), "언덕", "저 멀리 성이 보이고 언덕 아래로 좁은 길이 나 있습니다."),
                            new Room(Position.of(1, 2), "동굴", "어둠에 잠긴 동굴 안에 작은 화톳불이 피어 있습니다.", new Inventory(new Item("gem")))),
                    Position.of(0, 2)),
                game);

        game.initialize(world, commandParser);
        game.run();
    }    
}
