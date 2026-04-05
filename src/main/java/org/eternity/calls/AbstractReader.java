package org.eternity.calls;

import java.util.List;

public abstract class AbstractReader implements Reader {
    private String path;
    private Parser parser;

    protected AbstractReader(String path, Parser parser) {
        this.path = path;
        this.parser = parser;
    }

    public List<Call> read() {
        List<String> lines = readLines(path);   
        return parser.parse(lines);
    }

    protected abstract List<String> readLines(String path) ;
}