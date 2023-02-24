package ca.mcmaster.cas.se2aa4.a2.generator;

import java.util.ArrayList;
import java.util.Random;

import org.locationtech.jts.geom.PrecisionModel;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

public class CentroidGen {

    private final int width = 500;
    private final int height = 500;
    private final int numVertices = 100;

    public Mesh.Builder generateVertices(Mesh.Builder mesh) {

        ArrayList<Vertex.Builder> vertexBuilders = new ArrayList<>();
        Random bag = new Random();
        PrecisionModel round = new PrecisionModel(100);
        for (int i = 0; i < numVertices; i++) {
            float x = bag.nextFloat() * 500;
            float y = bag.nextFloat() * 500;
            round.makePrecise(x);
            round.makePrecise(y);
            vertexBuilders.add(Vertex.newBuilder().setX(x).setY(y));
        }

        vertexBuilders = addVertexProperties(vertexBuilders, 2.75f);
        ArrayList<Vertex> vertices = new ArrayList<>();
        for (Vertex.Builder v : vertexBuilders)
            vertices.add(v.build());

        return mesh.addAllVertices(vertices);
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

}
