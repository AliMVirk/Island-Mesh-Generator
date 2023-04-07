package pathfinder.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Graph {
    
    private HashMap<Node, List<Edge>> graph = new HashMap<>();
    public PathFinder path = new PathFinder(graph);

    public void addNode(Node n) {
        graph.putIfAbsent(n, new ArrayList<>());
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
        if (!graph.get(e.N1).contains(e))
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

}
