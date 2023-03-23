package island.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import island.Tile.River;
import island.Tile.Tile;
import island.Tile.Type;

public class RiverGen {
    
    public River[] createRivers(Mesh oMesh, List<Tile> tiles, int numRivers) {
        River[] rivers = new River[oMesh.getSegmentsCount()];

        Random rnd = new Random();
        List<Integer> polygonIdxs = getValidPolygonIdxs(oMesh, tiles);
        numRivers = (polygonIdxs.size() == 0) ? 0 : numRivers; // Create no rivers if there are no valid spots
        for (int i = 0; i < numRivers; i++) {
            // Initialize first vertex spring
            int pIndex = polygonIdxs.get(rnd.nextInt(polygonIdxs.size()));
            Polygon p = oMesh.getPolygons(pIndex);
            int segmentIndex = p.getSegmentIdxs(rnd.nextInt(p.getSegmentIdxsCount()));
            Segment s = oMesh.getSegments(segmentIndex);
            Vertex spring = oMesh.getVertices(s.getV1Idx());
            rivers[segmentIndex] = new River(2);
        }

        return rivers;
    }

    private List<Integer> getValidPolygonIdxs(Mesh mesh, List<Tile> tiles) {
        List<Integer> validPolygonIdxs = new ArrayList<>();
        for (int i = 0; i < mesh.getPolygonsCount(); i++) {
            boolean valid = true;
            if (tiles.get(i).getType().equals(Type.WATER.toString()) || tiles.get(i).getType().equals(Type.LAKE.toString()))
                continue;
            for (int j : mesh.getPolygons(i).getNeighborIdxsList()) {
                if (tiles.get(j).getType().equals(Type.WATER.toString()) || tiles.get(j).getType().equals(Type.LAKE.toString()))
                    valid = false;
            }
            if (valid)
                validPolygonIdxs.add(i);
        }
        return validPolygonIdxs;
    }

}
