/**
 * @file SCCAnalyzer.java
 * @brief Strongly Connected Components (SCC) analyzer for task dependencies.
 *
 * This class provides functionality for analyzing Strongly Connected Components (SCCs)
 * in a directed graph representation of task dependencies using Kosaraju's algorithm.
 *
 * @version 1.0
 * @date 2024-12-24
 * @author User
 */

package com.abdul.fatma.hamza.sahan.task;

import java.util.*;

/**
 * @class SCCAnalyzer
 * @brief Analyzes Strongly Connected Components (SCCs) in task dependencies.
 *
 * This class utilizes Kosaraju's algorithm to identify SCCs in a directed graph
 * representation of task dependencies. It supports adjacency list creation, graph
 * transposition, and SCC traversal.
 */
public class SCCAnalyzer {

    /** @brief Stack used during SCC discovery. */
    private Stack<Integer> sccStack = new Stack<>();

    /** @brief Adjacency list representing the graph. */
    private Map<Integer, List<Integer>> adjList = new HashMap<>();

    /** @brief Transposed adjacency list for reverse graph traversal. */
    private Map<Integer, List<Integer>> transposeList = new HashMap<>();

    /**
     * @brief Pushes a vertex onto the SCC stack.
     *
     * @param v The vertex to be pushed onto the stack.
     */
    public void pushSccStack(int v) {
        sccStack.push(v);
    }

    /**
     * @brief Pops a vertex from the SCC stack.
     *
     * @return The vertex popped from the stack, or `-1` if the stack is empty.
     */
    public int popSccStack() {
        if (sccStack.isEmpty()) return -1;
        return sccStack.pop();
    }

    /**
     * @brief Depth-First Search (DFS) utility function.
     *
     * Traverses the graph from a given vertex using DFS.
     *
     * @param v The starting vertex.
     * @param visited Array tracking visited vertices.
     * @param graph The graph to traverse.
     */
    public void dfsUtil(int v, boolean[] visited, Map<Integer, List<Integer>> graph) {
        if (v < 0 || v >= visited.length) {
            System.out.println("Invalid vertex ID: " + v);
            return;
        }

        visited[v] = true;
        pushSccStack(v);

        if (graph.containsKey(v)) {
            for (int neighbor : graph.get(v)) {
                if (neighbor >= 0 && neighbor < visited.length && !visited[neighbor]) {
                    dfsUtil(neighbor, visited, graph);
                }
            }
        }
    }

    /**
     * @brief Finds and outputs all Strongly Connected Components (SCCs).
     *
     * Identifies SCCs in the given graph using Kosaraju's algorithm and appends
     * the results to the provided output.
     *
     * @param V The number of vertices in the graph.
     * @param graph The adjacency list of the graph.
     * @param output The StringBuilder object to store SCC output.
     */
    public void findSCCs(int V, Map<Integer, List<Integer>> graph, StringBuilder output) {
        boolean[] visited = new boolean[V];

        // Perform DFS and populate stack with finishing times
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                dfsUtil(i, visited, graph);
            }
        }

        // Create the transpose graph
        createTransposeGraph(graph);

        // Reset visited array
        Arrays.fill(visited, false);

        int numSCC = 0;

        // Process vertices in stack order
        while (!sccStack.isEmpty()) {
            int v = popSccStack();

            if (!visited[v]) {
                numSCC++;
                output.append("SCC #").append(numSCC).append(": ");

                dfsUtil(v, visited, transposeList);

                while (!sccStack.isEmpty() && visited[sccStack.peek()]) {
                    output.append(popSccStack()).append(" ");
                }
                output.append("\n");
            }
        }
    }

    /**
     * @brief Creates a transposed graph from the original adjacency list.
     *
     * Reverses the direction of edges in the graph to prepare for second DFS.
     *
     * @param graph The original adjacency list of the graph.
     */
    private void createTransposeGraph(Map<Integer, List<Integer>> graph) {
        for (int node : graph.keySet()) {
            for (int neighbor : graph.get(node)) {
                transposeList.computeIfAbsent(neighbor, k -> new ArrayList<>()).add(node);
            }
        }
    }

    /**
     * @brief Analyzes SCCs in a list of tasks.
     *
     * Converts the list of tasks into an adjacency list and identifies SCCs.
     *
     * @param taskList A list of tasks with dependencies.
     * @param output A StringBuilder to store SCC analysis results.
     */
    public void analyzeSCC(ArrayList<TaskInfo> taskList, StringBuilder output) {
        int V = taskList.size(); // Total number of vertices

        // Build adjacency list
        for (TaskInfo task : taskList) {
            int taskId = task.getId() - 1; // Convert to 0-based index
            if (!adjList.containsKey(taskId)) {
                adjList.put(taskId, new ArrayList<>());
            }

            for (int dependency : task.getDependencies()) {
                if (dependency != 0) {
                    adjList.get(taskId).add(dependency - 1); // Convert to 0-based index
                }
            }
        }

        // Find and output SCCs
        findSCCs(V, adjList, output);
    }
}
