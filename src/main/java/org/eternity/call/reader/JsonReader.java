package org.eternity.call.reader;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.eternity.call.Call;
import org.eternity.call.Reader;
import org.eternity.call.TimeInterval;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonReader implements Reader {
    private String path;

    public JsonReader(String path) {
        this.path = path;
    }

    public record CallHistoryRecord(List<CallRecord> calls) {
        public record CallRecord(String from, String to, LocalDateTime start, LocalDateTime end) {}
    }

    @Override
    public List<Call> read() {
        List<String> lines = readLines(path);
        return parse(lines);
    }

    private List<String> readLines(String path) {
        try {
            return java.nio.file.Files.readAllLines(java.nio.file.Path.of(path));
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Call> parse(List<String> lines) {
        CallHistoryRecord history = parseJson(lines);
        return history.calls().stream()
                .map(call -> new Call(call.from, call.to, TimeInterval.of(call.start, call.end))).collect(Collectors.toList());
    }

    private CallHistoryRecord parseJson(List<String> lines) {
        try {
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            String json = lines.stream().collect(Collectors.joining());
            JsonNode node = mapper.readTree(json.getBytes());

            return mapper.treeToValue(node, CallHistoryRecord.class);
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
