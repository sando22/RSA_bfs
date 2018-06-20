package com.start;

import org.apache.commons.cli.CommandLine;

public class Main {

    public static void main(String[] args) {
        CommandReader commandReader = new CommandReader(args);

        CommandLine commandLine = commandReader.getCommandLine();
        if (commandLine != null && commandLine.hasOption("q")) {
            System.out.print("Hello from IDEA!");
        }
    }
}
