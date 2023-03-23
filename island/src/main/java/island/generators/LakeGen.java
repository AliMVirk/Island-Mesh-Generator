package island.generators;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import island.Tile.Tile;
import island.Tile.Type;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LakeGen {

    public List<Tile> transform(Mesh oMesh, List<Tile> tiles, int numOfLakes) {

        Random rnd = new Random();
        List<Integer> polygonIdxs = getValidPolygonIdxs(oMesh, tiles);
        numOfLakes = (polygonIdxs.size() == 0) ? 0 : numOfLakes; // Create no lakes if there are no valid spots

        for (int j = 0; j < numOfLakes && polygonIdxs.size() != 0; j++) {
            // Set random valid tile as a lake
            int index = polygonIdxs.get(rnd.nextInt(polygonIdxs.size()));
            Tile lakeTile = new Tile(Type.WATER, new Color(1, 64, 98));
            lakeTile.setAltitude(100);
            tiles.set(index, lakeTile);
            polygonIdxs.remove((Object) index);
            // Go through the lake's neighbors and randomly set some as lakes
            for (int n : oMesh.getPolygons(index).getNeighborIdxsList()){
                if (rnd.nextBoolean() && polygonIdxs.contains(n)) {
                    lakeTile = new Tile(Type.WATER, new Color(1, 64, 98));
                    lakeTile.setAltitude(100);
                    tiles.set(n, lakeTile);
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
            if (tiles.get(i).getType().equals(Type.WATER.toString()))
                continue;
            for (int j : mesh.getPolygons(i).getNeighborIdxsList()) {
                if (tiles.get(j).getType().equals(Type.WATER.toString()))
                    valid = false;
            }
            if (valid)
                validPolygonIdxs.add(i);
        }
        return validPolygonIdxs;
    }

}
