package org.eternity.reader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.eternity.calls.LineReader;

public class FileLineReader implements LineReader {
   
    @Override
    public List<String> readLines(String path) {
         try {
            return Files.readAllLines(Path.of(path));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
