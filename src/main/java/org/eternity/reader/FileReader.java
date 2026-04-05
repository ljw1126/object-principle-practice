package org.eternity.reader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.eternity.calls.AbstractReader;
import org.eternity.calls.Parser;

public class FileReader extends AbstractReader {
    public FileReader(String path, Parser parser) {
        super(path, parser);
    }
    
    @Override
    protected List<String> readLines(String path) {
         try {
            return Files.readAllLines(Path.of(path));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
