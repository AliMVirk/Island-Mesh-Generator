package island.generators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import island.Tile.Tile;
import pathfinder.graph.*;

public class RoadGen {
    
    public List<Segment> generate(Mesh oMesh, List<Tile> tiles, Graph g) {
        // Iterate through cities to find the most central
        Node capital = null;
        for (Node n : g.getNodes()) {
            double minMaxPath = Double.MAX_VALUE;
            if (Boolean.parseBoolean(n.get("isCity"))) {
                HashMap<Node, Double> cost = g.path.dijkstraShortestPath(n).getCost();
                double maxCostPath = 0;
                for (Node m : cost.keySet()) {
                    if (Boolean.parseBoolean(m.get("isCity")) && cost.get(m) > maxCostPath)
                        maxCostPath = cost.get(m);
                }
                if (maxCostPath == 0) // there exists only one city
                    return new ArrayList<>();
                else if (maxCostPath < minMaxPath) {
                    minMaxPath = maxCostPath;
                    capital = n;
                }
            }
        }
        // No cities exist
        if (capital == null) return new ArrayList<>();
        
        // Create paths from the capital to every other city
        

        return new ArrayList<>();
    }

}
