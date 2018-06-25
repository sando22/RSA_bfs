package com.start.singleton;

public class Logger {
    private static final Logger instance = new Logger();
    private boolean quiet;

    private Logger() {
    }

    public static Logger getInstance() {
        return instance;
    }

    public void setQuietMode(boolean enabled) {
        quiet = enabled;
    }

    public void logVerbose(String... messages) {
        if (!quiet) {
            log(messages);
        }
    }

    public void log(String... messages) {
        String outputString = "";

        for (String message : messages) {
            outputString = outputString.concat(message);
        }

        System.out.println(outputString);
    }

    public void printGraph(boolean[][] graphMatrix) {
        System.out.println("Graph representation: ");
        for (boolean[] matrixLine : graphMatrix) {
            for (boolean matrixValue : matrixLine) {
                int binary = matrixValue ? 1 : 0;

                System.out.print(binary + " ");
            }
            System.out.println();
        }
    }
}
