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

        public static final String GRAPH_GENERATION_TIME = "Graph generation execution time (millis): ";
        public static final String GRAPH_GENERATED = "Graph generated";
        public static final String GRAPH_TRAVERSAL_STARTED = "Graph traversal started";

        public static final String THREAD_STARTED = "Thread-%s started.";
        public static final String THREAD_STOPPED = "Thread-%s stopped. ";
        public static final String THREADS_EXECUTION_TIME = "Thread-%s execution time was (millis): ";

        public static final String THREADS_USED = "Threads used in current run: ";

        public static final String TOTAL_EXECUTION_TIME = "Total execution time for current run (millis): ";
        public static final String GRAPH_TRAVERSAL_TIME = "Graph traversal execution time (millis): ";
    }
}
