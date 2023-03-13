package island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import island.Tile.Tile;
import island.Tile.Type;

import java.awt.Color;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class LandGen {
    
    public Mesh.Builder createLand(Mesh oMesh, Path2D shape) {
        Mesh.Builder mesh = Mesh.newBuilder();
        mesh.addAllVertices(oMesh.getVerticesList()).addAllSegments(oMesh.getSegmentsList()).addAllProperties(oMesh.getPropertiesList());
        ArrayList<Polygon.Builder> polygonBuilders = new ArrayList<>();
        ArrayList<Property.Builder> tileTypeList = new ArrayList<>();
        ArrayList<Property.Builder> tileColorList = new ArrayList<>();

        Tile tile;
        for (Polygon oPoly : oMesh.getPolygonsList()) {
            // Duplicate polygon from original mesh
            Polygon.Builder p = Polygon.newBuilder().addAllNeighborIdxs(oPoly.getNeighborIdxsList()).addAllSegmentIdxs(oPoly.getSegmentIdxsList()).addAllProperties(oPoly.getPropertiesList()).setCentroidIdx(oPoly.getCentroidIdx());
            Vertex v = oMesh.getVertices(oPoly.getCentroidIdx());
            // Check if centroid of polygon is within appropriate circle
            if (shape.contains(v.getX(), v.getY()))
                tile = new Tile(Type.LAND, new Color(144, 137, 53));
            else
                tile = new Tile(Type.WATER, new Color(1, 64, 98));
            // Set tile type property for corresponding polygon
            tileTypeList.add(Property.newBuilder().setKey("tile_type").setValue(tile.getType()));
            tileColorList.add(Property.newBuilder().setKey("tile_color").setValue(tile.getColor()));
            polygonBuilders.add(p);
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
