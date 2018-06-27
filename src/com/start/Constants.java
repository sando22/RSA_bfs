package com.start;

public class Constants {
    public static class ErrorMessages {
        public static final String COMMAND_LINE_PARSING_ERROR = "Error at parsing the command line!\n";
        public static final String NO_INPUT_METHOD = "No input method selected, can`t create a graph!\n";

        public static final String WORKER_CREATION_EXCEPTION = "Can't create new instance of class, use getInstance() instead!";
    }

    public static final int DEFAULT_NUMBER_OF_THREADS = 1;

    public static class Messages {
        public static final String WELCOME = "BFS project started";
        public static final String NODE_GRAPH_GENERATING = "Generating graph with given nodes ";
        public static final String FILE_GRAPH_GENERATING = "Generating graph from given file ";
        public static final String GRAPH_GENERATED = "Graph generated";
    }
}
