package island.generators;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import island.Tile.Tile;
import island.Tile.Type;
import island.Tiles.LandTile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AquiferGen {

    public List<Tile> transform(Structs.Mesh oMesh, List<Tile> tiles, int numOfAquifers, Random rnd) {

        List<Integer> polygonIdxs = getValidPolygonIdxs(oMesh, tiles);
        numOfAquifers = (polygonIdxs.size() == 0) ? 0 : numOfAquifers; // Create no lakes if there are no valid spots

        for (int j = 0; j < numOfAquifers && polygonIdxs.size() != 0; j++) {
            // Set random valid tile as an aquifer
            int index = polygonIdxs.get(rnd.nextInt(polygonIdxs.size()));
            Tile t = new LandTile(100);
            t.setAltitude(tiles.get(index).getAltitude());
            ((LandTile) t).setMoisture(90);
            tiles.set(index, t);
            polygonIdxs.remove((Object) index);
        }

        return tiles;
    }

    private List<Integer> getValidPolygonIdxs(Structs.Mesh mesh, List<Tile> tiles) {
        List<Integer> validPolygonIdxs = new ArrayList<>();
        for (int i = 0; i < mesh.getPolygonsCount(); i++) {
            boolean valid = true;
            if (tiles.get(i).getType().equals(Type.OCEAN.toString()) || tiles.get(i).getType().equals(Type.LAKE.toString()))
                continue;
            for (int j : mesh.getPolygons(i).getNeighborIdxsList()) {
                if (tiles.get(j).getType().equals(Type.OCEAN.toString()) || tiles.get(i).getType().equals(Type.LAKE.toString()))
                    valid = false;
            }
            if (valid)
                validPolygonIdxs.add(i);
        }
        return validPolygonIdxs;
    }

}
