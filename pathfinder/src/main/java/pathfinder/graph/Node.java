package pathfinder.graph;

import java.util.HashMap;

public class Node {
    
    HashMap<String, String> attributes = new HashMap<>();
    
    public void add(String key, String value) {
        attributes.put(key, value);
    }

    public String get(String key) {
        return attributes.get(key);
    }

}
