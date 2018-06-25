package com.start.singleton;

import com.start.Constants;
import com.start.helper.InputLineOptions;
import org.apache.commons.cli.*;

public class CommandReader {
    private static final CommandReader instance = new CommandReader();

    private Options cmdOptions;
    private CommandLine commandLine;

    private CommandReader() {
    }

    public static CommandReader getInstance(String[] args) {
        instance.init(args);

        return instance;
    }

    public static CommandReader getInstance() {
        return instance;
    }

    private void init(String[] args) {
        if (cmdOptions != null) {
            return;
        }

        cmdOptions = new Options();
        initCommands();

        CommandLineParser parser = new DefaultParser();

        try {
            commandLine = parser.parse(cmdOptions, args);
        } catch (ParseException exp) {
            System.err.println(Constants.ErrorMessages.COMMAND_LINE_PARSING_ERROR + exp.getMessage());

            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("bfs", cmdOptions);
        }
    }

    private void initCommands() {
        for (InputLineOptions lineOption : InputLineOptions.values()) {
            cmdOptions.addOption(lineOption.getOption(), lineOption.hasArguments(), lineOption.getOptionDescription());
        }
    }

    public CommandLine getCommandLine() {
        return commandLine;
    }
}
