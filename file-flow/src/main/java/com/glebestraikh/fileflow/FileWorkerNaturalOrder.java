package com.glebestraikh.fileflow;

import java.io.IOException;
import java.util.List;

public class FileWorkerNaturalOrder implements FileWorkerInterface {
    @Override
    public List<String> readFileAsList(String fileName) throws IOException {
        return java.nio.file.Files.readAllLines(java.nio.file.Paths.get(fileName));
    }
}