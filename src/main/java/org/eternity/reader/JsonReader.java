package org.eternity.reader;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.eternity.calls.AbstractReader;
import org.eternity.calls.Call;
import org.eternity.calls.TimeInterval;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonReader extends AbstractReader {
    public JsonReader(String path) {
        super(path);
    }

    public record CallHistoryRecord(List<CallRecord> calls) {
        public record CallRecord(String from, String to, LocalDateTime start, LocalDateTime end) {}
    }

    @Override
    protected List<Call> parse(List<String> lines) {
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
