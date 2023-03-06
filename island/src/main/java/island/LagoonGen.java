package island;

import java.util.ArrayList;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;

public class LagoonGen {
    
    private final int width;
    private final int height;
    private final int lagoonRadius;
    private final int landRadius;

    public LagoonGen(int x, int y, int innerRadius, int outerRadius) {
        width = x;
        height = y;
        lagoonRadius = innerRadius;
        landRadius = outerRadius;
    }

    // CREATES NEW MESH
    public Mesh.Builder transform(Mesh oMesh) {

        Mesh.Builder mesh = Mesh.newBuilder();
        mesh.addAllVertices(oMesh.getVerticesList()).addAllSegments(oMesh.getSegmentsList()).addAllProperties(oMesh.getPropertiesList());
        ArrayList<Polygon.Builder> polygonBuilders = new ArrayList<>();
        ArrayList<Property.Builder> tileTypeList = new ArrayList<>();
        
        for (Polygon oPoly : oMesh.getPolygonsList()) {
            String tile = "";
            // Duplicate polygon from original mesh
            Polygon.Builder p = Polygon.newBuilder().addAllNeighborIdxs(oPoly.getNeighborIdxsList()).addAllSegmentIdxs(oPoly.getSegmentIdxsList()).addAllProperties(oPoly.getPropertiesList()).setCentroidIdx(oPoly.getCentroidIdx());
            Vertex v = oMesh.getVertices(oPoly.getCentroidIdx());
            // Check if centroid of polygon is within appropriate circle
            if (Math.pow(v.getX() - (width / 2), 2) + Math.pow(v.getY() - (height / 2), 2) <= Math.pow(lagoonRadius, 2))
                tile = "lagoon";
            else if (Math.pow(v.getX() - (width / 2), 2) + Math.pow(v.getY() - (height / 2), 2) <= Math.pow(landRadius, 2))
                tile = "land";
            else
                tile = "water";
            // Set tile type property for corresponding polygon
            tileTypeList.add(Property.newBuilder().setKey("tile_type").setValue(tile));
            polygonBuilders.add(p);
        }

        // Add tile type properties to polygons and add polygons to mesh
        for (int i = 0; i < polygonBuilders.size(); i++) {
            Polygon.Builder p = polygonBuilders.get(i);
            p.addProperties(tileTypeList.get(i).build());
            mesh.addPolygons(p.build());
        }

        return mesh;
    }

}
