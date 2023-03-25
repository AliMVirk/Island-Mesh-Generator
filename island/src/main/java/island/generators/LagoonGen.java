package island.generators;

import java.awt.*;
import java.util.ArrayList;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import island.Tile.Tile;
import island.Tile.Type;

public class LagoonGen {
    
    private final int width;
    private final int height;
    private final int lagoonRadius;
    private final int landRadius;

    public LagoonGen(int canvasWidth, int canvasHeight, int innerRadius, int outerRadius) {
        width = canvasWidth;
        height = canvasHeight;
        lagoonRadius = innerRadius;
        landRadius = outerRadius;
    }

    // CREATES NEW MESH
    public Mesh.Builder transform(Mesh oMesh) {

        Mesh.Builder mesh = Mesh.newBuilder();
        mesh.addAllVertices(oMesh.getVerticesList()).addAllSegments(oMesh.getSegmentsList()).addAllProperties(oMesh.getPropertiesList());
        ArrayList<Polygon.Builder> polygonBuilders = new ArrayList<>();
        ArrayList<Property.Builder> tileTypeList = new ArrayList<>();
        ArrayList<Structs.Property.Builder> tileColorList = new ArrayList<>();

        Tile tile;
        for (Polygon oPoly : oMesh.getPolygonsList()) {
            // Duplicate polygon from original mesh
            Polygon.Builder p = Polygon.newBuilder().addAllNeighborIdxs(oPoly.getNeighborIdxsList()).addAllSegmentIdxs(oPoly.getSegmentIdxsList()).addAllProperties(oPoly.getPropertiesList()).setCentroidIdx(oPoly.getCentroidIdx());
            Vertex v = oMesh.getVertices(oPoly.getCentroidIdx());
            // Check if centroid of polygon is within appropriate circle
            if (Math.pow(v.getX() - (width / 2), 2) + Math.pow(v.getY() - (height / 2), 2) <= Math.pow(lagoonRadius, 2))
                tile = new Tile(Type.LAGOON, new Color(4, 100, 151));
            else if (Math.pow(v.getX() - (width / 2), 2) + Math.pow(v.getY() - (height / 2), 2) <= Math.pow(landRadius, 2))
                tile = new Tile(Type.LAND, new Color(144, 137, 53));
            else
                tile = new Tile(Type.OCEAN, new Color(1, 64, 98));
            // Set tile type property for corresponding polygon
            tileTypeList.add(Property.newBuilder().setKey("tile_type").setValue(tile.getType()));
            tileColorList.add(Structs.Property.newBuilder().setKey("tile_color").setValue(tile.getColor()));
            polygonBuilders.add(p);
        }

        // Look for land tiles adjacent to water
        Tile beachTile = new Tile(Type.BEACH, new Color(255, 255, 217));
        for (int i = 0; i < polygonBuilders.size(); i++) {
            if (tileTypeList.get(i).getValue().equals("land")) {
                for (int j : polygonBuilders.get(i).getNeighborIdxsList()) {
                    if (tileTypeList.get(j).getValue().equals("water") || tileTypeList.get(j).getValue().equals("lagoon")) {
                        tileTypeList.set(i, tileTypeList.get(i).setValue(beachTile.getType()));
                        tileColorList.set(i, tileColorList.get(i).setValue(beachTile.getColor()));
                        break;
                    }
                }
            }
        }

        // Add tile type properties to polygons and add polygons to mesh
        for (int i = 0; i < polygonBuilders.size(); i++) {
            Polygon.Builder p = polygonBuilders.get(i);
            p.addProperties(tileTypeList.get(i).build());
            p.addProperties(tileColorList.get(i).build());
            mesh.addPolygons(p.build());
        }

        return mesh;
    }

}
