package com.start;

import com.start.helper.InputLineOptions;
import com.start.singleton.AsyncWorker;
import com.start.singleton.CommandReader;
import com.start.singleton.GraphGenerator;
import com.start.singleton.Logger;
import org.apache.commons.cli.CommandLine;

import java.util.Date;

public class Main {
    private static Logger logger;
    private static int threadsUsed;

    public static void main(String[] args) {
        Date startTime = new Date();
        logger = Logger.getInstance();
        CommandReader commandReader = CommandReader.getInstance(args);

        CommandLine commandLine = commandReader.getCommandLine();

        if (commandLine != null) {
            readConfigCommands(commandLine);
        } else {
            return;
        }

        logger.logVerbose(Constants.Messages.WELCOME);

        boolean[][] graph;
        GraphGenerator graphGenerator = GraphGenerator.getInstance();
        if (commandLine.hasOption(InputLineOptions.READ_SIZE.getOption())) {
            graph = graphGenerator
                    .generateGraph(
                            Integer.parseInt(commandLine.getOptionValue(InputLineOptions.READ_SIZE.getOption()))
                    );
        } else if (commandLine.hasOption(InputLineOptions.READ_FROM_FILE.getOption())) {
            graph = graphGenerator
                    .generateGraph(
                            commandLine.getOptionValue(InputLineOptions.READ_FROM_FILE.getOption())
                    );
        } else {
            logger.log(Constants.ErrorMessages.NO_INPUT_METHOD);
            return;
        }

        AsyncWorker.getInstance().traverseGraph(graph);

        logger.logVerbose(Constants.Messages.THREADS_USED + threadsUsed);
        logger.logVerbose(Constants.Messages.TOTAL_EXECUTION_TIME + (new Date().getTime() - startTime.getTime()));
    }

    private static void readConfigCommands(CommandLine commandLine) {
        if (commandLine.hasOption(InputLineOptions.WRITE_TO_FILE.getOption())) {
            logger.outputToFile(commandLine.getOptionValue(InputLineOptions.WRITE_TO_FILE.getOption()));
        }

        if (commandLine.hasOption(InputLineOptions.QUIET_MODE.getOption())) {
            logger.setQuietMode(true);
        }

        if (commandLine.hasOption(InputLineOptions.DEBUG_MODE.getOption())) {
            logger.setDebugMode(true);
        }

        if (commandLine.hasOption(InputLineOptions.THREADS_TO_USE.getOption())) {
            int numberOfThreads = Integer.parseInt(commandLine.getOptionValue(InputLineOptions.THREADS_TO_USE.getOption()));

            AsyncWorker.createInstance(numberOfThreads);
            GraphGenerator.createInstance(numberOfThreads);
            threadsUsed = numberOfThreads;
        } else {
            AsyncWorker.createInstance(Constants.DEFAULT_NUMBER_OF_THREADS);
            GraphGenerator.createInstance(Constants.DEFAULT_NUMBER_OF_THREADS);
            threadsUsed = Constants.DEFAULT_NUMBER_OF_THREADS;
        }
    }
}
