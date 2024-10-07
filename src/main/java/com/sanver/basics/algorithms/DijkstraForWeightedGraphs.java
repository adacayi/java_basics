package com.sanver.basics.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraForWeightedGraphs {
    public static void main(String[] args) {
        int[][] matrix = {
                {0, 2, 0, 7},
                {2, 0, 3, 0},
                {0, 3, 0, 1},
                {7, 0, 1, 0}
        }; // This matrix represents a weighted graph where matrix[i][j] represents the weight of the edge between node i and node j, and 0 means there is no edge between node i and node j.
        var result = findShortestPath(matrix, 0, 3);
        System.out.println(result);
    }

    public static PathDetails findShortestPath(int[][] matrix, int begin, int end) {
        int length;

        if (matrix == null || (length = matrix.length) < 1 || begin < 0 || begin >= length || end < 0 || end >= length) {
            return null;
        }

        if (begin == end) {
            return new PathDetails(0, List.of(end));
        }

        boolean[] isVisited = new boolean[length];
        int[] distances = new int[length];
        int[] previousNode = new int[length];

        for (int i = 0; i < length; i++) {
            distances[i] = Integer.MAX_VALUE;
            previousNode[i] = -1;
        }
        distances[begin] = 0;

        var queue = new PriorityQueue<Node>(length);

        queue.offer(new Node(begin, 0));

        while (!queue.isEmpty()) {
            var node = queue.poll();
            int index = node.index();
            int distance = node.distance();

            if (isVisited[index]) {
                continue;
            }

            isVisited[index] = true;

            if (index == end) {
                break;
            }

            for (int j = 0; j < length; j++) {
                var toJDistance = matrix[index][j];

                if (!isVisited[j] && toJDistance > 0 && distances[j] > toJDistance) { // toJDistance > 0 is put to avoid non-existing routes since 0 means there is no edge to j.
                    distances[j] = distance + toJDistance;
                    previousNode[j] = index;
                    queue.offer(new Node(j, distance + toJDistance));
                }
            }
        }

        if (previousNode[end] == -1) {
            return null;
        }

        var path = new ArrayList<Integer>();
        path.add(end);

        for (int current = previousNode[end]; current != -1; current = previousNode[current]) {
            path.add(current);
        }

        Collections.reverse(path);
        return new PathDetails(distances[end], path);
    }

    record PathDetails(int distance, List<Integer> path) {
    }

    record Node(int index, int distance) implements Comparable<Node> {

        @Override
        public int compareTo(Node o) {
            if (o == null) {
                return 1;
            }

            return this.distance - o.distance;
        }
    }
}
