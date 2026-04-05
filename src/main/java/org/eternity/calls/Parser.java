package org.eternity.calls;

import java.util.List;

public interface Parser {
    List<Call> parse(List<String> lines);
}
