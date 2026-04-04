package org.eternity.reader;

import java.time.LocalDateTime;
import java.util.List;
import org.eternity.calls.AbstractReader;
import org.eternity.calls.Call;
import org.eternity.calls.TimeInterval;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonReader extends AbstractReader {
     private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());


    public JsonReader(String path) {
        super(path);
    }

    public record CallHistoryRecord(List<CallRecord> calls) {
        public record CallRecord(String from, String to, LocalDateTime start, LocalDateTime end) {}
    }

    // toList() : Collections.unmodifiableList(..), Collectors.toList() : ArrayList 반환
    @Override
    protected List<Call> parse(List<String> lines) {
        CallHistoryRecord history = parseJson(lines);
        return history.calls().stream()
                .map(call -> new Call(call.from(), call.to(), TimeInterval.of(call.start(), call.end())))
                .toList();
    }

    private CallHistoryRecord parseJson(List<String> lines) {
        try {
            return OBJECT_MAPPER.readValue(String.join("", lines), CallHistoryRecord.class);
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
