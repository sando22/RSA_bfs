package com.start.singleton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Logger {
    private static final Logger instance = new Logger();
    private boolean quiet;
    private PrintStream output;

    private Logger() {
        output = System.out;
    }

    public static Logger getInstance() {
        return instance;
    }

    public void setQuietMode(boolean enabled) {
        quiet = enabled;
    }

    public void outputToFile(String fileName) {
        try {
            output = new PrintStream(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

        output.println(outputString);
    }

    public void printGraph(boolean[][] graphMatrix) {
        if (quiet) {
            return;
        }

        output.println("Graph representation: ");
        for (boolean[] matrixLine : graphMatrix) {
            for (boolean matrixValue : matrixLine) {
                int binary = matrixValue ? 1 : 0;

                output.print(binary + " ");
            }
            output.println();
        }
    }
}
