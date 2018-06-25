package com.start;

import com.start.helper.InputLineOptions;
import com.start.singleton.CommandReader;
import com.start.singleton.GraphGenerator;
import com.start.singleton.Logger;
import org.apache.commons.cli.CommandLine;

public class Main {
    private static Logger logger;

    public static void main(String[] args) {
        logger = Logger.getInstance();
        logger.logVerbose(Constants.Messages.WELCOME);

        CommandReader commandReader = CommandReader.getInstance(args);

        CommandLine commandLine = commandReader.getCommandLine();

        if (commandLine != null) {
            readCommands(commandLine);
        }
    }

    private static void readCommands(CommandLine commandLine) {
        if (commandLine.hasOption(InputLineOptions.QUIET_MODE.getOption())) {
            logger.setQuietMode(true);
        }

        GraphGenerator graphGenerator = GraphGenerator.getInstance();
        if (commandLine.hasOption(InputLineOptions.READ_SIZE.getOption())) {
            logger.printGraph(
                    graphGenerator
                            .generateGraph(
                                    Integer.parseInt(commandLine.getOptionValue(InputLineOptions.READ_SIZE.getOption()))
                            )
            );
        } else if (commandLine.hasOption(InputLineOptions.READ_FROM_FILE.getOption())) {
            logger.printGraph(
                    graphGenerator
                            .generateGraph(
                                    commandLine.getOptionValue(InputLineOptions.READ_FROM_FILE.getOption())
                            )
            );
        } else {
            logger.log(Constants.ErrorMessages.NO_INPUT_METHOD);
        }
    }
}
