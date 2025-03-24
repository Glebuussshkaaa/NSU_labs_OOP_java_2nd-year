package com.glebestraikh.fileflow;

import java.io.IOException;
import java.util.List;

public interface FileWorkerInterface {
    List<String> readFileAsList(String fileName) throws IOException;

    default void print(String fileName) throws IOException {
        List<String> lines = readFileAsList(fileName);
        for (String line : lines) {
            System.out.println(line);
        }
    }
}
