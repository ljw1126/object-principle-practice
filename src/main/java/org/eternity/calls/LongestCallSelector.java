package org.eternity.calls;

import java.util.Comparator;
import java.util.Optional;

public class LongestCallSelector {
    public Optional<Call> select(String phone, CallCollector collector) {
        return collector.collect(phone).calls().stream()
        .max(Comparator.comparing(Call::duration));
    }
}
