package island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import island.Tile.River;
import island.Tile.Tile;
import island.Tile.Type;
import island.Tiles.LandTile;
import island.generators.EnrichmentGen;
import island.generators.RiverGen;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnrichmentGenTest {

    private EnrichmentGen egen = new EnrichmentGen();

    @Test
    public void lakeHumiditySpreads() {
        // Create test mesh
        Polygon p1 = Polygon.newBuilder().addNeighborIdxs(0).setNeighborIdxs(0, 1).build();
        Polygon p2 = Polygon.newBuilder().addNeighborIdxs(0).setNeighborIdxs(0, 0).build();
        Polygon p3 = Polygon.newBuilder().build();
        Mesh aMesh = Mesh.newBuilder().addPolygons(p1).addPolygons(p2).addPolygons(p3).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new LandTile());
        tiles.add(new Tile(Type.WATER, null, 100));
        tiles.add(new LandTile());

        tiles = egen.enrichLand(aMesh, tiles, null);
        // Test humidity
        assertEquals(25, tiles.get(0).getHumidity());
        assertEquals(0, tiles.get(2).getHumidity());
        // Test moisture
        assertEquals(12.5, ((LandTile)tiles.get(0)).getMoisture());
        assertEquals(0, ((LandTile)tiles.get(2)).getMoisture());
        // Test vegetation
        assertEquals(6.25, ((LandTile)tiles.get(0)).getVegetation());
        assertEquals(0, ((LandTile)tiles.get(2)).getVegetation());
    }

    @Test
    public void riverHumiditySpreads() {
        // Create test mesh
        Vertex v1 = Vertex.newBuilder().setX(100).setY(100).build();
        Vertex v2 = Vertex.newBuilder().setX(250).setY(250).build();
        Segment s = Segment.newBuilder().setV1Idx(0).setV2Idx(1).build();
        Polygon p = Polygon.newBuilder().addSegmentIdxs(0).setSegmentIdxs(0, 0).build();
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addSegments(s).addPolygons(p).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new LandTile());

        // Create rivers
        RiverGen rgen = new RiverGen();
        River[] rivers = rgen.createRivers(aMesh, tiles, 1);

        tiles = egen.enrichLand(aMesh, tiles, rivers);
        // Test humidity
        assertEquals(rivers[0].getDischarge() * 5, tiles.get(0).getHumidity());
        // Test moisture
        assertEquals(rivers[0].getDischarge() * 2.5, ((LandTile)tiles.get(0)).getMoisture());
        // Test vegetation
        assertEquals(rivers[0].getDischarge() * 1.25, ((LandTile)tiles.get(0)).getVegetation());
    }

    @Test
    public void aquiferHumiditySpreads() {
        // Create test mesh
        Polygon p1 = Polygon.newBuilder().addNeighborIdxs(0).setNeighborIdxs(0, 1).build();
        Polygon p2 = Polygon.newBuilder().addNeighborIdxs(0).setNeighborIdxs(0, 0).build();
        Polygon p3 = Polygon.newBuilder().build();
        Mesh aMesh = Mesh.newBuilder().addPolygons(p1).addPolygons(p2).addPolygons(p3).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new LandTile());
        tiles.add(new LandTile(100)); // aquifer tile
        tiles.add(new LandTile());

        tiles = egen.enrichLand(aMesh, tiles, null);
        // Test humidity
        assertEquals(25, tiles.get(0).getHumidity());
        assertEquals(0, tiles.get(2).getHumidity());
        // Test moisture
        assertEquals(12.5, ((LandTile)tiles.get(0)).getMoisture());
        assertEquals(0, ((LandTile)tiles.get(2)).getMoisture());
        // Test vegetation
        assertEquals(6.25, ((LandTile)tiles.get(0)).getVegetation());
        assertEquals(0, ((LandTile)tiles.get(2)).getVegetation());
    }


}
