package island.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import island.Tile.Tile;
import island.Tile.Type;
import pathfinder.graph.*;

public class CityGen {
    
    private Graph g = new Graph();

    public Graph generate(Mesh oMesh, List<Tile> tiles, int numCities, Random rnd) {
        // Create graph nodes for every non-water polygon centroid
        List<Node> potentialCities = new ArrayList<>();
        for (int i = 0; i < oMesh.getPolygonsCount(); i++) {
            // No civilization on water
            Tile t = tiles.get(i);
            if (t.getType().equals(Type.OCEAN.toString()) || t.getType().equals(Type.LAKE.toString()) || t.getType().equals(Type.LAGOON.toString()))
                continue;
            // Create graph node corresponding to land polygon centroid
            Polygon p = oMesh.getPolygons(i);
            Node n = new Node();
            n.add("vertexIndex", String.valueOf(p.getCentroidIdx()));
            n.add("isCity", "false");
            g.addNode(n);
            potentialCities.add(n);
        }

        for (int i = 0; i < numCities && !potentialCities.isEmpty(); i++) {
            Node n = potentialCities.remove(rnd.nextInt(potentialCities.size()));
            n.add("isCity", "true");
            n.add("size", "1");
        }

        return g;

    }

}
