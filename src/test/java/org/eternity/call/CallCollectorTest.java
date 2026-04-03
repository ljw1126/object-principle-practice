package org.eternity.call;

import static org.assertj.core.api.Assertions.assertThat;
import java.net.URL;
import java.time.Duration;
import org.eternity.call.reader.JsonReader;
import org.junit.jupiter.api.Test;

public class CallCollectorTest {
    @Test
    void collect() {
       CallCollector callCollector = new CallCollector(new JsonReader(jsonPath()));
       CallHistory callHistory = callCollector.collect("010-1111-2222");

       assertThat(callHistory.callDuration())
            .isEqualTo(Duration.ofSeconds(159));
    }

    private static String jsonPath() {
        URL resource = CallCollectorTest.class.getClassLoader().getResource("calls.json");
        return resource.getPath();
    }
}
