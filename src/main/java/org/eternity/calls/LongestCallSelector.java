package org.eternity.calls;

import java.util.Optional;

public class LongestCallSelector {
    public Optional<Call> select(String phone, CallCollector collector) {
        CallHistory history = collector.collect(phone);
        return history.longestCall();
    }
}
