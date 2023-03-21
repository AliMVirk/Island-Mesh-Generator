package island.generators;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import island.Tile.Tile;
import island.Tile.Type;
import island.Tiles.LakeTile;

import java.awt.Color;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LakeGen {

    public List<Tile> transform(Mesh oMesh, List<Tile> tiles, int numOfLakes) {
        numOfLakes = numOfLakes >= 2 ? numOfLakes : 2;

        for (int j = 0; j < numOfLakes; j++) {
            int index = new Random().nextInt(tiles.size());
            Boolean createTile = true;
            for(int b : oMesh.getPolygons(index).getNeighborIdxsList()){
                if (tiles.get(b).getType().equals(Type.WATER.toString())){
                    createTile = false;
                }
            }
            if (tiles.get(index).getType().equals(Type.LAND.toString()) && createTile) {
                tiles.set(index, new LakeTile(100));
                int count = new Random().nextInt(10);
                for (int i = 0; i < count; i++){
                    int n = oMesh.getPolygons(index).getNeighborIdxs(i%3);
                    Tile nTile = tiles.get(n);
                    createTile = true;
                    for(int b : oMesh.getPolygons(n).getNeighborIdxsList()){
                        if (tiles.get(b).getType().equals(Type.WATER.toString())){
                            createTile = false;
                        }
                    }
                    if (nTile.getType().equals(Type.LAND.toString()) && createTile) {
                       tiles.set(n, new LakeTile(100));
                    }
                    else {
                        if (i <= 3 && !nTile.getType().equals(Type.LAKE.toString()) || nTile.getType().equals(Type.LAKE.toString())){
                            break;
                        } 
                        i--;
                    }
                }
            } else {
                j--;
            }
        }

        return tiles;
    }

}
