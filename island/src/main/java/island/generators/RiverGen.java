package island.generators;

import java.awt.Color;
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
            if (polygonIdxs.size() == 0) break; // stop generating if there are no more spots to place rivers
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
                if (findLowestTile(oMesh, tiles, pIndex, spring) == -1) break; // no lowest tile found
                pIndex = findLowestTile(oMesh, tiles, pIndex, spring);
                p = oMesh.getPolygons(pIndex);
                segmentIndex = findConnectingSegment(oMesh, p, spring);
                s = oMesh.getSegments(segmentIndex);
                spring = (oMesh.getVertices(s.getV1Idx()).equals(spring)) ? oMesh.getVertices(s.getV2Idx()) : oMesh.getVertices(s.getV1Idx());
                if (tiles.get(pIndex).getType().equals(Type.OCEAN.toString()) || tiles.get(i).getType().equals(Type.LAKE.toString())) // stop extending if water is reached
                    break;
                if (rivers[segmentIndex] != null)
                    previousDischarge += rivers[segmentIndex].getDischarge();
                rivers[segmentIndex] = new River(previousDischarge);
            }

            // Find if there is a lake or ocean neighbor to the tile containing the end of the river
            boolean oceanNeighbor = false;
            boolean lakeNeighbor = false;
            for (int n : oMesh.getPolygons(pIndex).getNeighborIdxsList()) {
                if (tiles.get(n).getType().equals(Type.OCEAN.toString()))
                    oceanNeighbor = true;
                else if (tiles.get(n).getType().equals(Type.LAKE.toString()))
                    lakeNeighbor = true;
            }

            // Endorheic lake formation
            if (oceanNeighbor || !(tiles.get(pIndex).getType().equals(Type.LAKE.toString()) || lakeNeighbor)) {
                // Form an ocean tile to connect river to ocean (instead of an endorheic lake)
                if (oceanNeighbor)
                    tiles.set(pIndex, new Tile(Type.OCEAN, new Color(1, 64, 98), 150));
                // Form an endorheic lake
                else
                    tiles.set(pIndex, createLakeTile(tiles.get(pIndex)));
                polygonIdxs.remove((Object) pIndex);
                for (int nIndex : oMesh.getPolygons(pIndex).getNeighborIdxsList()) {
                    polygonIdxs.remove((Object) nIndex);
                }
            }
        }

        return rivers;
    }

    // Gets all land tile indexes without water neighbors
    private List<Integer> getValidPolygonIdxs(Mesh mesh, List<Tile> tiles) {
        List<Integer> validPolygonIdxs = new ArrayList<>();
        for (int i = 0; i < mesh.getPolygonsCount(); i++) {
            boolean valid = true;
            if (tiles.get(i).getType().equals(Type.OCEAN.toString()) || tiles.get(i).getType().equals(Type.LAKE.toString()))
                continue;
            for (int j : mesh.getPolygons(i).getNeighborIdxsList()) {
                if (tiles.get(j).getType().equals(Type.OCEAN.toString()) || tiles.get(j).getType().equals(Type.LAKE.toString()))
                    valid = false;
            }
            if (valid)
                validPolygonIdxs.add(i);
        }
        return validPolygonIdxs;
    }

    // Returns the current tile's steepest neighbor index that has a connecting segment
    private int findLowestTile(Mesh mesh, List<Tile> tiles, int pIndex, Vertex v) {
        Polygon p = mesh.getPolygons(pIndex);
        int nIndex = -1; // no neighbor found
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
                return -1; // no neighbor found
            else
                neighbors.remove((Object) nIndex);
        } while (findConnectingSegment(mesh, mesh.getPolygons(nIndex), v) == -1);
        return nIndex;
    }

    // Finds a connecting segment index between a polygon and vertex
    private int findConnectingSegment(Mesh mesh, Polygon n, Vertex v) {
        for (int i : n.getSegmentIdxsList()) {
            Segment s = mesh.getSegments(i);
            Vertex v1 = mesh.getVertices(s.getV1Idx());
            Vertex v2 = mesh.getVertices(s.getV2Idx());
            if (v1.equals(v) != v2.equals(v)) {
                return i;
            }
        }
        return -1; // no segment found
    }

    private Tile createLakeTile(Tile t) {
        Tile lake = new Tile(Type.LAKE, new Color(86, 163, 204), 100);
        lake.setAltitude(t.getAltitude());
        return lake;
    }

}
