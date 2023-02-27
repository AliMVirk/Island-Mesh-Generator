package ca.mcmaster.cas.se2aa4.a2.generator;

import java.util.ArrayList;
import java.util.Random;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

public class CentroidGen {

    private final int width;
    private final int height;
    private final int numVertices;
    private PrecisionModel round = new PrecisionModel(100);

    public CentroidGen(int numPolygons, int x, int y) {
        width = x;
        height = y;
        numVertices = numPolygons;
    }

    public Mesh.Builder generateVertices(Mesh.Builder mesh) {

        ArrayList<Vertex.Builder> vertexBuilders = new ArrayList<>();
        Random bag = new Random();
        for (int i = 0; i < numVertices; i++) {
            float x = bag.nextFloat() * width;
            float y = bag.nextFloat() * height;
            x = (float) round.makePrecise(x);
            y = (float) round.makePrecise(y);
            vertexBuilders.add(Vertex.newBuilder().setX(x).setY(y));
        }

        // Add index to differentiate between regular vertices and centroid vertices
        Property centroidDivider = Property.newBuilder().setKey("divider").setValue(String.valueOf(vertexBuilders.size())).build();
        mesh.addProperties(centroidDivider);
        
        vertexBuilders = addVertexProperties(vertexBuilders, 2.75f);
        for (Vertex.Builder v : vertexBuilders)
            mesh.addVertices(v.build());

        return mesh;
    }

    public Mesh.Builder generateVertices(Mesh.Builder mesh, ArrayList<Geometry> polygons) {

        Mesh.Builder rMesh = Mesh.newBuilder();
        ArrayList<Vertex.Builder> vertexBuilders = new ArrayList<>();
        PrecisionModel round = new PrecisionModel(100);

        for (int i = 0; i < polygons.size(); i++) {
            Geometry p = polygons.get(i);
            Point centroid = p.getCentroid();
            double x = centroid.getX();
            double y = centroid.getY();
            x = round.makePrecise(x);
            y = round.makePrecise(y);
            if (x < 0 || x > width || y < 0 || y > height)
                vertexBuilders.add(computeCentroid(p.getCoordinates())); // RECOMPUTE CENTROID
            else
                vertexBuilders.add(Vertex.newBuilder().setX(x).setY(y));
        }

        // Add index to differentiate between regular vertices and centroid vertices
        Property centroidDivider = Property.newBuilder().setKey("divider").setValue(String.valueOf(vertexBuilders.size())).build();
        rMesh.addProperties(centroidDivider);

        vertexBuilders = addVertexProperties(vertexBuilders, 2.75f);
        for (Vertex.Builder v : vertexBuilders)
            rMesh.addVertices(v.build());

        return rMesh;
    }

    private ArrayList<Vertex.Builder> addVertexProperties(ArrayList<Vertex.Builder> vertexBuilders, float thickness) {
        // All centroid vertices are colored red
        String colorCode = "255,0,0";
        for(Vertex.Builder v : vertexBuilders){
            // Set vertex color
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            v.addProperties(color);
            // Assign regular vertex thickness
            v.addProperties(Property.newBuilder().setKey("thickness").setValue(String.valueOf(thickness)).build());
        }
        return vertexBuilders;
    }

    private Vertex.Builder computeCentroid(Coordinate[] coords) {
        int centroidX = 0;
        int centroidY = 0;
        for (Coordinate c : coords) {
            if (c.x < 0)
                centroidX += 0;
            else if (c.x > width)
                centroidX += width;
            else
                centroidX += c.x;
            if (c.y < 0)
                centroidY += 0;
            else if (c.y > height)
                centroidY += height;
            else
                centroidY += c.y;
        }
        centroidX /= coords.length;
        centroidY /= coords.length;
        round.makePrecise(centroidX);
        round.makePrecise(centroidY);

        return Vertex.newBuilder().setX(centroidX).setY(centroidY);
    }

}
