package org.eternity.adventure.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Inventory implements Carrier {
    private final List<Item> items = new ArrayList<>();

    public Inventory(Item... items) {
        this.items.addAll(List.of(items));
    }

    @Override
    public List<Item> items() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public Optional<Item> find(String itemName) {
        return items.stream()
            .filter(item -> item.name().equalsIgnoreCase(itemName))
            .findFirst();
    }

    @Override
    public void add(Item item) {
        items.add(item);
    }

    @Override
    public void remove(Item item) {
        items.remove(item);
    }
    
}
