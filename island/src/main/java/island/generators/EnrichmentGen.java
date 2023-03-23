package island.generators;

import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import island.Tile.River;
import island.Tile.Tile;
import island.Tile.Type;

public class EnrichmentGen {
    
    public List<Tile> enrichLand(Mesh oMesh, List<Tile> tiles, River[] rivers) {

        // Add humidity to land neighboring water
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getType().equals(Type.WATER.toString())) {
                double humidity = tiles.get(i).getHumidity();
                for (int j : oMesh.getPolygons(i).getNeighborIdxsList()) {
                    if (tiles.get(j).getType().equals(Type.LAND.toString()))
                        tiles.get(j).setHumidity(humidity / 4);
                }
            }
        }

        return tiles;
    }

}
