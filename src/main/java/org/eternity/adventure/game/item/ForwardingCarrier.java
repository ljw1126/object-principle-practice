package org.eternity.adventure.game.item;

import java.util.List;
import java.util.Optional;

public abstract class ForwardingCarrier implements Carrier {
    private Carrier carrier;

    protected ForwardingCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    @Override
    public List<Item> items() {
        return carrier.items();
    }

    @Override
    public void add(Item item) {
        carrier.add(item);
    }

    @Override
    public Optional<Item> find(String itemName) {
        return carrier.find(itemName);
    }

    @Override
    public void remove(Item item) {
        carrier.remove(item);
    }

    @Override
    public boolean hasItems() {
        return carrier.hasItems();
    }

    @Override
    public boolean transferTo(String itemName, Carrier target) {
        return carrier.transferTo(itemName, target);
    }
}
