package org.eternity.calls;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.Duration;
import org.eternity.reader.CsvParser;
import org.eternity.reader.JsonParser;
import org.eternity.reader.RedisReader;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.RedisClient;
import redis.embedded.RedisServer;

public class CallCollectorWithRedisTest {
     @Test
    public void collect_with_json() throws Exception {
        RedisServer server = new RedisServer(6379);
        server.start();

        RedisClient redisClient = RedisClient.create("redis://localhost:6379");
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

        CallCollector callCollector = new CallCollector(
                new RedisReader("phone:calls:json", new JsonParser(), redisClient));

        CallHistory history = callCollector.collect("010-1111-2222");

        assertThat(history.callDuration()).isEqualTo(Duration.ofSeconds(159));

        server.stop();
    }

    @Test
    public void collect_with_csv() throws Exception {
        RedisServer server = new RedisServer(6379);
        server.start();

        RedisClient redisClient = RedisClient.create("redis://localhost:6379");
        redisClient.set("phone:calls:csv",
                """
                010-1111-2222,010-3333-4444,2024-01-01T11:31:05,2024-01-01T11:31:25
                010-1111-2222,010-3333-4444,2024-01-02T09:10:01,2024-01-02T09:11:10
                010-3333-4444,010-5555-6666,2024-01-02T09:11:32,2024-01-02T09:11:50
                010-3333-4444,010-5555-6666,2024-01-03T20:01:30,2024-01-03T20:02:30
                010-1111-2222,010-5555-6666,2024-01-04T15:45:23,2024-01-04T15:46:33
                """);

        CallCollector callCollector = new CallCollector(
                new RedisReader("phone:calls:csv", new CsvParser(), redisClient));

        CallHistory history = callCollector.collect("010-1111-2222");

        assertThat(history.callDuration()).isEqualTo(Duration.ofSeconds(159));

        server.stop();
    }
}
