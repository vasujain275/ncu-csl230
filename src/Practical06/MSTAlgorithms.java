package Practical06;

import java.util.*;

// Edge class represents an edge in the graph.
class Edge implements Comparable<Edge> {
    int src, dest, weight;

    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    // This method enables sorting edges by weight.
    @Override
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

// Graph class holds the graph using both an edge list (for Kruskal's)
// and an adjacency list (for Prim's).
class Graph {
    int V; // Number of vertices
    ArrayList<Edge> edges; // List of edges for Kruskal's algorithm
    ArrayList<ArrayList<Edge>> adjList; // Adjacency list for Prim's algorithm

    // Constructor to initialize the graph with V vertices
    public Graph(int V) {
        this.V = V;
        edges = new ArrayList<>();
        adjList = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    // Adds an undirected edge between source and destination.
    public void addEdge(int src, int dest, int weight) {
        // Create an edge and add to the overall edge list.
        Edge edge = new Edge(src, dest, weight);
        edges.add(edge);
        // For an undirected graph, add edges in both directions.
        adjList.get(src).add(new Edge(src, dest, weight));
        adjList.get(dest).add(new Edge(dest, src, weight));
    }

    // ---------------------------
    // Prim's Algorithm
    // ---------------------------
    public void primMST() {
        // Boolean array to mark vertices already included in MST.
        boolean[] inMST = new boolean[V];
        // key[i] holds the minimum weight edge to connect vertex i to the MST.
        int[] key = new int[V];
        // parent[i] will store the MST structure.
        int[] parent = new int[V];

        // Initialize all keys to "infinity" and parent to -1.
        Arrays.fill(key, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        // Start from vertex 0 by setting its key to 0.
        key[0] = 0;

        // Repeat V-1 times (since MST includes V-1 edges)
        for (int count = 0; count < V - 1; count++) {
            // Find the vertex not yet included in MST with minimum key value.
            int u = -1, min = Integer.MAX_VALUE;
            for (int v = 0; v < V; v++) {
                if (!inMST[v] && key[v] < min) {
                    min = key[v];
                    u = v;
                }
            }
            // If no valid vertex is found, break early.
            if (u == -1)
                break;

            // Include the vertex in MST.
            inMST[u] = true;

            // Update key and parent for adjacent vertices of u.
            for (Edge edge : adjList.get(u)) {
                int v = edge.dest;
                // Only update if v is not yet included and the edge weight is less than current key.
                if (!inMST[v] && edge.weight < key[v]) {
                    key[v] = edge.weight;
                    parent[v] = u;
                }
            }
        }

        // Display the MST produced by Prim's algorithm.
        System.out.println("\nPrim's MST:");
        int totalWeight = 0;
        // We start at 1 because vertex 0 is the starting point.
        for (int i = 1; i < V; i++) {
            System.out.println(parent[i] + " - " + i + " \tweight: " + key[i]);
            totalWeight += key[i];
        }
        System.out.println("Total weight: " + totalWeight);
    }

    // ---------------------------
    // Kruskal's Algorithm
    // ---------------------------
    public void kruskalMST() {
        // Sort all edges based on their weight.
        Collections.sort(edges);

        // Union-Find (Disjoint Set) data structures.
        int[] parent = new int[V];
        int[] rank = new int[V];
        for (int i = 0; i < V; i++) {
            parent[i] = i; // Each vertex is its own parent initially.
            rank[i] = 0;   // Rank is used to keep the tree flat.
        }

        ArrayList<Edge> result = new ArrayList<>(); // To store the resulting MST edges.
        int edgeCount = 0, i = 0;

        // Keep adding edges until we have V-1 edges or we run out.
        while (edgeCount < V - 1 && i < edges.size()) {
            // Pick the smallest edge.
            Edge edge = edges.get(i++);
            int x = find(parent, edge.src);
            int y = find(parent, edge.dest);

            // If adding this edge doesn't form a cycle, include it.
            if (x != y) {
                result.add(edge);
                union(parent, rank, x, y);
                edgeCount++;
            }
        }

        // Display the MST produced by Kruskal's algorithm.
        System.out.println("\nKruskal's MST:");
        int totalWeight = 0;
        for (Edge edge : result) {
            System.out.println(edge.src + " - " + edge.dest + " \tweight: " + edge.weight);
            totalWeight += edge.weight;
        }
        System.out.println("Total weight: " + totalWeight);
    }

    // Find the set (with path compression) of element i.
    private int find(int[] parent, int i) {
        if (parent[i] != i) {
            parent[i] = find(parent, parent[i]); // Path compression.
        }
        return parent[i];
    }

    // Union the sets of x and y using union by rank.
    private void union(int[] parent, int[] rank, int x, int y) {
        int xRoot = find(parent, x);
        int yRoot = find(parent, y);

        if (rank[xRoot] < rank[yRoot]) {
            parent[xRoot] = yRoot;
        } else if (rank[xRoot] > rank[yRoot]) {
            parent[yRoot] = xRoot;
        } else {
            parent[yRoot] = xRoot;
            rank[xRoot]++;
        }
    }
}

public class MSTAlgorithms {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read the number of vertices from the user.
        System.out.print("Enter number of vertices: ");
        int V = scanner.nextInt();

        // Read the number of edges.
        System.out.print("Enter number of edges: ");
        int E = scanner.nextInt();

        // Create a graph with V vertices.
        Graph graph = new Graph(V);

        // Read each edge from the user.
        System.out.println("Enter each edge in format: source destination weight");
        for (int i = 0; i < E; i++) {
            int src = scanner.nextInt();
            int dest = scanner.nextInt();
            int weight = scanner.nextInt();
            graph.addEdge(src, dest, weight);
        }

        // Run Prim's algorithm to compute and display the MST.
        graph.primMST();

        // Run Kruskal's algorithm to compute and display the MST.
        graph.kruskalMST();

        scanner.close();
    }
}

