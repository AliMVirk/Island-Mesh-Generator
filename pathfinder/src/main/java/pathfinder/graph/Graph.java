package pathfinder.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Graph {
    
    private HashMap<Node, List<Edge>> graph = new HashMap<>();

    public void addNode(Node n) {
        graph.put(n, new ArrayList<>());
    }

    public void removeNode(Node n) {
        graph.remove(n);
    }

    public void addEdge(Edge e) {
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
        return new ArrayList<>(edges);
    }

}
