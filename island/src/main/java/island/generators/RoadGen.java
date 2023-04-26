package island.generators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import pathfinder.graph.*;

public class RoadGen {
    
    public List<Segment> generate(Mesh oMesh, Graph g) {
        List<Segment> segments = new ArrayList<>();

        // Iterate through cities to find the most central
        Node capital = null;
        List<Node> cities = new ArrayList<>();
        double minMaxPath = Double.MAX_VALUE;
        for (Node n : g.getNodes()) {
            if (Boolean.parseBoolean(n.get("isCity"))) {
                n.add("isCapital", "false");
                cities.add(n); // to avoid looping over all nodes again
                HashMap<Node, Double> cost = g.path.dijkstraShortestPath(n).getCost();
                double maxPathCost = 0;
                for (Node m : cost.keySet()) {
                    double pathCost = cost.get(m);
                    if (Boolean.parseBoolean(m.get("isCity")) && pathCost != Double.MAX_VALUE && pathCost > maxPathCost)
                        maxPathCost = cost.get(m);
                }
                if (maxPathCost != 0 && maxPathCost < minMaxPath) {
                    minMaxPath = maxPathCost;
                    capital = n;
                }
            }
        }
        if (capital == null || minMaxPath == 0) return segments; // no possible city connections exist
        
        // Create paths from the capital to every other city
        capital.add("isCapital", "true");
        capital.add("size", "20");
        cities.remove(capital);
        for (Node n : cities) {
            List<Node> path = g.path.findPath(capital, n);
            if (path == null) continue; // no path exists
            segments.addAll(createRoad(segments, path, "125,128,124", "7"));
        }

        // Create paths from random cities to a neighbouring city
        for (Node n : cities) {
            if (!n.equals(capital)) {
                // Find closest city to n
                Vertex v1 = oMesh.getVertices(Integer.parseInt(n.get("vertexIndex")));
                Node end = null;
                double minDistance = Double.MAX_VALUE;
                for (Node m : cities) {
                    Vertex v2 = oMesh.getVertices(Integer.parseInt(m.get("vertexIndex")));
                    if (!n.equals(m) && !m.equals(capital) && Math.sqrt(Math.pow(v1.getX() - v2.getX(), 2) + Math.pow(v1.getY() - v2.getY(), 2)) < minDistance) {
                        end = m;
			minDistance = Math.sqrt(Math.pow(v1.getX() - v2.getX(), 2) + Math.pow(v1.getY() - v2.getY(), 2));
                    }
                }
                if (end == null) continue;
                // Create road
                List<Node> path = g.path.findPath(n, end);
                if (path == null) continue; // no path exists
                segments.addAll(createRoad(segments, path, "162,168,160", "2"));
            }
        }

        return segments;
    }

    private List<Segment> createRoad(List<Segment> existingRoads, List<Node> path, String color, String thickness) {
        List<Segment> segments = new ArrayList<>();
        Property segColor = Property.newBuilder().setKey("rgb_color").setValue(color).build();
        Property segThickness = Property.newBuilder().setKey("thickness").setValue(thickness).build();

        for (int i = 0; i < path.size() - 1; i++) {
            Segment s = Segment.newBuilder().setV1Idx(Integer.parseInt(path.get(i).get("vertexIndex"))).setV2Idx(Integer.parseInt(path.get(i+1).get("vertexIndex"))).addProperties(segColor).addProperties(segThickness).build();
            if (!segmentExists(s, existingRoads))
                segments.add(s);
        }
        return segments;
    }

    // For preventing addition of duplicate segments
    private boolean segmentExists(Segment seg, List<Segment> segments) {
        for (Segment s : segments) {
            if (s.getV1Idx() == seg.getV1Idx() && s.getV2Idx() == seg.getV2Idx() || s.getV1Idx() == seg.getV2Idx() && s.getV2Idx() == seg.getV1Idx()) return true;
        }
        return false;
    }

}
