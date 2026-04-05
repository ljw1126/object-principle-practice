package org.eternity.reader;

import java.util.Arrays;
import java.util.List;
import org.eternity.calls.Call;
import redis.clients.jedis.RedisClient;

public class JsonRedisReader extends JsonReader {
    private String key;
    private RedisClient redisClient;
    
    public JsonRedisReader(String key, RedisClient redisClient) {
        super(null);
        this.key = key;
        this.redisClient = redisClient;
    }

    @Override
    public List<Call> read() {
        List<String> lines = readKey(key);
        return super.parse(lines);
    }

    private List<String> readKey(String path) {
        String json = redisClient.get(path);
        return Arrays.stream(json.split("\n")).toList();
    }
}
