package com.start.singleton;

import com.start.Constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    public boolean[][] generateGraph(String fileName) {
        boolean[][] graph = null;
        Logger.getInstance().logVerbose(Constants.Messages.FILE_GRAPH_GENERATING.concat(String.valueOf(false)));

        try {
            BufferedReader bufferedReader =
                    new BufferedReader(new FileReader(fileName));

            String line = bufferedReader.readLine();
            if (line == null) {
                return null;
            }

            int matrixSize = Integer.parseInt(line);
            graph = new boolean[matrixSize][matrixSize];

            int lineIndex = 0;
            while ((line = bufferedReader.readLine()) != null) {
                int valueIndex = 0;
                for (String matrixValue
                        : line.split(" ")) {
                    graph[lineIndex][valueIndex++] = Integer.parseInt(matrixValue) == 1;
                }

                lineIndex++;
            }

            bufferedReader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Logger.getInstance().logVerbose(Constants.Messages.GRAPH_GENERATED);

        return graph;
    }
}
