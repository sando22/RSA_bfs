package com.start;

import com.start.helper.InputLineOptions;
import com.start.singleton.CommandReader;
import com.start.singleton.GraphGenerator;
import com.start.singleton.Logger;
import org.apache.commons.cli.CommandLine;

import java.util.*;

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
        if (commandLine.hasOption(InputLineOptions.WRITE_TO_FILE.getOption())) {
            logger.outputToFile(commandLine.getOptionValue(InputLineOptions.WRITE_TO_FILE.getOption()));
        }

        if (commandLine.hasOption(InputLineOptions.QUIET_MODE.getOption())) {
            logger.setQuietMode(true);
        }

        GraphGenerator graphGenerator = GraphGenerator.getInstance();
        if (commandLine.hasOption(InputLineOptions.READ_SIZE.getOption())) {
            boolean[][] graph = graphGenerator
                    .generateGraph(
                            Integer.parseInt(commandLine.getOptionValue(InputLineOptions.READ_SIZE.getOption()))
                    );
            logger.printGraph(graph);
            traverseGraph(graph);
        } else if (commandLine.hasOption(InputLineOptions.READ_FROM_FILE.getOption())) {
            boolean[][] graph = graphGenerator
                    .generateGraph(
                            commandLine.getOptionValue(InputLineOptions.READ_FROM_FILE.getOption())
                    );
            logger.printGraph(graph);
            traverseGraph(graph);
        } else {
            logger.log(Constants.ErrorMessages.NO_INPUT_METHOD);
        }
    }

    private static void traverseGraph(boolean[][] graph) {
        Date startTime = new Date();
        logger.log("Start traversing");

        List<Integer> visited = new ArrayList<>();
        Queue<boolean[]> queue = new LinkedList<>();

        for (int i = 0; i < graph.length; i++) {
            if (!visited.contains(i)) {
                logger.log("Visiting " + i + " from outer for cycle");
                visited.add(i);
                queue.add(graph[i]);

                while (!queue.isEmpty()) {
                    boolean[] matrixLine = queue.poll();
                    for (int j = 0; j < matrixLine.length; j++) {
                        if (matrixLine[j] && !visited.contains(j)) {
                            visited.add(j);
                            logger.log("Visiting " + j + " from the queue");

                            queue.add(graph[j]);
                        }
                    }
                }
            }
        }

        long timeTakenMillis = new Date().getTime() - startTime.getTime();

        logger.log("Complete traversing " + timeTakenMillis);
    }
}
