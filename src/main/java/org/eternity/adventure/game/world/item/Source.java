package org.eternity.adventure.game.world.item;

import java.util.Optional;

public interface Source {
    Optional<Item> find(String itemName);
    void remove(Item item);

    default boolean transferTo(String itemName, Target target) {
        return find(itemName).map(item -> {
            remove(item);
            target.add(item);
            return true;
        }).orElse(false);
    }
} 
