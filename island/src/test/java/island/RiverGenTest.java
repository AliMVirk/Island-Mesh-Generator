package island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import island.Tile.River;
import island.Tile.Tile;
import island.Tile.Type;
import island.generators.RiverGen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RiverGenTest {
    
    private RiverGen rgen = new RiverGen();

    @Test
    public void riverExists() {
        // Create test mesh
        Vertex v1 = Vertex.newBuilder().setX(100).setY(100).build();
        Vertex v2 = Vertex.newBuilder().setX(250).setY(250).build();
        Segment s = Segment.newBuilder().setV1Idx(0).setV2Idx(1).build();
        Polygon p = Polygon.newBuilder().addSegmentIdxs(0).setSegmentIdxs(0, 0).build();
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addSegments(s).addPolygons(p).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(Type.LAND, null));
        tiles.add(new Tile(Type.LAND, null));

        River[] rivers = rgen.createRivers(aMesh, tiles, 1, new Random());
        assertNotNull(rivers[0]);
    }

    @Test
    public void noRiverNearOcean() {
        // Create test mesh
        Vertex v1 = Vertex.newBuilder().setX(100).setY(100).build();
        Vertex v2 = Vertex.newBuilder().setX(250).setY(250).build();
        Segment s = Segment.newBuilder().setV1Idx(0).setV2Idx(1).build();
        Polygon p1 = Polygon.newBuilder().addNeighborIdxs(0).setNeighborIdxs(0, 1).addSegmentIdxs(0).setSegmentIdxs(0, 0).build();
        Polygon p2 = Polygon.newBuilder().addNeighborIdxs(0).setNeighborIdxs(0, 0).build();
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addSegments(s).addPolygons(p1).addPolygons(p2).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(Type.LAND, null));
        tiles.add(new Tile(Type.OCEAN, null));

        River[] rivers = rgen.createRivers(aMesh, tiles, 1, new Random());
        assertNull(rivers[0]);
    }

    @Test
    public void riverExtends() {
        // Create test mesh
        Vertex v1 = Vertex.newBuilder().setX(100).setY(100).build();
        Vertex v2 = Vertex.newBuilder().setX(250).setY(250).build();
        Vertex v3 = Vertex.newBuilder().setX(300).setY(300).build();
        Segment s1 = Segment.newBuilder().setV1Idx(1).setV2Idx(0).build(); // spring (v1 of any chosen segment) must connect to another segment if river is to extend
        Segment s2 = Segment.newBuilder().setV1Idx(1).setV2Idx(2).build();
        Polygon p1 = Polygon.newBuilder().addNeighborIdxs(0).setNeighborIdxs(0, 1).addSegmentIdxs(0).setSegmentIdxs(0, 0).build();
        Polygon p2 = Polygon.newBuilder().addNeighborIdxs(0).setNeighborIdxs(0, 0).addSegmentIdxs(0).setSegmentIdxs(0, 1).build();
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addVertices(v3).addSegments(s1).addSegments(s2).addPolygons(p1).addPolygons(p2).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(Type.LAND, null));
        tiles.add(new Tile(Type.LAND, null));
        // Set altitudes for extending rivers
        tiles.get(0).setAltitude(5);
        tiles.get(0).setAltitude(1);

        River[] rivers = rgen.createRivers(aMesh, tiles, 1, new Random());
        assertNotNull(rivers[1]); // river will either start at tile 1 and stay, or river will extend from tile 0
    }

    @Test
    public void checkDischarge() {
        // Create test mesh
        Vertex v1 = Vertex.newBuilder().setX(100).setY(100).build();
        Vertex v2 = Vertex.newBuilder().setX(250).setY(250).build();
        Segment s = Segment.newBuilder().setV1Idx(0).setV2Idx(1).build();
        Polygon p = Polygon.newBuilder().addSegmentIdxs(0).setSegmentIdxs(0, 0).build();
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addSegments(s).addPolygons(p).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(Type.LAND, null));

        // Get discharge for 1 river in 1 spot
        River[] rivers = rgen.createRivers(aMesh, tiles, 1, new Random()); // equals at most the largest floating point below 2
        double smallDischarge = rivers[0].getDischarge();
        // Get discharge for 4 rivers in 1 spot
        rivers[0] = null;
        rivers = rgen.createRivers(aMesh, tiles, 4, new Random());
        double largeDischarge = rivers[0].getDischarge(); // equals at least 2
        assertTrue(largeDischarge > smallDischarge);
    }

}
