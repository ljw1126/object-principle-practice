package org.eternity.calls;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class CallHistory {
    private String phone;
    private List<Call> calls = new ArrayList<>();
    
    public CallHistory(String phone) {
        this.phone = phone;
    }

    public void append(Call call) {
        if(call.from().equals(phone)) {
            calls.add(call);
        }
    }
    
    public String phone() {
        return phone;
    }

    public Duration callDuration() {
        return calls.stream()
                .map(Call::duration)
                .reduce(Duration.ZERO, Duration::plus);
    }

    public List<Call> calls() {
        return Collections.unmodifiableList(calls);
    }

    public CallHistory find(Predicate<Call> condition) {
        CallHistory result = new CallHistory(this.phone);
        calls.stream().filter(condition).forEach(result::append);
        return result;
    }

    public Optional<Call> longestCall() {
        return calls.stream()
            .max(Comparator.comparing(Call::duration));
    }


}
