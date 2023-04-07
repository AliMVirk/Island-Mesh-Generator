package pathfinder.graph;

import java.util.HashMap;

// Container class for accessing both path and cost maps from Dijsktra's shortest path algorithm
public class NodePath {
    
    private HashMap<Node, Node> path;
    private HashMap<Node, Double> cost;

    public NodePath(HashMap<Node, Node> path, HashMap<Node, Double> cost) {
        this.path = path;
        this.cost = cost;
    }

    public HashMap<Node, Node> getPath() {
        return new HashMap<>(path);
    }

    public HashMap<Node, Double> getCost() {
        return new HashMap<>(cost);
    }

}
