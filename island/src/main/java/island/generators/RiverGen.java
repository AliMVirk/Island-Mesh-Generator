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
    
    public River[] createRivers(Mesh oMesh, List<Tile> tiles, int numRivers, Random rnd) {
        River[] rivers = new River[oMesh.getSegmentsCount()];

        List<Integer> polygonIdxs = getValidPolygonIdxs(oMesh, tiles);
        numRivers = (polygonIdxs.size() == 0) ? 0 : numRivers; // Create no rivers if there are no valid spots
        for (int i = 0; i < numRivers; i++) {
            // Initialize first vertex spring
            int pIndex = polygonIdxs.get(rnd.nextInt(polygonIdxs.size()));
            Polygon p = oMesh.getPolygons(pIndex);
            int segmentIndex = p.getSegmentIdxs(rnd.nextInt(p.getSegmentIdxsCount()));
            Segment s = oMesh.getSegments(segmentIndex);
            Vertex spring = oMesh.getVertices(s.getV1Idx());
            double previousDischarge = rnd.nextDouble(1.5) + 0.5;
            if (rivers[segmentIndex] != null)
                previousDischarge += rivers[segmentIndex].getDischarge();
            rivers[segmentIndex] = new River(previousDischarge);

            // Continue extending river until it reaches water or a lowest point
            while (true) {
                pIndex = findLowestTile(oMesh, tiles, pIndex, spring);
                if (pIndex == -1) break;
                p = oMesh.getPolygons(pIndex);
                segmentIndex = findConnectingSegment(oMesh, p, spring);
                s = oMesh.getSegments(segmentIndex);
                spring = (oMesh.getVertices(s.getV1Idx()).equals(spring)) ? oMesh.getVertices(s.getV2Idx()) : oMesh.getVertices(s.getV1Idx());
                if (tiles.get(pIndex).getType().equals(Type.OCEAN.toString()) || tiles.get(i).getType().equals(Type.LAKE.toString()))
                    break;
                if (rivers[segmentIndex] != null)
                    previousDischarge += rivers[segmentIndex].getDischarge();
                rivers[segmentIndex] = new River(previousDischarge);
            }
        }

        return rivers;
    }

    private List<Integer> getValidPolygonIdxs(Mesh mesh, List<Tile> tiles) {
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

    private int findLowestTile(Mesh mesh, List<Tile> tiles, int pIndex, Vertex v) {
        Polygon p = mesh.getPolygons(pIndex);
        int nIndex = -1;
        List<Integer> neighbors = new ArrayList<>(p.getNeighborIdxsList());
        do {
            double minAltitude = tiles.get(pIndex).getAltitude();
            for (int i : neighbors) {
                if (tiles.get(i).getAltitude() < minAltitude) {
                    minAltitude = tiles.get(i).getAltitude();
                    nIndex = i;
                }
            }
            if (minAltitude == tiles.get(pIndex).getAltitude())
                return -1;
            else
                neighbors.remove((Object) nIndex);
        } while (findConnectingSegment(mesh, mesh.getPolygons(nIndex), v) == -1);
        return nIndex;
    }

    private int findConnectingSegment(Mesh mesh, Polygon n, Vertex v) {
        for (int i : n.getSegmentIdxsList()) {
            Segment s = mesh.getSegments(i);
            Vertex v1 = mesh.getVertices(s.getV1Idx());
            Vertex v2 = mesh.getVertices(s.getV2Idx());
            if (v1.equals(v) != v2.equals(v)) {
                return i;
            }
        }
        return -1;
    }

}
