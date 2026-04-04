package org.eternity.calls;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.Duration;
import java.time.LocalDateTime;
import org.eternity.reader.FakeReader;
import org.junit.jupiter.api.Test;

public class CallCollectorTest {
    @Test
    void collect() {
       CallCollector callCollector = new CallCollector(
                new FakeReader(
                    new Call("010-1111-2222", "010-3333-4444",
                            TimeInterval.of(LocalDateTime.of(2024,1,1,11, 31,5), LocalDateTime.of(2024,1,1,11,31,25))),
                    new Call("010-1111-2222", "010-3333-4444",
                            TimeInterval.of(LocalDateTime.of(2024,1,2,9,10,1), LocalDateTime.of(2024,1,2,9,11,10))),
                    new Call("010-3333-4444", "010-5555-6666",
                            TimeInterval.of(LocalDateTime.of(2024, 1, 2, 9, 11, 32), LocalDateTime.of(2024,1,2,9,11,50))),
                    new Call("010-3333-4444", "010-5555-6666",
                            TimeInterval.of(LocalDateTime.of(2024, 1, 3, 10, 1, 30), LocalDateTime.of(2024,1,3,20,2,30))),
                    new Call("010-1111-2222", "010-3333-4444",
                                TimeInterval.of(LocalDateTime.of(2024, 1, 4, 15, 45, 23), LocalDateTime.of(2024,1,4,15, 46,33)))
                ));

       CallHistory callHistory = callCollector.collect("010-1111-2222");

       assertThat(callHistory.callDuration())
            .isEqualTo(Duration.ofSeconds(159));
    }
}
