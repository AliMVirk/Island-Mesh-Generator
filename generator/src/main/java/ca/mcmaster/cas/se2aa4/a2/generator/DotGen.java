package ca.mcmaster.cas.se2aa4.a2.generator;

import java.util.ArrayList;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;

    public Mesh.Builder generateVertices(Mesh.Builder mesh) {
        // Create all the vertices builders
        ArrayList<Vertex.Builder> vertexBuilders = initializeVertices();
        ArrayList<Vertex.Builder> centroidVertexBuilders = initializeCentroidVertices();
        // Attribute thickness and random colors to vertices
        vertexBuilders = addVertexProperties(vertexBuilders, null, 3);
        // Attribute thickness and color to centroid vertices
        centroidVertexBuilders = addVertexProperties(centroidVertexBuilders, "0,0,0", 1.5f);

        // Add the list of centroidVertices to the list of vertices
        vertexBuilders.addAll(centroidVertexBuilders);

        // Build all the vertices and add them to mesh
        for (Vertex.Builder v : vertexBuilders)
            mesh.addVertices(v.build());

        return mesh;
    }

    private ArrayList<Vertex.Builder> initializeVertices() {
        ArrayList<Vertex.Builder> vertexBuilders = new ArrayList<>();
        for(int x = 0; x < width; x += square_size) {
            for(int y = 0; y < height; y += square_size)
                vertexBuilders.add(Vertex.newBuilder().setX((double) x).setY((double) y));
        }
        return vertexBuilders;
    }

    private ArrayList<Vertex.Builder> initializeCentroidVertices() {
        ArrayList<Vertex.Builder> vertexBuilders = new ArrayList<>();
        for(int x = 0; x < width; x += square_size) {
            for(int y = 0; y < height; y += square_size) {
                if (x != width-square_size && y != height-square_size)
                    vertexBuilders.add(Vertex.newBuilder().setX((double) x+10).setY((double) y+10));
            }
        }
        return vertexBuilders;
    }

    private ArrayList<Vertex.Builder> addVertexProperties(ArrayList<Vertex.Builder> vertexBuilders, String rgbValue, float thickness) {
        String colorCode = rgbValue;
        for(Vertex.Builder v : vertexBuilders){
            // Distribute vertex colors randomly if there is no given color
            if (rgbValue == null) {
                Random bag = new Random();
                int red = bag.nextInt(255);
                int green = bag.nextInt(255);
                int blue = bag.nextInt(255);
                colorCode = red + "," + green + "," + blue;
            }
            // Set vertex color
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            v.addProperties(color);
            // Assign regular vertex thickness
            v.addProperties(Property.newBuilder().setKey("thickness").setValue(String.valueOf(thickness)).build());
        }
        return vertexBuilders;
    }

}
