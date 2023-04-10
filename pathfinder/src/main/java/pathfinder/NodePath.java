package pathfinder;

import java.util.HashMap;

import pathfinder.graph.Node;

// Container class for accessing both path and cost maps from Dijsktra's shortest path algorithm
public class NodePath {
    
    private final HashMap<Node, Node> path;
    private final HashMap<Node, Double> cost;

    public NodePath(HashMap<Node, Node> path, HashMap<Node, Double> cost) {
        this.path = path;
        this.cost = cost;
    }

    public HashMap<Node, Node> getPath() {
        return path;
    }

    public HashMap<Node, Double> getCost() {
        return cost;
    }

}
