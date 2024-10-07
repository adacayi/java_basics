package com.sanver.basics.algorithms;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringJoiner;

public class DijkstrasAlgorithmExample2 {
    static Result findMinPath(int[][] matrix, int xEnd, int yEnd) {
        // Priority Queue to explore the least cost node first
        var priorityQueue = new PriorityQueue<Node>();
        int n = matrix.length;
        boolean[][] isVisited = new boolean[n][n];
        int[][] distances = new int[n][n];
        char[][] directions = new char[n][n];
        int x;
        int y;
        int distance;
        int newDistance;

        // Initialize distance array with large values
        for (var row : distances) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        distances[0][0] = 0;

        priorityQueue.offer(new Node(0, 0, 0));

        // Perform Dijkstra's algorithm
        while (!priorityQueue.isEmpty()) {
            var node = priorityQueue.poll();
            x = node.x;
            y = node.y;
            distance = node.distance;

            if (isVisited[x][y]) {
                continue;
            }

            isVisited[x][y] = true;

            // If we've reached the (xEnd, yEnd) cell, we're done
            if (x == xEnd && y == yEnd) {
                break;
            }

            // Explore the four directions (Down, Right, Up, Left)

            if (x + 1 < n && !isVisited[x + 1][y]) {
                newDistance = distance + matrix[x + 1][y];

                if (newDistance < distances[x + 1][y]) {
                    distances[x + 1][y] = newDistance;
                    directions[x + 1][y] = 'd';
                    priorityQueue.offer(new Node(x + 1, y, newDistance));
                }
            }

            if (y + 1 < n && !isVisited[x][y + 1]) {
                newDistance = distance + matrix[x][y + 1];

                if (newDistance < distances[x][y + 1]) {
                    distances[x][y + 1] = newDistance;
                    directions[x][y + 1] = 'r';
                    priorityQueue.offer(new Node(x, y + 1, newDistance));
                }
            }

            if (x - 1 >= 0 && !isVisited[x - 1][y]) {
                newDistance = distance + matrix[x - 1][y];

                if (newDistance < distances[x - 1][y]) {
                    distances[x - 1][y] = newDistance;
                    directions[x - 1][y] = 'u';
                    priorityQueue.offer(new Node(x - 1, y, newDistance));
                }
            }

            if (y - 1 >= 0 && !isVisited[x][y - 1]) {
                newDistance = distance + matrix[x][y - 1];

                if (newDistance < distances[x][y - 1]) {
                    distances[x][y - 1] = newDistance;
                    directions[x][y - 1] = 'l';
                    priorityQueue.offer(new Node(x, y - 1, newDistance));
                }
            }
        }

        return new Result(buildPath(directions, xEnd, yEnd), distances[xEnd][yEnd]);
    }

    // Reconstruct the path by backtracking from the (xEnd,yEnd) cell
    static String buildPath(char[][] directions, int xEnd, int yEnd) {
        var builder = new StringBuilder();
        int x = xEnd, y = yEnd;
        char direction;

        while (x != 0 || y != 0) {
            direction = directions[x][y];
            builder.append(direction);

            switch (direction) {
                case 'd' -> x--;
                case 'r' -> y--;
                case 'u' -> x++;
                case 'l' -> y++;
                default ->
                        throw new IllegalArgumentException(String.format("Undefined direction %c at %d,%d.", direction, x, y));
            }
        }

        return String.join(", ", builder.reverse().toString().split(""));
    }

    static String toString(int[][] matrix) {
        if (matrix == null) {
            return "null";
        }

        var length = matrix.length;

        if (length == 0) {
            return "{{}}";
        }

        var builder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            builder.append("{");
            var rowStrings = Arrays.stream(matrix[i]).mapToObj(String::valueOf).toArray(String[]::new);
            builder.append(String.join(", ", rowStrings));
            builder.append("}");

            if (i < length - 1) {
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    public static void main(String[] args) {
        // The seed is the one dimensional values of the nxn matrix.
        // The matrix show the distance needs to be travelled to each cell from an adjacent cell.
        // The travel can be done in 4 directions up, down, left, right and only one cell.
        // e.g. we can travel from (0,0) to (0,1) or (1,0), and the distance travelling from (0,0) to (0,1) is matrix[0][1].
        // This code has the method findMinPath, to find the path with minimum distance from (0,0) to (n-1, n-1).
        int[] seed = {5, 1, 2, 2, 3, 8, 1, 7, 2}; // path = d, d, r, r, distance = 12
//        int[] seed = {8, 1, 3, 5, 4, 3, 2, 6, 5, 2, 7, 3, 1, 8, 6, 2}; // r, r, d, r, d, d or r, d, r, r, d, d  distance = 17
//        int[] seed = {2, 1, 4, 2, 7, 6, 2, 3, 5, 3, 6, 5, 7, 3, 4, 1, 8, 5, 5, 4, 6, 7, 2, 3, 2, 3, 1, 2, 4, 8, 5, 5, 3, 4, 5, 6, 2, 3, 5, 3, 9, 5, 3, 7, 8, 1, 1, 7, 2, 2, 1, 8, 3, 6, 2, 1, 1, 3, 6, 4, 2, 1, 3, 6};
// r, d, d, d, r, r, r, d, r, d, r, d, r, d, distance = 35
        int n = (int) Math.sqrt(seed.length);
        int[][] matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = seed[i * n + j];
            }
        }

        System.out.println(toString(matrix));

        var xEnd = n - 1;
        var yEnd = n - 1;
        var result = findMinPath(matrix, xEnd, yEnd);
        System.out.printf("Path with minimum distance from (0, 0) to (%d,%d): %s. %nDistance: %d%n", xEnd, yEnd, result.path, result.distance);
    }

    static class Node implements Comparable<Node> {
        private final int x;
        private final int y;
        private final int distance;

        Node(final int x, final int y, final int distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }

        @Override
        public int compareTo(Node o) {
            if (o == null) {
                return 1;
            }

            return distance - o.distance;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Node.class.getSimpleName() + "[", "]")
                    .add("x=" + x)
                    .add("y=" + y)
                    .add("distance=" + distance)
                    .toString();
        }
    }

    static class Result {
        private final String path;
        private final int distance;

        Result(final String path, final int distance) {
            this.path = path;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return "path = " + path + ", distance = " + distance;
        }
    }

}
