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

    public List<Node> getNodes() {
        return new ArrayList<>(N);
    }

    public List<Edge> getEdges() {
        return new ArrayList<>(E);
    }

}
