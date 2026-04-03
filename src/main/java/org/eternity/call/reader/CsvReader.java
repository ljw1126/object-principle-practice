package org.eternity.call.reader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.eternity.call.Call;
import org.eternity.call.Reader;
import org.eternity.call.TimeInterval;

public class CsvReader implements Reader {
    private String path;

    public CsvReader(String path) {
        this.path = path;
    }

    @Override
    public List<Call> read() {
        List<String> lines = readLines(path);
        return parse(lines);
    }

    private List<String> readLines(String path) {
        try {
            return Files.readAllLines(Path.of(ClassLoader.getSystemResource(path).toURI()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Call> parse(List<String> lines) {
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
