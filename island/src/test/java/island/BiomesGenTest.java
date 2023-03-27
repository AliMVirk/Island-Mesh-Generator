package island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import island.biomes.*;
import island.Tile.Tile;
import island.Tile.Type;
import island.Tiles.LandTile;
import island.generators.BiomesGen;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BiomesGenTest {

    private BiomesGen bgen = new BiomesGen();

    @Test
    public void arcticCorrectBiomes() {
        // Create test mesh
        Structs.Polygon p1 = Structs.Polygon.newBuilder().build();
        Structs.Polygon p2 = Structs.Polygon.newBuilder().build();
        Structs.Polygon p3 = Structs.Polygon.newBuilder().build();
        Structs.Polygon p4 = Structs.Polygon.newBuilder().build();
        Structs.Polygon p5 = Structs.Polygon.newBuilder().build();
        Structs.Mesh aMesh = Structs.Mesh.newBuilder().addPolygons(p1).addPolygons(p2).addPolygons(p3).addPolygons(p4).addPolygons(p5).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        Tile tile1 = new LandTile();
        tile1.setHumidity(100);
        tile1.setAltitude(100);
        tiles.add(tile1);
        Tile tile2 = new LandTile();
        tile2.setHumidity(15);
        tile2.setAltitude(15);
        tiles.add(tile2);
        Tile tile3 = new LandTile();
        tile3.setHumidity(20);
        tile3.setAltitude(50);
        tiles.add(tile3);
        Tile tile4 = new LandTile();
        tile4.setHumidity(50);
        tile4.setAltitude(30);
        tiles.add(tile4);
        Tile tile5 = new LandTile();
        tile5.setHumidity(5);
        tile5.setAltitude(5);
        tiles.add(tile5);

        tiles = bgen.transform(aMesh, tiles, Biomes.ARCTIC.toString());
        assertEquals(Arctic.GLACIER.toString(), tiles.get(0).getBiomes());
        assertEquals(Arctic.ARCTICTUNDRA.toString(), tiles.get(1).getBiomes());
        assertEquals(Arctic.ALPINETUNDRA.toString(), tiles.get(2).getBiomes());
        assertEquals(Arctic.BOREALFOREST.toString(), tiles.get(3).getBiomes());
        assertEquals(Arctic.PLAINS.toString(), tiles.get(4).getBiomes());

    }

    @Test
    public void TropicalCorrectBiomes() {
        // Create test mesh
        Structs.Polygon p1 = Structs.Polygon.newBuilder().build();
        Structs.Polygon p2 = Structs.Polygon.newBuilder().build();
        Structs.Polygon p3 = Structs.Polygon.newBuilder().build();
        Structs.Polygon p4 = Structs.Polygon.newBuilder().build();
        Structs.Polygon p5 = Structs.Polygon.newBuilder().build();
        Structs.Polygon p6 = Structs.Polygon.newBuilder().build();
        Structs.Mesh aMesh = Structs.Mesh.newBuilder().addPolygons(p1).addPolygons(p2).addPolygons(p3).addPolygons(p4).addPolygons(p5).addPolygons(p6).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        Tile tile1 = new LandTile();
        tile1.setHumidity(100);
        tile1.setAltitude(100);
        tiles.add(tile1);
        Tile tile2 = new LandTile();
        tile2.setHumidity(10);
        tile2.setAltitude(15);
        tiles.add(tile2);
        Tile tile3 = new LandTile();
        tile3.setHumidity(0);
        tile3.setAltitude(15);
        tiles.add(tile3);
        Tile tile4 = new LandTile();
        tile4.setHumidity(15);
        tile4.setAltitude(8);
        tiles.add(tile4);
        Tile tile5 = new LandTile();
        tile5.setHumidity(5);
        tile5.setAltitude(8);
        tiles.add(tile5);
        Tile tile6 = new LandTile();
        tile6.setHumidity(2);
        tile6.setAltitude(2);
        tiles.add(tile6);

        tiles = bgen.transform(aMesh, tiles, Biomes.TROPICAL.toString());
        assertEquals(Tropical.RAINFOREST.toString(), tiles.get(0).getBiomes());
        assertEquals(Tropical.FOREST.toString(), tiles.get(1).getBiomes());
        assertEquals(Tropical.SAVANNA.toString(), tiles.get(2).getBiomes());
        assertEquals(Tropical.WETLAND.toString(), tiles.get(3).getBiomes());
        assertEquals(Tropical.MANGROVES.toString(), tiles.get(4).getBiomes());
        assertEquals(Tropical.SAVANNA.toString(), tiles.get(5).getBiomes());
    }

    @Test
    public void DesertCorrectBiomes() {
        // Create test mesh
        Structs.Polygon p1 = Structs.Polygon.newBuilder().build();
        Structs.Polygon p2 = Structs.Polygon.newBuilder().build();
        Structs.Polygon p3 = Structs.Polygon.newBuilder().build();
        Structs.Mesh aMesh = Structs.Mesh.newBuilder().addPolygons(p1).addPolygons(p2).addPolygons(p3).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        Tile tile1 = new LandTile();
        tile1.setHumidity(100);
        tile1.setAltitude(100);
        tiles.add(tile1);
        Tile tile2 = new LandTile();
        tile2.setHumidity(20);
        tile2.setAltitude(20);
        tiles.add(tile2);
        Tile tile3 = new LandTile();
        tile3.setHumidity(0);
        tile3.setAltitude(5);
        tiles.add(tile3);


        tiles = bgen.transform(aMesh, tiles, Biomes.DESERT.toString());
        assertEquals(Desert.SAVANNA.toString(), tiles.get(0).getBiomes());
        assertEquals(Desert.DESERT.toString(), tiles.get(1).getBiomes());
        assertEquals(Desert.SUBTROPICALDESERT.toString(), tiles.get(2).getBiomes());
    }

    @Test
    public void TemperateCorrectBiomes() {
        // Create test mesh
        Structs.Polygon p1 = Structs.Polygon.newBuilder().build();
        Structs.Polygon p2 = Structs.Polygon.newBuilder().build();
        Structs.Polygon p3 = Structs.Polygon.newBuilder().build();
        Structs.Polygon p4 = Structs.Polygon.newBuilder().build();
        Structs.Mesh aMesh = Structs.Mesh.newBuilder().addPolygons(p1).addPolygons(p2).addPolygons(p3).addPolygons(p4).build();

        // Create corresponding tiles
        List<Tile> tiles = new ArrayList<>();
        Tile tile1 = new LandTile();
        tile1.setHumidity(100);
        tile1.setAltitude(100);
        tiles.add(tile1);
        Tile tile2 = new LandTile();
        tile2.setHumidity(10);
        tile2.setAltitude(10);
        tiles.add(tile2);
        Tile tile3 = new LandTile();
        tile3.setHumidity(80);
        tile3.setAltitude(15);
        tiles.add(tile3);
        Tile tile4 = new LandTile();
        tile4.setHumidity(15);
        tile4.setAltitude(2);
        tiles.add(tile4);

        tiles = bgen.transform(aMesh, tiles, Biomes.TEMPERATE.toString());
        assertEquals(Temperate.FOREST.toString(), tiles.get(0).getBiomes());
        assertEquals(Temperate.GRASSLAND.toString(), tiles.get(1).getBiomes());
        assertEquals(Temperate.CHAPARRAL.toString(), tiles.get(2).getBiomes());
        assertEquals(Temperate.PLAINS.toString(), tiles.get(3).getBiomes());
    }

}
