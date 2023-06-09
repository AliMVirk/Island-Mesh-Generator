package island.generators;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import island.Tile.Tile;
import island.Tile.Type;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LakeGen {

    public List<Tile> transform(Mesh oMesh, List<Tile> tiles, int numOfLakes, Random rnd) {

        List<Integer> polygonIdxs = getValidPolygonIdxs(oMesh, tiles);
        numOfLakes = (polygonIdxs.size() == 0) ? 0 : numOfLakes; // Create no lakes if there are no valid spots

        for (int j = 0; j < numOfLakes && polygonIdxs.size() != 0; j++) {
            // Set random valid tile as a lake
            int index = polygonIdxs.get(rnd.nextInt(polygonIdxs.size()));
            tiles.set(index, createLakeTile(tiles.get(index)));
            polygonIdxs.remove((Object) index);
            // Go through the lake's neighbors and randomly set some as lakes
            for (int n : oMesh.getPolygons(index).getNeighborIdxsList()){
                if (rnd.nextBoolean() && polygonIdxs.contains(n)) {
                    tiles.set(n, createLakeTile(tiles.get(n)));
                    polygonIdxs.remove((Object) n);
                }
            }
        }

        return tiles;
    }

    // Gets land polygons indexes that have no water neighbors
    private List<Integer> getValidPolygonIdxs(Mesh mesh, List<Tile> tiles) {
        List<Integer> validPolygonIdxs = new ArrayList<>();
        for (int i = 0; i < mesh.getPolygonsCount(); i++) {
            boolean valid = true;
            if (tiles.get(i).getType().equals(Type.OCEAN.toString()))
                continue;
            for (int j : mesh.getPolygons(i).getNeighborIdxsList()) {
                if (tiles.get(j).getType().equals(Type.OCEAN.toString()))
                    valid = false;
            }
            if (valid)
                validPolygonIdxs.add(i);
        }
        return validPolygonIdxs;
    }

    private Tile createLakeTile(Tile t) {
        Tile lake = new Tile(Type.LAKE, new Color(1, 99, 147), 100);
        lake.setAltitude(t.getAltitude());
        return lake;
    }

}
