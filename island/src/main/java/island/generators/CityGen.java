package island.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
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
            n.add("polygonIndex", String.valueOf(i));
            n.add("vertexIndex", String.valueOf(p.getCentroidIdx()));
            n.add("isCity", "false");
            g.addNode(n);
            potentialCities.add(n);
        }

        // Create edges between all neighbouring nodes
        for (Node n : g.getNodes()) {
            for (int i : oMesh.getPolygons(Integer.parseInt(n.get("polygonIndex"))).getNeighborIdxsList()) {
                for (Node m : g.getNodes()) {
                    if (Integer.parseInt(m.get("polygonIndex")) == i) {
                        g.addEdge(new Edge(n, m, findEdgeDistance(oMesh, n, m)));
                        break;
                    }
                }
            }
        }

        // Create cities from randomly selected nodes
        for (int i = 0; i < numCities && !potentialCities.isEmpty(); i++) {
            Node n = potentialCities.remove(rnd.nextInt(potentialCities.size()));
            n.add("isCity", "true");
            n.add("size", String.valueOf(rnd.nextDouble(5)+5));
        }

        return g;
    }

    private double findEdgeDistance(Mesh mesh, Node n1, Node n2) {
        Vertex v1 = mesh.getVertices(Integer.parseInt(n1.get("vertexIndex")));
        Vertex v2 = mesh.getVertices(Integer.parseInt(n2.get("vertexIndex")));
        return Math.sqrt(Math.pow(v1.getX() - v2.getX(), 2) + Math.pow(v1.getY() - v2.getY(), 2));
    }

}
