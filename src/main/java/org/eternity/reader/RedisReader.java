package org.eternity.reader;

import java.util.List;
import org.eternity.calls.AbstractReader;
import org.eternity.calls.Parser;
import redis.clients.jedis.RedisClient;

public class RedisReader extends AbstractReader {
    private final RedisClient redisClient;

    public RedisReader(String path, Parser parser, RedisClient redisClient) {
        super(path, parser);
        this.redisClient = redisClient;
    }

    @Override
    protected List<String> readLines(String path) {
        String json = redisClient.get(path);
        return json == null ? List.of() : List.of(json.split("\n"));
    }
}
