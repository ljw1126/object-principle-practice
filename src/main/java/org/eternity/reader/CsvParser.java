package org.eternity.reader;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.eternity.calls.Call;
import org.eternity.calls.Parser;
import org.eternity.calls.TimeInterval;

public class CsvParser implements Parser{

    @Override
    public List<Call> parse(List<String> lines) {
        return lines.stream().map(this::parseCall).toList();
    }

    private Call parseCall(String line) {
        String[] tokens = line.split(",");
        return new Call(tokens[0].trim(), tokens[1].trim(), 
                TimeInterval.of(parseDateTime(tokens[2].trim()), parseDateTime(tokens[3].trim())));
    }

    private LocalDateTime parseDateTime(String token) {
        return LocalDateTime.parse(token.trim(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
