package org.eternity.adventure;

import static org.assertj.core.api.Assertions.assertThat;
import org.eternity.adventure.game.world.Transfer;
import org.eternity.adventure.game.world.item.Inventory;
import org.eternity.adventure.game.world.item.Item;
import org.eternity.adventure.game.world.worldmap.Position;
import org.eternity.adventure.game.world.worldmap.Room;
import org.junit.jupiter.api.Test;

public class TransferTest {
    @Test
    public void transfer_is_possible() {
        Room source = new Room(Position.of(0,0), "", "", new Inventory(new Item("item")));
        Room target = new Room(Position.of(0,0), "", "", new Inventory(new Item("item")));

        Transfer transfer = new Transfer(source, target, "item");

        assertThat(transfer.isPossible()).isTrue();
    }

    @Test
    public void transfer() {
        Room source = new Room(Position.of(0,0), "", "", new Inventory(new Item("item")));
        Room target = new Room(Position.of(0,0), "", "", new Inventory(new Item("item")));

        Transfer transfer = new Transfer(source, target, "item");
        transfer.perform();

        assertThat(source.find("item")).isEmpty();
        assertThat(target.find("item")).isPresent();
    }
}
