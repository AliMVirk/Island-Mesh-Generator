package pathfinder.graph;

import java.util.HashMap;
import java.util.List;

public interface ShortestPath {
    
    public List<Node> findPath(Node n1, Node n2);
    public HashMap<Node, Node> dijkstraShortestPath(Node s);

}
