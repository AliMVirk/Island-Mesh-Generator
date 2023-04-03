package pathfinder.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Map.Entry;

public class Graph implements ShortestPath {
    
    private HashMap<Node, List<Edge>> graph = new HashMap<>();

    public void addNode(Node n) {
        graph.put(n, new ArrayList<>());
    }

    public void removeNode(Node n) {
        graph.remove(n); // Removes all outgoing edges from n
        // Removes all incoming edges to n
        for (Edge e : getEdges()) {
            if (e.N2.equals(n))
                removeEdge(e);
        }
    }

    public void addEdge(Edge e) {
        if (!graph.containsKey(e.N1))
            addNode(e.N1);
        if (!graph.containsKey(e.N2))
            addNode(e.N2);
        graph.get(e.N1).add(e);
    }

    public void removeEdge(Edge e) {
        graph.get(e.N1).remove(e);
    }

    public List<Node> getNodes() {
        return new ArrayList<>(graph.keySet());
    }

    public List<Edge> getEdges() {
        ArrayList<Edge> edges = new ArrayList<>();
        for (Entry<Node, List<Edge>> entry : graph.entrySet()) {
            for (Edge e : entry.getValue()) {
                edges.add(e);
            }
        }
        return edges;
    }

    public List<Node> findPath(Node n1, Node n2) {
        // Check for given node existence
        if (!graph.containsKey(n1) || !graph.containsKey(n2))
            return null;

        resetNodeCost();
        HashMap<Node, Node> path = dijkstraShortestPath(n1);

        // Get shortest path between n1 and n2 as a list of nodes
        List<Node> shortestPath = new ArrayList<>();
        Node n = n2;
        shortestPath.add(n);
        while (n != null && !n.equals(n1)) {
            n = path.get(n);
            shortestPath.add(0, n);
        }
        // Path existence check
        if (n == null)
            return null;

        return shortestPath;
    }

    private void resetNodeCost() {
        graph.keySet().forEach(n -> n.cost = 0);
    }

    private HashMap<Node, Node> dijkstraShortestPath(Node s) {
        // Initialize path and cost
        HashMap<Node, Node> path = new HashMap<>();
        graph.keySet().forEach(n -> path.put(n, null));
        HashMap<Node, Double> cost = new HashMap<>();
        graph.keySet().forEach(n -> cost.put(n, Double.MAX_VALUE));
        // Initialize path/cost for n1
        path.put(s, s); cost.put(s, 0d);
        // Q = min priority queue, hold s with 0
        PriorityQueue<Node> q = new PriorityQueue<>(new Node());
        q.add(s);

        // Dijkstra
        while (!q.isEmpty()) {
            Node m = q.poll();
            for (Edge e : graph.get(m)) {
                Node n = e.N2;
                double nextCost = cost.get(m) + e.weight;
                if (nextCost < cost.get(n)) {
                    path.put(n, m);
                    cost.put(n, nextCost);
                    n.cost = cost.get(n);
                    q.add(n);
                }
            }
        }

        return path;
    }

}
