package com.glebestraikh.stackcalculator;

public class CommandLineParser {
    private String inputFilePath;

    public boolean parse(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-h") || args[i].equals("--help")) {
                printHelp();
                return false;
            }
            if (args[i].equals("-i") || args[i].equals("--input")) {
                if (i + 1 < args.length) {
                    inputFilePath = args[i + 1];
                    i++;
                }
            }
        }
        return true;
    }

    private void printHelp() {
        System.out.println("Usage: java CommandLineParser -i <inputFile> -o <outputFile>");
        System.out.println("Options:");
        System.out.println("  -h, --help    Print this help message");
        System.out.println("  -i, --input   Specify input file");
        System.out.println("  -o, --output  Specify output file");
    }

    public String getInput() {
        return inputFilePath;
    }
}