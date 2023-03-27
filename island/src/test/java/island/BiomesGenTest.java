//package island;
//
//import ca.mcmaster.cas.se2aa4.a2.io.Structs;
//import island.biomes.Biome;
//import island.Tile.Tile;
//import island.Tile.Type;
//import island.Tiles.LandTile;
//import island.generators.BiomesGen;
//import org.junit.jupiter.api.Test;
//
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class BiomesGenTest {
//
//    private BiomesGen bgen = new BiomesGen();
//
//    @Test
//    public void arcticExists() {
//        // Create test mesh
//        Structs.Polygon p1 = Structs.Polygon.newBuilder().build();
//        Structs.Polygon p2 = Structs.Polygon.newBuilder().build();
//        Structs.Mesh aMesh = Structs.Mesh.newBuilder().addPolygons(p1).addPolygons(p2).build();
//
//        // Create corresponding tiles
//        List<Tile> tiles = new ArrayList<>();
//        Tile tile1 = new LandTile();
//        tile1.setHumidity(100);
//        tile1.setAltitude(100);
//        tiles.add(tile1);
//        Tile tile2 = new LandTile();
//        tile2.setHumidity(41);
//        tile2.setAltitude(90);
//        tiles.add(tile2);
//
//        tiles = bgen.transform(aMesh, tiles, 0, 0);
//        assertEquals(Biome.ARCTIC.toString(), tiles.get(0).getBiomes());
//        assertEquals(Biome.ARCTIC.toString(), tiles.get(1).getBiomes());
//    }
//    @Test
//    public void tundraExists() {
//        // Create test mesh
//        Structs.Polygon p1 = Structs.Polygon.newBuilder().build();
//        Structs.Polygon p2 = Structs.Polygon.newBuilder().build();
//        Structs.Mesh aMesh = Structs.Mesh.newBuilder().addPolygons(p1).addPolygons(p2).build();
//
//        // Create corresponding tiles
//        List<Tile> tiles = new ArrayList<>();
//        Tile tile1 = new LandTile();
//        tile1.setHumidity(30);
//        tile1.setAltitude(90);
//        tiles.add(tile1);
//        Tile tile2 = new LandTile();
//        tile2.setHumidity(10);
//        tile2.setAltitude(60);
//        tiles.add(tile2);
//
//        tiles = bgen.transform(aMesh, tiles, 0, 0);
//        assertEquals(Biome.TUNDRA.toString(), tiles.get(0).getBiomes());
//        assertEquals(Biome.TUNDRA.toString(), tiles.get(1).getBiomes());
//    }
//    @Test
//    public void rainForestExists() {
//        // Create test mesh
//        Structs.Polygon p1 = Structs.Polygon.newBuilder().build();
//        Structs.Mesh aMesh = Structs.Mesh.newBuilder().addPolygons(p1).build();
//
//        // Create corresponding tiles
//        List<Tile> tiles = new ArrayList<>();
//        Tile tile = new LandTile();
//        tile.setHumidity(100);
//        tile.setAltitude(40);
//        tiles.add(tile);
//
//        tiles = bgen.transform(aMesh, tiles, 0, 0);
//        assertEquals(Biome.RAINFOREST.toString(), tiles.get(0).getBiomes());
//    }
//    @Test
//    public void forestExists() {
//        // Create test mesh
//        Structs.Polygon p1 = Structs.Polygon.newBuilder().build();
//        Structs.Polygon p2 = Structs.Polygon.newBuilder().build();
//        Structs.Mesh aMesh = Structs.Mesh.newBuilder().addPolygons(p1).addPolygons(p2).build();
//
//        // Create corresponding tiles
//        List<Tile> tiles = new ArrayList<>();
//        Tile tile1 = new LandTile();
//        tile1.setHumidity(60);
//        tile1.setAltitude(60);
//        tiles.add(tile1);
//        Tile tile2 = new LandTile();
//        tile2.setHumidity(40);
//        tile2.setAltitude(30);
//        tiles.add(tile2);
//
//        tiles = bgen.transform(aMesh, tiles, 0, 0);
//        assertEquals(Biome.FOREST.toString(), tiles.get(0).getBiomes());
//        assertEquals(Biome.FOREST.toString(), tiles.get(1).getBiomes());
//    }
//    @Test
//    public void desertExists() {
//        // Create test mesh
//        Structs.Polygon p1 = Structs.Polygon.newBuilder().build();
//        Structs.Mesh aMesh = Structs.Mesh.newBuilder().addPolygons(p1).build();
//
//        // Create corresponding tiles
//        List<Tile> tiles = new ArrayList<>();
//        Tile tile = new LandTile();
//        tile.setHumidity(10);
//        tile.setAltitude(30);
//        tiles.add(tile);
//
//
//        tiles = bgen.transform(aMesh, tiles, 0, 0);
//        assertEquals(Biome.DESERT.toString(), tiles.get(0).getBiomes());
//    }
//    @Test
//    public void mangroveExists() {
//        // Create test mesh
//        Structs.Polygon p1 = Structs.Polygon.newBuilder().addNeighborIdxs(0).setNeighborIdxs(0, 1).build();
//        Structs.Polygon p2 = Structs.Polygon.newBuilder().addNeighborIdxs(0).setNeighborIdxs(0, 0).build();
//        Structs.Polygon p3 = Structs.Polygon.newBuilder().build();
//        Structs.Mesh aMesh = Structs.Mesh.newBuilder().addPolygons(p1).addPolygons(p2).addPolygons(p3).build();
//
//        // Create corresponding tiles
//        List<Tile> tiles = new ArrayList<>();
//        Tile tile1 = new LandTile();
//        tile1.setHumidity(70);
//        tile1.setAltitude(10);
//        tiles.add(tile1);
//        Tile tile2 = new Tile(Type.LAKE, new Color(0,0,255));
//        tiles.add(tile2);
//        Tile tile3 = new LandTile();
//        tile3.setHumidity(70);
//        tile3.setAltitude(10);
//        tiles.add(tile3);
//
//
//        tiles = bgen.transform(aMesh, tiles, 0, 0);
//        assertEquals(Biome.MANGROVES.toString(), tiles.get(0).getBiomes());
//        assertEquals(Biome.FOREST.toString(), tiles.get(2).getBiomes());
//    }
//    @Test
//    public void taigaExists() {
//        // Create test mesh
//        Structs.Polygon p1 = Structs.Polygon.newBuilder().build();
//        Structs.Mesh aMesh = Structs.Mesh.newBuilder().addPolygons(p1).build();
//
//        // Create corresponding tiles
//        List<Tile> tiles = new ArrayList<>();
//        Tile tile1 = new LandTile();
//        tile1.setHumidity(50);
//        tile1.setAltitude(10);
//        tiles.add(tile1);
//
//        tiles = bgen.transform(aMesh, tiles, 0, 0);
//        assertEquals(Biome.TAIGA.toString(), tiles.get(0).getBiomes());
//    }
//
//    @Test
//    public void plainsExists() {
//        // Create test mesh
//        Structs.Polygon p1 = Structs.Polygon.newBuilder().build();
//        Structs.Mesh aMesh = Structs.Mesh.newBuilder().addPolygons(p1).build();
//
//        // Create corresponding tiles
//        List<Tile> tiles = new ArrayList<>();
//        Tile tile1 = new LandTile();
//        tile1.setHumidity(16);
//        tile1.setAltitude(10);
//        tiles.add(tile1);
//
//        tiles = bgen.transform(aMesh, tiles, 0, 0);
//        assertEquals(Biome.PLAINS.toString(), tiles.get(0).getBiomes());
//    }
//    @Test
//    public void subtropicalDesertExists() {
//        // Create test mesh
//        Structs.Polygon p1 = Structs.Polygon.newBuilder().build();
//        Structs.Mesh aMesh = Structs.Mesh.newBuilder().addPolygons(p1).build();
//
//        // Create corresponding tiles
//        List<Tile> tiles = new ArrayList<>();
//        Tile tile1 = new LandTile();
//        tile1.setHumidity(10);
//        tile1.setAltitude(10);
//        tiles.add(tile1);
//
//        tiles = bgen.transform(aMesh, tiles, 0, 0);
//        assertEquals(Biome.SUBTROPICALDESERT.toString(), tiles.get(0).getBiomes());
//    }
//}
