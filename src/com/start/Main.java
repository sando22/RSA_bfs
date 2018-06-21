package com.start;

import com.start.singleton.CommandReader;
import com.start.singleton.Logger;
import org.apache.commons.cli.CommandLine;

public class Main {

    public static void main(String[] args) {
        CommandReader commandReader = CommandReader.getInstance(args);

        Logger.getInstance().log("Hello from IDEA!");

        CommandLine commandLine = commandReader.getCommandLine();
        if (commandLine != null && commandLine.hasOption("q")) {
            Logger.getInstance().log("Quiet this time!");
        }
        if (commandLine != null && commandLine.hasOption("n")) {
            Logger.getInstance().log(commandLine.getOptionValue("n"));
        }
    }
}
