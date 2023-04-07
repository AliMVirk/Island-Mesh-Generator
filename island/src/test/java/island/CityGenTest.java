package island;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals("1", g.getNodes().get(0).get("size"));        
    }

    @Test
    public void graphIsDense() {
        // Create test
        List<Vertex> vertices = new ArrayList<>();
        List<Polygon> polygons = new ArrayList<>();
        int numNodes = new Random().nextInt(100);
        for (int i = 0; i < numNodes; i++) {
            vertices.add(Vertex.newBuilder().setX(0).setY(0).build());
            polygons.add(Polygon.newBuilder().setCentroidIdx(i).build());
        }
        Mesh aMesh = Mesh.newBuilder().addAllVertices(vertices).addAllPolygons(polygons).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < numNodes; i++)
            tiles.add(new LandTile());
        
        Graph g = cgen.generate(aMesh, tiles, numNodes, new Random());
        assertEquals(numNodes * numNodes - numNodes, g.getEdges().size());
    }

}
