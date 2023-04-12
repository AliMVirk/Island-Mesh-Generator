package island;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import island.Tile.Tile;
import island.Tiles.LandTile;
import island.generators.CityGen;
import island.generators.RoadGen;
import pathfinder.graph.Graph;

public class RoadGenTest {
    
    private CityGen cgen = new CityGen();
    private RoadGen rgen = new RoadGen();

    @Test
    public void noRoadsWithNoCities() {
        List<Segment> segments = rgen.generate(Mesh.newBuilder().build(), new Graph());
        assertTrue(segments.isEmpty());
    }

    @Test
    public void noRoadsWithOneCity() {
        // Create test mesh
        Vertex v1 = Vertex.newBuilder().setX(0).setY(0).build();
        Vertex v2 = Vertex.newBuilder().setX(0).setY(5).build();
        Vertex v3 = Vertex.newBuilder().setX(0).setY(10).build();
        Polygon p1 = Polygon.newBuilder().setCentroidIdx(0).addNeighborIdxs(0).addNeighborIdxs(1).setNeighborIdxs(0, 1).setNeighborIdxs(1, 2).build();
        Polygon p2 = Polygon.newBuilder().setCentroidIdx(1).addNeighborIdxs(0).addNeighborIdxs(1).setNeighborIdxs(0, 0).setNeighborIdxs(1, 2).build();
        Polygon p3 = Polygon.newBuilder().setCentroidIdx(2).addNeighborIdxs(0).addNeighborIdxs(1).setNeighborIdxs(0, 0).setNeighborIdxs(1, 1).build();
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addVertices(v3).addPolygons(p1).addPolygons(p2).addPolygons(p3).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new LandTile());
        tiles.add(new LandTile());
        tiles.add(new LandTile());

        // Create corresponding cities
        Graph g = cgen.generate(aMesh, tiles, 1, new Random());

        List<Segment> segments = rgen.generate(aMesh, g);
        assertTrue(segments.isEmpty());
    }

    @Test
    public void roadsExist() {
        // Create test mesh
        Vertex v1 = Vertex.newBuilder().setX(0).setY(0).build();
        Vertex v2 = Vertex.newBuilder().setX(0).setY(15).build();
        Polygon p1 = Polygon.newBuilder().setCentroidIdx(0).addNeighborIdxs(0).setNeighborIdxs(0, 1).build();
        Polygon p2 = Polygon.newBuilder().setCentroidIdx(1).addNeighborIdxs(0).setNeighborIdxs(0, 0).build();
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addPolygons(p1).addPolygons(p2).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new LandTile());
        tiles.add(new LandTile());

        // Create corresponding cities
        Graph g = cgen.generate(aMesh, tiles, 2, new Random());
        
        List<Segment> segments = rgen.generate(aMesh, g);
        assertEquals(1, segments.size());
        assertTrue(segments.get(0).getV1Idx() == 0 && segments.get(0).getV2Idx() == 1 || segments.get(0).getV1Idx() == 1 && segments.get(0).getV2Idx() == 0);
    }

}
