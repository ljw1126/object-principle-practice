package org.eternity.adventure.game.item;

import java.util.List;

public interface Carrier extends Source, Target {
    List<Item> items();
    boolean hasItems();
}
