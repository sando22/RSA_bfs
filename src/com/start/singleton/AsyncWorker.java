package com.start.singleton;

import com.start.Constants;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
        logger.logVerbose(Constants.Messages.GRAPH_TRAVERSAL_STARTED);

        List<Integer> visited = new ArrayList<>();
        List<Callable<Void>> workers = new ArrayList<>();

        for (int i = 0; i < graph.length; i++) {
            workers.add(new Traverse(visited, graph, i));
        }

        Date startTime = new Date();
        try {
            executorService.invokeAll(workers);
            executorService.shutdown();
            executorService.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.log(Constants.Messages.GRAPH_TRAVERSAL_TIME + (new Date().getTime() - startTime.getTime()));
    }

    private class Traverse implements Callable<Void> {
        List<Integer> visited;
        Queue<Integer> queue;
        private boolean[][] graph;
        private int line;
        private Date startTime;

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
                logger.logDebug("Visiting " + startNode + " from " + Thread.currentThread().getId());
            }
        }

        private synchronized void visit(List<Integer> neighbours) {
            for (int neighbour :
                    neighbours) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                    logger.logDebug("Visiting " + neighbour + " from " + Thread.currentThread().getId());
                }
            }
        }

        @Override
        public Void call() {
            startTime = new Date();
            logger.logVerbose(String.format(Constants.Messages.THREAD_STARTED, Thread.currentThread().getId()));

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

            logger.logVerbose(
                    String.format(Constants.Messages.THREADS_EXECUTION_TIME, Thread.currentThread().getId())
                            + (new Date().getTime() - startTime.getTime())
            );
            logger.logVerbose(String.format(Constants.Messages.THREAD_STOPPED, Thread.currentThread().getId()));
            return null;
        }
    }
}
