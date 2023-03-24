package island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import island.Tile.Tile;
import island.Tile.Type;
import island.Tiles.LandTile;
import island.generators.EnrichmentGen;

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

}
