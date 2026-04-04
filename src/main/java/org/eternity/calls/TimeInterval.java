package org.eternity.calls;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeInterval {
    private final LocalDateTime from;
    private final LocalDateTime to;

    public TimeInterval(LocalDateTime from, LocalDateTime to) {
        this.from = from;
        this.to = to;
    }

    public static TimeInterval of(LocalDateTime from, LocalDateTime to) {
        return new TimeInterval(from, to);
    }
    
    public Duration duration() {
        return Duration.between(from, to);
    }

}
