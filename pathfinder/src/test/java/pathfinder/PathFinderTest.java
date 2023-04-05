package pathfinder;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import pathfinder.graph.*;

import java.util.Arrays;
import java.util.List;

public class PathFinderTest {

    private Graph g = new Graph();

    @Test
    public void graphIsNotNull() {
        assertNotNull(g);
    }

    @Test
    public void graphIsEmpty() {
        assertEquals(0, g.getNodes().size());
        assertEquals(0, g.getEdges().size());
    }

    @Test
    public void canAddNode() {
        Node n = new Node();
        g.addNode(n);
        assertEquals(n, g.getNodes().get(0));
    }

    @Test
    public void canAddEdge() {
        Edge e = new Edge(new Node(), new Node(), 0);
        g.addEdge(e);
        assertEquals(e, g.getEdges().get(0));
    }

    @Test
    public void canRemoveNode() {
        Node n = new Node();
        g.addNode(n);
        g.removeNode(n);
    }

    @Test
    public void canRemoveEdge() {
        Edge e = new Edge(new Node(), new Node(), 0);
        g.addEdge(e);
        g.removeEdge(e);
    }

    @Test
    public void noPathInEmptyGraph() {
        List<Node> path = g.path.findPath(new Node(), new Node());
        assertNull(path);
    }
    
    @Test
    public void noPathBetweenNodes() {
        // Store references to nodes
        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();
        // Create test graph
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.addEdge(new Edge(n1, n2, 3));
        
        List<Node> path = g.path.findPath(n1, n3);
        assertNull(path);
    }

    @Test
    public void oneNodePath() {
        // Store references to nodes
        Node n1 = new Node();
        // Create test graph
        g.addNode(n1);
        g.addEdge(new Edge(n1, n1, 1));
        
        List<Node> generatedShortestPath = g.path.findPath(n1, n1);
        List<Node> calculatedShortestPath = Arrays.asList(n1);
        assertIterableEquals(calculatedShortestPath, generatedShortestPath);
    }

    @Test
    public void getShortestPath1() {
        // Store references to nodes
        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();
        // Create test graph
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.addEdge(new Edge(n1, n2, 3));
        g.addEdge(new Edge(n2, n3, 5));
        g.addEdge(new Edge(n1, n3, 4));

        List<Node> generatedShortestPath = g.path.findPath(n1, n3);
        List<Node> calculatedShortestPath = Arrays.asList(n1, n3);
        assertIterableEquals(calculatedShortestPath, generatedShortestPath);
    }

    @Test
    public void getShortestPath2() {
        // Store references to nodes
        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();
        Node n4 = new Node();
        Node n5 = new Node();
        // Create test graph
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.addNode(n4);
        g.addNode(n5);
        g.addEdge(new Edge(n1, n2, 1));
        g.addEdge(new Edge(n2, n3, 1));
        g.addEdge(new Edge(n3, n4, 1));
        g.addEdge(new Edge(n4, n5, 1));
        g.addEdge(new Edge(n5, n1, 1));
        g.addEdge(new Edge(n3, n2, 1));
        g.addEdge(new Edge(n2, n4, 1));
        g.addEdge(new Edge(n2, n5, 1));

        List<Node> generatedShortestPath = g.path.findPath(n1, n5);
        List<Node> calculatedShortestPath = Arrays.asList(n1, n2, n5);
        assertIterableEquals(calculatedShortestPath, generatedShortestPath);
    }

}
