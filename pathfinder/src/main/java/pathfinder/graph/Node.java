package pathfinder.graph;

import java.util.Comparator;
import java.util.HashMap;

public class Node implements Comparator<Node> {
    
    private HashMap<String, String> attributes = new HashMap<>();
    double cost = 0;
    
    public void add(String key, String value) {
        attributes.putIfAbsent(key, value);
    }

    public String get(String key) {
        return attributes.get(key);
    }

    @Override
    // Allow nodes to be comparable for priority queue usage for shortest paths
    public int compare(Node node1, Node node2) {
        if (node1.cost < node2.cost)
            return -1;
        else if (node1.cost > node2.cost)
            return 1;
        else
            return 0;
    }

}
