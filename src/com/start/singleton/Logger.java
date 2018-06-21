package com.start.singleton;

public class Logger {
    private static final Logger instance = new Logger();

    private Logger() {
    }

    public static Logger getInstance() {
        return instance;
    }

    public void log(String... messages) {
        String outputString = "";

        for (String message : messages) {
            outputString = outputString.concat(message);
        }

        System.out.println(outputString);
    }
}
