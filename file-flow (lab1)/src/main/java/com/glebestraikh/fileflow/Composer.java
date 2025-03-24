package com.glebestraikh.fileflow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Composer {

    private final int columnWidth;

    private final FileWorkerInterface firstSourceText;
    private final FileWorkerInterface secondSourceText;

    public Composer(int columnWidth, FileWorkerInterface firstSource, FileWorkerInterface secondSource) {
        this.columnWidth = columnWidth;
        this.firstSourceText = firstSource;
        this.secondSourceText = secondSource;
    }

    public void printInTwoColumns(String fileName) throws IOException {
        List<String> naturalOrderLines = firstSourceText.readFileAsList(fileName);
        List<String> reversedOrderLines = secondSourceText.readFileAsList(fileName);

        List<String> leftPartsList = new ArrayList<>();
        List<String> rightPartsList = new ArrayList<>();
        int maxLines = Math.max(naturalOrderLines.size(), reversedOrderLines.size());
        for (int i = 0; i < maxLines; i++) {
            String left = i < naturalOrderLines.size() ? naturalOrderLines.get(i) : "";
            String right = i < reversedOrderLines.size() ? reversedOrderLines.get(i) : "";

            String[] leftParts = wrapTextToWidth(left);
            leftPartsList.addAll(Arrays.asList(leftParts));

            String[] rightParts = wrapTextToWidth(right);
            Collections.reverse(Arrays.asList(rightParts));
            rightPartsList.addAll(Arrays.asList(rightParts));
        }

        for (int i = 0; i < rightPartsList.size(); i++) {
            System.out.printf("%-40s | %-40s%n", leftPartsList.get(i), rightPartsList.get(i));
        }
    }

    private String[] wrapTextToWidth(String line) {
        int length = line.length();
        int numParts = (length + columnWidth - 1) / columnWidth;
        String[] parts = new String[numParts];

        for (int i = 0; i < numParts; i++) {
            int start = i * columnWidth;
            int end = Math.min(start + columnWidth, length);
            parts[i] = line.substring(start, end);
        }

        return parts;
    }
}