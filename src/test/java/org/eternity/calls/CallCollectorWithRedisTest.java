package org.eternity.calls;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.time.Duration;
import org.eternity.reader.CsvParser;
import org.eternity.reader.JsonParser;
import org.eternity.reader.RedisLineReader;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.RedisClient;
import redis.embedded.RedisServer;

public class CallCollectorWithRedisTest {
    private static RedisServer server;
    private static RedisClient redisClient;

    @BeforeAll
    static void setUp() throws IOException {
        server = new RedisServer(6379);
        server.start();
        redisClient = RedisClient.create("redis://localhost:6379");
    }

    @BeforeEach
    void flushRedis() {
        redisClient.flushAll();
    }

    @AfterAll
    static void tearDown() throws IOException {
        redisClient.close();
        server.stop();
    }

    @Test
    public void collect_with_json() throws Exception {
        String key = "phone:calls:json";
        String value = """
            {
              "calls": [
                { "from": "010-1111-2222", "to": "010-3333-4444", "start": "2024-01-01T11:31:05", "end": "2024-01-01T11:31:25" },
                { "from": "010-1111-2222", "to": "010-3333-4444", "start": "2024-01-02T09:10:01", "end": "2024-01-02T09:11:10" },
                { "from": "010-3333-4444", "to": "010-5555-6666", "start": "2024-01-02T09:11:32", "end": "2024-01-02T09:11:50" },
                { "from": "010-3333-4444", "to": "010-5555-6666", "start": "2024-01-03T20:01:30", "end": "2024-01-03T20:02:30" },
                { "from": "010-1111-2222", "to": "010-5555-6666", "start": "2024-01-04T15:45:23", "end": "2024-01-04T15:46:33" }
              ]
            }
            """;

        setupRedisData(key, value);

        CallCollector callCollector = new CallCollector(
                new DefaultReader("phone:calls:json", new JsonParser(), new RedisLineReader(redisClient)));

        CallHistory history = callCollector.collect("010-1111-2222");

        assertThat(history.callDuration()).isEqualTo(Duration.ofSeconds(159));
    }

    @Test
    public void collect_with_csv() throws Exception {
        String key = "phone:calls:csv";
        String value = """
                010-1111-2222,010-3333-4444,2024-01-01T11:31:05,2024-01-01T11:31:25
                010-1111-2222,010-3333-4444,2024-01-02T09:10:01,2024-01-02T09:11:10
                010-3333-4444,010-5555-6666,2024-01-02T09:11:32,2024-01-02T09:11:50
                010-3333-4444,010-5555-6666,2024-01-03T20:01:30,2024-01-03T20:02:30
                010-1111-2222,010-5555-6666,2024-01-04T15:45:23,2024-01-04T15:46:33
                """;

        setupRedisData(key, value);

        CallCollector callCollector = new CallCollector(
                new DefaultReader("phone:calls:csv", new CsvParser(), new RedisLineReader(redisClient)));

        CallHistory history = callCollector.collect("010-1111-2222");

        assertThat(history.callDuration()).isEqualTo(Duration.ofSeconds(159));
    }

    private static void setupRedisData(String key, String value) {
        redisClient.set(key, value);
    }
}
