package com.abdul.fatma.hamza.sahan.task;
import java.util.*;

public class SCCAnalyzer {
    private Stack<Integer> sccStack = new Stack<>(); // SCC için yığın
    private Map<Integer, List<Integer>> adjList = new HashMap<>(); // Adjacency List
    private Map<Integer, List<Integer>> transposeList = new HashMap<>(); // Transpose List

    // SCC Stack'e eleman ekle
    public void pushSccStack(int v) {
        sccStack.push(v);
    }

    // SCC Stack'ten eleman çıkar
    public int popSccStack() {
        if (sccStack.isEmpty()) return -1;
        return sccStack.pop();
    }

    public void dfsUtil(int v, boolean[] visited, Map<Integer, List<Integer>> graph) {
        // ID kontrolü: v geçerli bir vertex ID'si mi?
        if (v < 0 || v >= visited.length) {
            System.out.println("Invalid vertex ID: " + v);
            return;
        }

        visited[v] = true;
        pushSccStack(v);

        // Komşuları kontrol et
        if (graph.containsKey(v)) {
            for (int neighbor : graph.get(v)) {
                if (neighbor >= 0 && neighbor < visited.length && !visited[neighbor]) {
                    dfsUtil(neighbor, visited, graph);
                }
            }
        }
    }


    // Tüm SCC'leri Bulma
    public void findSCCs(int V, Map<Integer, List<Integer>> graph, StringBuilder output) {
        boolean[] visited = new boolean[V];

        // İlk DFS ile bitiş sırasını stack'e ekle
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                dfsUtil(i, visited, graph);
            }
        }

        // Transpose Grafiği Oluştur
        createTransposeGraph(graph);

        // Ziyaret edilenleri sıfırla
        Arrays.fill(visited, false);

        int numSCC = 0;

        // Stack'teki elemanlara göre SCC bul
        while (!sccStack.isEmpty()) {
            int v = popSccStack();

            if (!visited[v]) {
                numSCC++;
                output.append("SCC #").append(numSCC).append(": ");

                dfsUtil(v, visited, transposeList);

                // SCC elemanlarını yazdır
                while (!sccStack.isEmpty() && visited[sccStack.peek()]) {
                    output.append(popSccStack()).append(" ");
                }
                output.append("\n");
            }
        }
    }

    // Transpose Grafiği Oluştur
    private void createTransposeGraph(Map<Integer, List<Integer>> graph) {
        for (int node : graph.keySet()) {
            for (int neighbor : graph.get(node)) {
                transposeList.computeIfAbsent(neighbor, k -> new ArrayList<>()).add(node);
            }
        }
    }

    public void analyzeSCC(ArrayList<TaskInfo> taskList, StringBuilder output) {
        int V = taskList.size(); // Toplam vertex sayısı

        // Adjacency List oluştur
        for (TaskInfo task : taskList) {
            int taskId = task.getId() - 1; // Vertex ID (0 tabanlı)
            if (!adjList.containsKey(taskId)) {
                adjList.put(taskId, new ArrayList<>()); // Vertex için boş bir liste oluştur
            }

            for (int dependency : task.getDependencies()) {
                if (dependency != 0) { // Geçerli bir bağımlılık varsa ekle
                    adjList.get(taskId).add(dependency - 1); // 0 tabanlı ID
                }
            }
        }

        // SCC'leri bul ve çıktı üret
        findSCCs(V, adjList, output);
    }


}

