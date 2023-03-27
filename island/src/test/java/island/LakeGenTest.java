package island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import island.Tile.Tile;
import island.Tile.Type;
import island.generators.LakeGen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LakeGenTest {

    private LakeGen lgen = new LakeGen();

    @Test
    public void lakeExists() {
        // Create test mesh
        Vertex v1 = Vertex.newBuilder().setX(0).setY(0).build();
        Vertex v2 = Vertex.newBuilder().setX(250).setY(250).build();
        Polygon p1 = Polygon.newBuilder().setCentroidIdx(0).addNeighborIdxs(0).setNeighborIdxs(0, 1).build();
        Polygon p2 = Polygon.newBuilder().setCentroidIdx(1).addNeighborIdxs(0).setNeighborIdxs(0, 0).build();
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addPolygons(p1).addPolygons(p2).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(Type.LAND, null));
        tiles.add(new Tile(Type.LAND, null));

        tiles = lgen.transform(aMesh, tiles, 1, new Random());
        boolean lakeExists = tiles.get(0).getType().equals(Type.LAKE.toString()) || tiles.get(1).getType().equals(Type.LAKE.toString());
        assertTrue(lakeExists);
    }

    @Test
    public void noLakeByOcean() {
        // Create test mesh
        Vertex v1 = Vertex.newBuilder().setX(0).setY(0).build();
        Vertex v2 = Vertex.newBuilder().setX(250).setY(250).build();
        Polygon p1 = Polygon.newBuilder().setCentroidIdx(0).addNeighborIdxs(0).setNeighborIdxs(0, 1).build();
        Polygon p2 = Polygon.newBuilder().setCentroidIdx(1).addNeighborIdxs(0).setNeighborIdxs(0, 0).build();
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addPolygons(p1).addPolygons(p2).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(Type.OCEAN, null));
        tiles.add(new Tile(Type.LAND, null));

        tiles = lgen.transform(aMesh, tiles, 1, new Random());
        assertEquals(Type.LAND.toString(), tiles.get(1).getType());
    }

    @Test
    public void correctHumidity() {
        // Create test mesh
        Vertex v1 = Vertex.newBuilder().setX(0).setY(0).build();
        Vertex v2 = Vertex.newBuilder().setX(250).setY(250).build();
        Polygon p1 = Polygon.newBuilder().setCentroidIdx(0).build();
        Polygon p2 = Polygon.newBuilder().setCentroidIdx(1).build();
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addPolygons(p1).addPolygons(p2).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(Type.LAND, null));
        tiles.add(new Tile(Type.LAND, null));

        tiles = lgen.transform(aMesh, tiles, new Random().nextInt(tiles.size()), new Random());
        for (Tile t : tiles) {
            if (t.getType().equals(Type.LAKE.toString()))
                assertEquals(100, t.getHumidity());
            else
                assertEquals(0, t.getHumidity());
        }
    }

}
