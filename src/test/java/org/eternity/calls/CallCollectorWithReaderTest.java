package org.eternity.calls;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.time.Duration;
import java.util.stream.Stream;
import org.eternity.reader.CsvReader;
import org.eternity.reader.CsvRedisReader;
import org.eternity.reader.JsonReader;
import org.eternity.reader.JsonRedisReader;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import redis.clients.jedis.RedisClient;
import redis.embedded.RedisServer;

public class CallCollectorWithReaderTest {
    private static RedisServer redisServer;
    private static RedisClient redisClient;

    public static Stream<Arguments> readers() {
        return Stream.of(
            Arguments.of(new CsvReader("src/main/resources/calls.csv")),
            Arguments.of(new JsonReader("src/main/resources/calls.json")),
            Arguments.of(new CsvRedisReader("phone:calls:csv", redisClient)),
            Arguments.of(new JsonRedisReader("phone:calls:json", redisClient))
        );
    }

    @BeforeAll
    public static void prepareRedis() throws IOException {
        redisServer = new RedisServer(6379);
        redisServer.start();

        redisClient = RedisClient.create("redis://localhost:6379");
        redisClient.set("phone:calls:json",
                """
                {
                  "calls": [
                    { "from": "010-1111-2222", "to": "010-3333-4444", "start": "2024-01-01T11:31:05", "end": "2024-01-01T11:31:25" },
                    { "from": "010-1111-2222", "to": "010-3333-4444", "start": "2024-01-02T09:10:01", "end": "2024-01-02T09:11:10" },
                    { "from": "010-3333-4444", "to": "010-5555-6666", "start": "2024-01-02T09:11:32", "end": "2024-01-02T09:11:50" },
                    { "from": "010-3333-4444", "to": "010-5555-6666", "start": "2024-01-03T20:01:30", "end": "2024-01-03T20:02:30" },
                    { "from": "010-1111-2222", "to": "010-5555-6666", "start": "2024-01-04T15:45:23", "end": "2024-01-04T15:46:33" }
                  ]
                }
                """);

        redisClient.set("phone:calls:csv",
                """
                010-1111-2222,010-3333-4444,2024-01-01T11:31:05,2024-01-01T11:31:25
                010-1111-2222,010-3333-4444,2024-01-02T09:10:01,2024-01-02T09:11:10
                010-3333-4444,010-5555-6666,2024-01-02T09:11:32,2024-01-02T09:11:50
                010-3333-4444,010-5555-6666,2024-01-03T20:01:30,2024-01-03T20:02:30
                010-1111-2222,010-5555-6666,2024-01-04T15:45:23,2024-01-04T15:46:33
                """);
    }

    @AfterAll
    public static void closeRedis() throws IOException {
        redisServer.stop();
    }

    @ParameterizedTest
    @MethodSource("readers")
    public void collect(Reader reader) {
        CallCollector collector = new CallCollector(reader);
        CallHistory history = collector.collect("010-1111-2222");
        
        assertThat(history.callDuration()).isEqualTo(Duration.ofSeconds(159));
    }
}
