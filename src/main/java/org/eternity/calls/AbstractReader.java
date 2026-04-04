package org.eternity.calls;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class AbstractReader implements Reader {
    private String path;

    protected AbstractReader(String path) {
        this.path = path;
    }

    public List<Call> read() {
        List<String> lines = readLines(path);   
        return parse(lines);
    }

    private List<String> readLines(String path) {
         try {
            return Files.readAllLines(Path.of(path));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract List<Call> parse(List<String> lines);
}