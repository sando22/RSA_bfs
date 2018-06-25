package com.start.helper;

public enum InputLineOptions {
    // todo use different random methods for edge generating

    READ_SIZE("n", true, "generates graph with given the given number of vertexes"),
    READ_FROM_FILE("i", true, "read graph from file"),
    WRITE_TO_FILE("o", true, "writes output of the program to file"),
    THREADS_TO_USE("t", true, "specify the number of threads to use for the algorithm"),
    QUIET_MODE("q", false, "print only the primary objectives");

    private String option;
    private boolean hasArguments;
    private String optionDescription;

    InputLineOptions(String option, boolean hasArguments, String optionDescription) {
        this.option = option;
        this.hasArguments = hasArguments;
        this.optionDescription = optionDescription;
    }

    public String getOption() {
        return option;
    }

    public boolean hasArguments() {
        return hasArguments;
    }

    public String getOptionDescription() {
        return optionDescription;
    }
}
