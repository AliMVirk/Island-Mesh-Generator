package pathfinder.graph;

import java.util.List;

public class Graph {
    
    List<Node> N;
    List<Edge> E;

    public Graph(List<Node> nodes, List<Edge> edges) {
        N = nodes;
        E = edges;
    }

}
