package com.start.singleton;

import com.start.Constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class GraphGenerator {
    private static GraphGenerator instance;

    private Random random;
    private int threadsToUse;

    private GraphGenerator(int threadsToUse) {
        this.threadsToUse = threadsToUse;
        random = new Random();
    }

    public static GraphGenerator createInstance(int threadsToUse) {
        if (instance != null) {
            throw new IllegalArgumentException(Constants.ErrorMessages.WORKER_CREATION_EXCEPTION);
        }

        instance = new GraphGenerator(threadsToUse);

        return instance;
    }

    public static GraphGenerator getInstance() {
        return instance;
    }

    public boolean[][] generateGraph(int nodes) {
        boolean[][] graph = new boolean[nodes][nodes];
        Date startTime = new Date();

        Logger.getInstance().logVerbose(Constants.Messages.NODE_GRAPH_GENERATING.concat(String.valueOf(nodes)));

        if (threadsToUse > 1) {
            multiThreadGenerate(graph, nodes);
        } else {
            singleThreadGenerate(graph, nodes);
        }

        Logger.getInstance().logVerbose(Constants.Messages.GRAPH_GENERATION_TIME + (new Date().getTime() - startTime.getTime()));

        return graph;
    }

    private void multiThreadGenerate(boolean[][] graph, int nodes) {
        List<Callable<Void>> generators = new ArrayList<>();
// todo handle edge cases
        int linesPerThread = graph.length / threadsToUse;

        for (int i = 0; i < threadsToUse; i++) {
            if (i == threadsToUse - 1) {
                generators.add(new Generator(graph, i * linesPerThread, graph.length));
            } else {
                generators.add(new Generator(graph, i * linesPerThread, i * linesPerThread + linesPerThread));
            }
        }

        ExecutorService executorService = Executors.newFixedThreadPool(threadsToUse);
        try {
            executorService.invokeAll(generators);
            executorService.shutdown();
            executorService.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void singleThreadGenerate(boolean[][] graph, int nodes) {
        for (int i = 0; i < nodes; i++) {
            for (int j = 0; j < nodes; j++) {
                graph[i][j] = random.nextBoolean();
            }
        }
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

    private class Generator implements Callable<Void> {
        private boolean[][] graph;
        private int startIndex;
        private int endIndex;
        private ThreadLocalRandom random;

        public Generator(boolean[][] graph, int startIndex, int endIndex) {
            this.graph = graph;
            this.startIndex = startIndex;
            this.endIndex = endIndex;

            random = ThreadLocalRandom.current();
        }

        @Override
        public Void call() {
            for (int i = startIndex; i < endIndex; i++) {
                for (int j = 0; j < graph.length; j++) {
                    graph[i][j] = random.nextBoolean();
                }
            }

            return null;
        }
    }
}
