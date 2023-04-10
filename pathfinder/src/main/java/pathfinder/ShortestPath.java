package pathfinder;

import java.util.List;

import pathfinder.graph.Node;

public interface ShortestPath {
    
    public List<Node> findPath(Node n1, Node n2);
    public NodePath dijkstraShortestPath(Node s);

}
