package island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import island.Tile.Tile;
import island.Tile.Type;
import island.generators.AltitudeGen;

import java.util.ArrayList;
import java.util.List;

import island.profiles.altitude.AltitudeData;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;

import static org.junit.jupiter.api.Assertions.*;

public class AltitudeGenTest {
    
    private AltitudeGen agen = new AltitudeGen();

    @Test
    public void checkPeak() {
        // Create test mesh
        Vertex v1 = Vertex.newBuilder().setX(0).setY(0).build();
        Vertex v2 = Vertex.newBuilder().setX(250).setY(250).build();
        Polygon p1 = Polygon.newBuilder().setCentroidIdx(0).addNeighborIdxs(0).setNeighborIdxs(0, 1).build();
        Polygon p2 = Polygon.newBuilder().setCentroidIdx(1).addNeighborIdxs(0).setNeighborIdxs(0, 0).build();
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addPolygons(p1).addPolygons(p2).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(Type.LAND, null));
        tiles.add(new Tile(Type.WATER, null));

        // Create coordinate peaks
        List<Coordinate> coords = new ArrayList<>();
        coords.add(new Coordinate(50, 50));

        AltitudeData altitudeData = new AltitudeData(coords, 10, 0);

        tiles = agen.transform(aMesh, tiles, altitudeData);
        assertEquals(tiles.get(0).getAltitude(), 10);
        assertEquals(tiles.get(1).getAltitude(), 0);
    }

    @Test
    public void checkIncline() {
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

        // Create coordinate peaks
        List<Coordinate> coords = new ArrayList<>();
        coords.add(new Coordinate(0, 0));

        AltitudeData altitudeData = new AltitudeData(coords, 10, 0.5);


        tiles = agen.transform(aMesh, tiles, altitudeData);
        assertEquals(Math.round(tiles.get(0).getAltitude()), 10);
        assertEquals(Math.round(tiles.get(1).getAltitude()), 5);
    }


    @Test
    public void checkOceanAltitude() {
        // Create test mesh
        Vertex v1 = Vertex.newBuilder().setX(0).setY(0).build();
        Vertex v2 = Vertex.newBuilder().setX(250).setY(250).build();
        Polygon p1 = Polygon.newBuilder().setCentroidIdx(0).addNeighborIdxs(0).setNeighborIdxs(0, 1).build();
        Polygon p2 = Polygon.newBuilder().setCentroidIdx(1).addNeighborIdxs(0).setNeighborIdxs(0, 0).build();
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addPolygons(p1).addPolygons(p2).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(Type.WATER, null));
        tiles.add(new Tile(Type.WATER, null));

        // Create coordinate peaks
        List<Coordinate> coords = new ArrayList<>();
        coords.add(new Coordinate(0, 0));

        // Test altitudes with no land tiles
        AltitudeData altitudeData = new AltitudeData(coords, 10, 0.5);

        tiles = agen.transform(aMesh, tiles, altitudeData);
        assertEquals(tiles.get(0).getAltitude(), 0);
        assertEquals(tiles.get(1).getAltitude(), 0);

        // Test altitude with one land tile
        tiles.clear();
        tiles.add(new Tile(Type.WATER, null));
        tiles.add(new Tile(Type.LAND, null));
        tiles = agen.transform(aMesh, tiles, altitudeData);
        assertEquals(tiles.get(0).getAltitude(), 0);
    }

}
