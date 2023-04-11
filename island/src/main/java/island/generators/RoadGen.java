package island.generators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
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
        cities.remove(capital);
        Property segColor = Property.newBuilder().setKey("rgb_color").setValue("125,128,124").build();
        Property segThickness = Property.newBuilder().setKey("thickness").setValue("4").build();
        for (Node n : cities) {
            List<Node> path = g.path.findPath(capital, n);
            if (path == null) continue; // no path exists
            for (int i = 0; i < path.size() - 1; i++) {
                Segment s = Segment.newBuilder().setV1Idx(Integer.parseInt(path.get(i).get("vertexIndex"))).setV2Idx(Integer.parseInt(path.get(i+1).get("vertexIndex"))).addProperties(segColor).addProperties(segThickness).build();
                if (!segmentExists(s, segments))
                    segments.add(s);
            }
        }

        return segments;
    }

    // For preventing addition of duplicate segments
    private boolean segmentExists(Segment seg, List<Segment> segments) {
        for (Segment s : segments) {
            if (s.getV1Idx() == seg.getV1Idx() && s.getV2Idx() == seg.getV2Idx()) return true;
        }
        return false;
    }

}
