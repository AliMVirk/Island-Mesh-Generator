package pathfinder.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class PathFinder implements ShortestPath {

    private HashMap<Node, List<Edge>> graph;

    public PathFinder(HashMap<Node, List<Edge>> graphRepresentation) {
        graph = graphRepresentation;
    }
    
    public List<Node> findPath(Node n1, Node n2) {
        // Check for given node existence
        if (!graph.containsKey(n1) || !graph.containsKey(n2))
            return null;

        resetNodeCost();
        HashMap<Node, Node> path = dijkstraShortestPath(n1);

        // Get shortest path between n1 and n2 as a list of nodes
        List<Node> shortestPath = new ArrayList<>();
        Node n = n2;
        shortestPath.add(n);
        while (n != null && !n.equals(n1)) {
            n = path.get(n);
            shortestPath.add(0, n);
        }
        // Path existence check
        if (n == null)
            return null;

        return shortestPath;
    }

    private void resetNodeCost() {
        graph.keySet().forEach(n -> n.cost = 0);
    }

    public HashMap<Node, Node> dijkstraShortestPath(Node s) {
        // Initialize path and cost
        HashMap<Node, Node> path = new HashMap<>();
        graph.keySet().forEach(n -> path.put(n, null));
        HashMap<Node, Double> cost = new HashMap<>();
        graph.keySet().forEach(n -> cost.put(n, Double.MAX_VALUE));
        // Initialize path/cost for n1
        path.put(s, s); cost.put(s, 0d);
        // Q = min priority queue, hold s with 0
        PriorityQueue<Node> q = new PriorityQueue<>(new Node());
        q.add(s);

        // Dijkstra
        while (!q.isEmpty()) {
            Node m = q.poll();
            for (Edge e : graph.get(m)) {
                Node n = e.N2;
                double nextCost = cost.get(m) + e.weight;
                if (nextCost < cost.get(n)) {
                    path.put(n, m);
                    cost.put(n, nextCost);
                    n.cost = cost.get(n);
                    q.add(n);
                }
            }
        }

        return path;
    }

}
