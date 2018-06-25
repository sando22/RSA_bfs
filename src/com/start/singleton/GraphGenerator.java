package com.start.singleton;

import com.start.Constants;

import java.util.Random;

public class GraphGenerator {
    private static GraphGenerator instance = new GraphGenerator();
    private Random random;

    private GraphGenerator() {
        random = new Random();
    }

    public static GraphGenerator getInstance() {
        if (instance == null) {
            instance = new GraphGenerator();
        }

        return instance;
    }

    public boolean[][] generateGraph(int nodes) {
        boolean[][] graph = new boolean[nodes][nodes];

        Logger.getInstance().logVerbose(Constants.Messages.NODE_GRAPH_GENERATING.concat(String.valueOf(nodes)));

        for (int i = 0; i < nodes; i++) {
            for (int j = 0; j < nodes; j++) {
                graph[i][j] = random.nextBoolean();
            }
        }

        Logger.getInstance().logVerbose(Constants.Messages.GRAPH_GENERATED);

        return graph;
    }

    public boolean[][] generateGraph(String filename) {
        // todo
        return null;
    }
}
