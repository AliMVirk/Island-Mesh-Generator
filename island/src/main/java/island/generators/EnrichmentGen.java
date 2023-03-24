package island.generators;

import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import island.Tile.River;
import island.Tile.Tile;
import island.Tile.Type;
import island.Tiles.LandTile;

public class EnrichmentGen {

    public List<Tile> enrichLand(Mesh oMesh, List<Tile> tiles, River[] rivers) {

        // Iterate through land neighboring water and set humidity, moisture, and vegetation levels
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getType().equals(Type.WATER.toString()) || tiles.get(i).getType().equals(Type.AQUIFER.toString())) {
                double humidity = tiles.get(i).getHumidity();
                for (int j : oMesh.getPolygons(i).getNeighborIdxsList()) {
                    Tile nTile = tiles.get(j);
                    if (nTile.getType().equals(Type.LAND.toString())) {
                        nTile.setHumidity(humidity / 4);
                        ((LandTile) nTile).setMoisture(nTile.getHumidity() / 2);
                        ((LandTile) nTile).setVegetation(((LandTile) nTile).getMoisture() / 2);
                    }
                }
            }
        }

        // Find polygons that have river segments and adjust humidity, moisture, and vegetation accordingly
        for (int i = 0; i < oMesh.getPolygonsCount(); i++) {
            for (int segIdx : oMesh.getPolygons(i).getSegmentIdxsList()) {
                if (rivers[segIdx] != null) {
                    Tile nTile = tiles.get(i);
                    nTile.setHumidity(nTile.getHumidity() + rivers[segIdx].getDischarge() * 5);
                    ((LandTile) nTile).setMoisture(nTile.getHumidity() / 2);
                    ((LandTile) nTile).setVegetation(((LandTile) nTile).getMoisture() / 2);
                }
            }
        }

        return tiles;
    }

}
