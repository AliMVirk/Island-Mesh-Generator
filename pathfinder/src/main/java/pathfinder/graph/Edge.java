package pathfinder.graph;

public class Edge {
    
    final double weight;
    final Node N1;
    final Node N2;

    public Edge(Node node_1, Node node_2, double edgeWeight) {
        N1 = node_1;
        N2 = node_2;
        weight = edgeWeight;
    }

}
