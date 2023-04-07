package pathfinder.graph;

import java.util.List;

public interface ShortestPath {
    
    public List<Node> findPath(Node n1, Node n2);
    public NodePath dijkstraShortestPath(Node s);

}
