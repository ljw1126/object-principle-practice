package org.eternity.adventure.game.world.item;

import java.util.List;
import java.util.stream.Collectors;

public class ItemFormatter {
    public static String format(String label, List<Item> items) {
        if(items.isEmpty()) return "";

        return items.stream()
                .map(Item::name)
                .collect(Collectors.joining(", ", label + ": [", "]"));
    }
}
