package org.eternity.adventure;

import java.util.Random;
import org.eternity.adventure.game.item.Carrier;
import org.eternity.adventure.game.item.Item;

public class Destroy {
    private Carrier first;
    private Carrier second;
    private String itemName;

    public Destroy(Carrier first, Carrier second, String itemName) {
        this.first = first;
        this.second = second;
        this.itemName = itemName;
    }

    public boolean isPossible() {
        return contains(first) || contains(second);
    }

    private boolean contains(Carrier carrier) {
        return carrier.find(itemName).isPresent();
    }

    public void perform() {
        if(!isPossible()) {
            throw new IllegalStateException();
        }

        if(contains(first) && contains(second)) {
            if(new Random().nextInt(2) == 0){
                first.remove(new Item(itemName));
            } else {
                second.remove(new Item(itemName));
            }
        }

        if(contains(first)) {
            first.remove(new Item(itemName));
            return;
        }

        if(contains(second)) {
            second.remove(new Item(itemName));
        }
    }

}
