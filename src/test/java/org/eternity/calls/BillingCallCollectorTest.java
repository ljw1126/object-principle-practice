package org.eternity.calls;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.Duration;
import java.time.LocalDateTime;
import org.eternity.reader.FakeReader;
import org.junit.jupiter.api.Test;

public class BillingCallCollectorTest {
    @Test
    void collect() {
        var collector = new CallCollector(new FakeReader(
                new Call("010-1111-2222", "010-3333-4444", TimeInterval.of(LocalDateTime.of(2024,1,1,0,0,0), LocalDateTime.of(2024,1,1,0,0,9))),
                new Call("010-1111-2222", "010-3333-4444", TimeInterval.of(LocalDateTime.of(2024,1,2,0,0,0), LocalDateTime.of(2024,1,2,0,0,9))),
                new Call("010-3333-4444", "010-5555-6666", TimeInterval.of(LocalDateTime.of(2024,1,3,0,0,0), LocalDateTime.of(2024,1,3,0,0,11)))
            ));
        BillingCallCollector callCollector = new BillingCallCollector(collector);
        
        assertThat(callCollector.collect("010-1111-2222").callDuration())
            .isEqualTo(Duration.ofSeconds(0));
        assertThat(callCollector.collect("010-3333-4444").callDuration())
            .isEqualTo(Duration.ofSeconds(11));
    }
}
