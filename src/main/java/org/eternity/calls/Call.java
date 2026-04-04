package org.eternity.calls;

import java.time.Duration;

public class Call {
    private String from;
    private String to;
    private TimeInterval callTime;
    
    public Call(String from, String to, TimeInterval callTime) {
        this.from = from;
        this.to = to;
        this.callTime = callTime;
    }

    public String from() {
        return from;
    }

    public String to() {
        return to;
    }

    public Duration duration() {
        return callTime.duration();
    }
    
}
