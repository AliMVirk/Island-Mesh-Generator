package pathfinder.graph;

public class Edge {
    
    final Node N1;
    final Node N2;
    final double weight;

    public Edge(Node node1, Node node2, double edgeWeight) {
        N1 = node1;
        N2 = node2;
        weight = edgeWeight;
    }

    public Node getN1() {
        return N1;
    }

    public Node getN2() {
        return N2;
    }

}
