package com.start;

import org.apache.commons.cli.*;

public class CommandReader {
    private Options cmdOptions;
    private CommandLineParser parser;
    private CommandLine commandLine;

    public CommandReader(String[] args) {
        cmdOptions = new Options();
        initCommands();

        parser = new DefaultParser();

        try {
            commandLine = parser.parse(cmdOptions, args);
        } catch (ParseException exp) {
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }
    }

    private void initCommands() {
        cmdOptions.addOption("q", false, "display current time");
    }

    public CommandLine getCommandLine() {
        return commandLine;
    }
}
