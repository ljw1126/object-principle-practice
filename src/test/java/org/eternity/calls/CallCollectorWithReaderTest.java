package org.eternity.calls;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.Duration;
import java.util.stream.Stream;
import org.eternity.reader.CsvReader;
import org.eternity.reader.JsonReader;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CallCollectorWithReaderTest {
    public static Stream<Arguments> readers() {
        return Stream.of(
            Arguments.of(new CsvReader("src/main/resources/calls.csv")),
            Arguments.of(new JsonReader("src/main/resources/calls.json"))
        );
    }

    @ParameterizedTest
    @MethodSource("readers")
    public void collect(Reader reader) {
        CallCollector collector = new CallCollector(reader);
        CallHistory history = collector.collect("010-1111-2222");
        
        assertThat(history.callDuration()).isEqualTo(Duration.ofSeconds(159));
    }
}
