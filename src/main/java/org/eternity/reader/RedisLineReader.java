package org.eternity.reader;

import java.util.List;
import org.eternity.calls.LineReader;
import redis.clients.jedis.RedisClient;

public class RedisLineReader implements LineReader {
    private final RedisClient redisClient;

    public RedisLineReader(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    @Override
    public List<String> readLines(String path) {
        String json = redisClient.get(path);
        return json == null ? List.of() : List.of(json.split("\n"));
    }
}
