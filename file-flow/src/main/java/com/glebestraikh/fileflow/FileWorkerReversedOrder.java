package com.glebestraikh.fileflow;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class FileWorkerReversedOrder implements FileWorkerInterface {
    @Override
    public List<String> readFileAsList(String fileName) throws IOException {
        List<String> lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(fileName));
        Collections.reverse(lines);
        return lines;
    }
}
