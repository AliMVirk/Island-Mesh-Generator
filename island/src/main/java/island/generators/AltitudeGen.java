package island.generators;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import island.Tile.Tile;
import island.Tile.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import island.profiles.altitude.AltitudeData;
import org.locationtech.jts.geom.Coordinate;

public class AltitudeGen {
    
    public List<Tile> transform(Mesh oMesh, List<Tile> tiles, AltitudeData altitudeData, Random rnd) {
        List<Coordinate> coords = altitudeData.getCoords();
        double maxAltitude = altitudeData.getMaxAltitude();
        double steepnessFactor = altitudeData.getSteepnessFactor();
        List<Integer> assignAltitude = new ArrayList<>();

        // Initialize nearest vertex variable as centroid of first found land tile
        Vertex nearestPoint = null;
        int defaultIndex = 0;
        for (int i = 0; i < tiles.size(); i++) {
            if (!tiles.get(i).getType().equals(Type.OCEAN.toString())) {
                nearestPoint = oMesh.getVertices(oMesh.getPolygons(i).getCentroidIdx());
                defaultIndex = i;
                break;
            }
        }
        // If there are no land tiles, altitude for all tiles remains at default zero value
        if (nearestPoint == null)
            return tiles;
        // Find nearest centroid vertices to given peak coordinates
        for (Coordinate c : coords) {
            int peakIndex = defaultIndex;
            for (int i = 0; i < oMesh.getPolygonsCount(); i++) {
                if (tiles.get(i).getType().equals(Type.OCEAN.toString()))
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
                if (!nTile.getType().equals(Type.OCEAN.toString())) {
                    if (nTile.getAltitude() == 0 && nTile.getAltitude() < tiles.get(i).getAltitude()) {
                        nTile.setAltitude(tiles.get(i).getAltitude() * steepnessFactor + ((rnd.nextDouble() - 0.5) / 10));
                        pendingTiles.add(n);
                    }
                }
            }
            assignAltitude.addAll(pendingTiles);
        }

        return tiles;
    }

}
