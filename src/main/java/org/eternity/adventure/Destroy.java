package org.eternity.adventure;

import java.util.Random;
import org.eternity.adventure.game.item.Item;
import org.eternity.adventure.game.item.Source;

public class Destroy {
    private Source first;
    private Source second;
    private String itemName;

    public Destroy(Source first, Source second, String itemName) {
        this.first = first;
        this.second = second;
        this.itemName = itemName;
    }

    public boolean isPossible() {
        return contains(first) || contains(second);
    }

    private boolean contains(Source source) {
        return source.find(itemName).isPresent();
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
