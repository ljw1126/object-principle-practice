package org.eternity.adventure.game.item;

import java.util.List;
import java.util.Optional;

public interface Carrier {
    List<Item> items();
    Optional<Item> find(String itemName);
    void add(Item item);
    void remove(Item item);
}
