package org.eternity.calls;

import java.util.List;

public class DefaultReader implements Reader {
    private String path;
    private Parser parser;
    private LineReader lineReader;

    public DefaultReader(String path, Parser parser, LineReader lineReader) {
        this.path = path;
        this.parser = parser;
        this.lineReader = lineReader;
    }

    @Override
    public List<Call> read() {
        List<String> lines = lineReader.readLines(path);   
        return parser.parse(lines);
    }
}
