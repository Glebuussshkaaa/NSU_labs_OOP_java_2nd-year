package com.glebestraikh.stackcalculator;

public class CommandLineParser {
    private String inputFilePath;

    public void parse(String[] args) {
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals("-i") || args[i].equals("--input")) {
                inputFilePath = args[i + 1];
            }
        }
    }

    public String getInputFilePath() {
        return inputFilePath;
    }
}
