package org.eternity.adventure.game.world;

import java.util.List;
import org.eternity.adventure.game.Input;
import org.eternity.adventure.game.Output;

public class FakeInputOutput implements Input, Output{
    private int currentInput;
    private List<String> inputs;
    private StringBuilder outputs;

    public FakeInputOutput(String ... inputs) {
        this.currentInput = 0;
        this.inputs = List.of(inputs);
        this.outputs = new StringBuilder();
    }

    public List<String> outputs() {
        return outputs.toString().lines().toList();
    }

    @Override
    public String input() {
        return inputs.get(currentInput++);
    }

    @Override
    public void showLine(String text) {
        outputs.append(text + "\n");
    }

    @Override
    public void show(String text) {
        outputs.append(text);
    }
    
}
