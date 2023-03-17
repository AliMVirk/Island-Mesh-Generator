package island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import island.Tile.Tile;
import island.Tile.Type;

import java.awt.Color;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class LandGen {
    
    public List<Tile> createLand(Mesh oMesh, Path2D shape) {
        List<Tile> tiles = new ArrayList<>();

        Tile tile;
        for (Polygon oPoly : oMesh.getPolygonsList()) {
            // Duplicate polygon from original mesh
            Vertex v = oMesh.getVertices(oPoly.getCentroidIdx());
            // Check if centroid of polygon is within appropriate circle
            if (shape.contains(v.getX(), v.getY()))
                tile = new Tile(Type.LAND, new Color(144, 137, 53));
            else
                tile = new Tile(Type.WATER, new Color(1, 64, 98));
            // Set tile type property for corresponding polygon
            tiles.add(tile);
        }

        return tiles;
    }

}
