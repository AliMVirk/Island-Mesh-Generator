package pathfinder.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    
    private List<Node> N;
    private List<Edge> E;

    public Graph(List<Node> nodes, List<Edge> edges) {
        N = nodes;
        E = edges;
    }

    public void addNode(Node n) {
        N.add(n);
    }

    public void removeNode(Node n) {
        N.remove(n);
    }

    public void addEdge(Edge e) {
        E.add(e);
    }

    public void removeEdge(Edge e) {
        E.remove(e);
    }

    public List<Node> getNodes() {
        return new ArrayList<>(N);
    }

    public List<Edge> getEdges() {
        return new ArrayList<>(E);
    }

}
