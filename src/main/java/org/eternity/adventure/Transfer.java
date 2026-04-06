package org.eternity.adventure;

import org.eternity.adventure.game.item.Carrier;

public class Transfer {
    private Carrier source;
    private Carrier target;
    private String itemName;

    public Transfer(Carrier source, Carrier target, String itemName) {
        this.source = source;
        this.target = target;
        this.itemName = itemName;
    }

    public boolean isPossible() {
        return source.find(itemName).isPresent();
    }

    public boolean transfer() {
        return source.find(itemName).map(
            item -> {
                source.remove(item);
                target.add(item);
                return true;
        }).orElse(false);
    }
    
    public void perform() {
        transfer();
    }
}
