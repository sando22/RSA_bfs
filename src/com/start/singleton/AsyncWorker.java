package com.start.singleton;

import com.start.Constants;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncWorker {
    private static AsyncWorker instance;

    private Logger logger = Logger.getInstance();
    private ExecutorService executorService;

    private AsyncWorker(int numberOfThreads) {
        executorService = Executors.newFixedThreadPool(numberOfThreads);
    }

    public static AsyncWorker createInstance(int numberOfThreads) {
        if (instance != null) {
            throw new IllegalArgumentException(Constants.ErrorMessages.WORKER_CREATION_EXCEPTION);
        }

        instance = new AsyncWorker(numberOfThreads);

        return instance;
    }

    public static AsyncWorker getInstance() {
        return instance;
    }

    public void traverseGraph(boolean[][] graph) {
        logger.log("Start traversing");

        List<Integer> visited = new ArrayList<>();
        List<Callable<Void>> workers = new ArrayList<>();

        for (int i = 0; i < graph.length; i++) {
            workers.add(new Traverse(visited, graph, i));
        }

        logger.logVerbose("start");
        Date startTime = new Date();
        try {
            executorService.invokeAll(workers);
            executorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.log("Finished executing after " + (new Date().getTime() - startTime.getTime()) + "ms");
    }

    private class Traverse implements Callable<Void> {
        List<Integer> visited;
        Queue<Integer> queue;
        private boolean[][] graph;
        private int line;

        public Traverse(List<Integer> visited, boolean[][] graph, int line) {
            this.visited = visited;
            this.graph = graph;
            this.line = line;

            queue = new LinkedList<>();
        }

        private synchronized void visit(int startNode) {
            if (!visited.contains(startNode)) {
                visited.add(startNode);
                queue.add(startNode);
                logger.logVerbose("Visiting " + startNode + " from " + Thread.currentThread().getName());
            } else {
                logger.logVerbose("Skipping " + startNode + " from " + Thread.currentThread().getName());
            }
        }

        private synchronized void visit(List<Integer> neighbours) {
            for (int neighbour :
                    neighbours) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                    logger.logVerbose("Visiting " + neighbour + " from " + Thread.currentThread().getName());
                }
            }
        }

        @Override
        public Void call() {
            visit(line);

            while (!queue.isEmpty()) {
                int matrixLine = queue.remove();

                List<Integer> neighbours = new ArrayList<>();

                for (int j = 0; j < graph[matrixLine].length; j++) {
                    if (graph[matrixLine][j]) {
                        neighbours.add(j);
                    }
                }

                visit(neighbours);
            }

            return null;
        }
    }
}
