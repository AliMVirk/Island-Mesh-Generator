package island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import island.Tile.Tile;
import island.Tile.Type;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;

public class AltitudeGen {
    
    public List<Tile> transform(Mesh oMesh, List<Tile> tiles, List<Coordinate> coords, int maxAltitude, double steepnessFactor) {
        List<Integer> assignAltitude = new ArrayList<>();

        // Initialize nearest vertex variable
        Vertex nearestPoint = null;
        int peakIndex = 0;
        for (int i = 0; i < tiles.size(); i++) {
            if (!tiles.get(i).getType().equals(Type.WATER.toString())) {
                nearestPoint = oMesh.getVertices(oMesh.getPolygons(i).getCentroidIdx());
                peakIndex = i;
                break;
            }
        }
        // If there are no land tiles, altitude for all tiles remains at default zero value
        if (nearestPoint == null)
            return tiles;
        // Find nearest centroid vertices to given peak coordinates
        for (Coordinate c : coords) {
            for (int i = 0; i < oMesh.getPolygonsCount(); i++) {
                if (tiles.get(i).getType().equals(Type.WATER.toString()))
                    continue;
                Polygon p = oMesh.getPolygons(i);
                Vertex v = oMesh.getVertices(p.getCentroidIdx());
                double distanceToV = Math.sqrt(Math.pow(c.x - v.getX(), 2) + Math.pow(c.y - v.getY(), 2));
                double distanceToNearestPoint = Math.sqrt(Math.pow(c.x - nearestPoint.getX(), 2) + Math.pow(c.y - nearestPoint.getY(), 2));
                if (distanceToV < distanceToNearestPoint) {
                    nearestPoint = v;
                    peakIndex = i;
                }
            }
            assignAltitude.add(peakIndex);
        }

        // Assign max altitude to peaks
        for (int i : assignAltitude)
            tiles.get(i).setAltitude(maxAltitude);
        // Set altitude for all polygons in mesh
        for (int j = 0; j < assignAltitude.size(); j++) {
            int i = assignAltitude.get(j);
            List<Integer> pendingTiles = new ArrayList<>();
            for (int n : oMesh.getPolygons(i).getNeighborIdxsList()) {
                Tile nTile = tiles.get(n);
                if (!nTile.getType().equals(Type.WATER.toString())) {
                    if (nTile.getAltitude() == 0 && nTile.getAltitude() < tiles.get(i).getAltitude()) {
                        nTile.setAltitude(tiles.get(i).getAltitude() * steepnessFactor);
                        pendingTiles.add(n);
                    }
                }
            }
            assignAltitude.addAll(pendingTiles);
        }

        return tiles;
    }

}
