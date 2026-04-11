package org.eternity.adventure.game.item;

import java.util.List;

public interface Carrier extends Source, Target {
    List<Item> items();
    boolean hasItems();

    default boolean transferTo(String itemName, Carrier target) {
        return find(itemName).map(item -> {
            remove(item);
            target.add(item);
            return true;
        }).orElse(false);
    }
}
