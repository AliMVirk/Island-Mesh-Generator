package island.generators;

import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import island.Tile.River;
import island.Tile.Tile;
import island.Tile.Type;
import island.Tiles.LandTile;

public class EnrichmentGen {

    public List<Tile> enrichLand(Mesh oMesh, List<Tile> tiles, River[] rivers, double[] composition) {
        List<Integer> assigned = new ArrayList<>();
        double humidityFactor = composition[0];
        double moistureFactor = composition[1];
        int spreadLevel = (int) composition[2];

        // Iterate through land neighboring water and set humidity, moisture, and vegetation levels
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getType().equals(Type.OCEAN.toString()) || tiles.get(i).getType().equals(Type.AQUIFER.toString()) || tiles.get(i).getType().equals(Type.LAKE.toString())) {
                double humidity = tiles.get(i).getHumidity();
                for (int j : oMesh.getPolygons(i).getNeighborIdxsList()) {
                    Tile nTile = tiles.get(j);
                    if (nTile.getType().equals(Type.LAND.toString())) {
                        nTile.setHumidity(humidity * humidityFactor);
                        if (!tiles.get(i).getType().equals(Type.OCEAN.toString()))
                            ((LandTile) nTile).setMoisture(humidity * moistureFactor);
                        assigned.add(j);
                    }
                }
            }
        }

        // Find polygons that have river segments and adjust humidity, moisture, and vegetation accordingly
        for (int i = 0; i < oMesh.getPolygonsCount(); i++) {
            for (int segIdx : oMesh.getPolygons(i).getSegmentIdxsList()) {
                Tile t = tiles.get(i);
                if (rivers[segIdx] != null && t.getType().equals(Type.LAND.toString())) {
                    t.setHumidity(t.getHumidity() + rivers[segIdx].getHumidity() * humidityFactor);
                    ((LandTile) t).setMoisture(((LandTile) t).getMoisture() + rivers[segIdx].getHumidity() * moistureFactor);
                    assigned.add(i);
                }
            }
        }

        // Spread humidity and moisture to tiles beyond water neighbors
        for (int n = 0; n < spreadLevel; n++) {
            List<Integer> toAssign = new ArrayList<>();
            for (int i = 0; i < assigned.size(); i++) {
                double humidity = tiles.get(assigned.get(i)).getHumidity();
                for (int j : oMesh.getPolygons(assigned.get(i)).getNeighborIdxsList()) {
                    Tile nTile = tiles.get(j);
                    if (nTile.getType().equals(Type.LAND.toString())) {
                        if (nTile.getHumidity() < humidity * humidityFactor)
                            nTile.setHumidity(humidity * humidityFactor);
                        if (((LandTile) nTile).getMoisture() < humidity * moistureFactor)
                            ((LandTile) nTile).setMoisture(humidity * moistureFactor);
                        toAssign.add(j);
                    }
                }
            }
            assigned.clear();
            assigned.addAll(toAssign);
        }
        

        return tiles;
    }

}
