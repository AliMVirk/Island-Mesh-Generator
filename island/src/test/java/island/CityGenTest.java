package island;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import island.Tile.Tile;
import island.Tile.Type;
import island.Tiles.LandTile;
import island.generators.CityGen;
import pathfinder.graph.Edge;
import pathfinder.graph.Graph;

public class CityGenTest {
    
    private CityGen cgen = new CityGen();

    @Test
    public void landNodesExist() {
        // Create test mesh
        Vertex v1 = Vertex.newBuilder().setX(0).setY(0).build();
        Vertex v2 = Vertex.newBuilder().setX(250).setY(250).build();
        Polygon p1 = Polygon.newBuilder().setCentroidIdx(0).build(); // Land polygon
        Polygon p2 = Polygon.newBuilder().setCentroidIdx(1).build(); // Non-land polygon
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addPolygons(p1).addPolygons(p2).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new LandTile());
        tiles.add(new Tile(Type.OCEAN, null));

        Graph g = cgen.generate(aMesh, tiles, 0, new Random());
        assertEquals(1, g.getNodes().size());
        assertEquals("0", g.getNodes().get(0).get("vertexIndex"));
    }

    @Test
    public void citiesExist() {
        // Create test mesh
        Vertex v1 = Vertex.newBuilder().setX(0).setY(0).build();
        Polygon p1 = Polygon.newBuilder().setCentroidIdx(0).build();
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addPolygons(p1).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new LandTile());

        Graph g = cgen.generate(aMesh, tiles, 1, new Random());
        assertEquals("true", g.getNodes().get(0).get("isCity"));        
    }

    @Test
    public void edgesBetweenNeighbors() {
        // Create test
        Vertex v1 = Vertex.newBuilder().setX(0).setY(0).build();
        Vertex v2 = Vertex.newBuilder().setX(0).setY(0).build();
        Vertex v3 = Vertex.newBuilder().setX(0).setY(0).build();
        Polygon p1 = Polygon.newBuilder().setCentroidIdx(0).addNeighborIdxs(0).setNeighborIdxs(0, 1).build();
        Polygon p2 = Polygon.newBuilder().setCentroidIdx(1).addNeighborIdxs(0).setNeighborIdxs(0, 0).build();
        Polygon p3 = Polygon.newBuilder().setCentroidIdx(2).build();
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addVertices(v3).addPolygons(p1).addPolygons(p2).addPolygons(p3).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new LandTile());
        tiles.add(new LandTile());
        tiles.add(new LandTile());
        
        Graph g = cgen.generate(aMesh, tiles, 3, new Random());
        List<Edge> edges = g.getEdges();
        assertEquals(2, edges.size());
        assertEquals(edges.get(0).getN1().get("polygonIndex"), edges.get(1).getN2().get("polygonIndex"));
    }

}
