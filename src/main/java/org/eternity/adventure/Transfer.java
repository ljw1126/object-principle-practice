package org.eternity.adventure;

import org.eternity.adventure.game.item.Source;
import org.eternity.adventure.game.item.Target;

public class Transfer {
    private Source source;
    private Target target;
    private String itemName;

    public Transfer(Source source, Target target, String itemName) {
        this.source = source;
        this.target = target;
        this.itemName = itemName;
    }

    public boolean isPossible() {
        return source.find(itemName).isPresent();
    }

    public boolean transfer() {
        return source.transferTo(itemName, target);
    }
    
    public void perform() {
        transfer();
    }
}
