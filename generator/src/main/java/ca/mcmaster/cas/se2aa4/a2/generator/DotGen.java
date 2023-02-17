package ca.mcmaster.cas.se2aa4.a2.generator;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;

    public Mesh generate() {
        // Create list of builders so properties can be added without creating more lists and rebuilding
        ArrayList<Vertex.Builder> vertexBuilders = new ArrayList<>();
        ArrayList<Vertex.Builder> centroidVertexBuilders = new ArrayList<>();
        ArrayList<Segment.Builder> segmentBuilders = new ArrayList<>();
        ArrayList<Polygon.Builder> polygonBuilders = new ArrayList<>();
        // Create all the vertices builders
        for(int x = 0; x < width; x += square_size) {
            for(int y = 0; y < height; y += square_size) {
                vertexBuilders.add(Vertex.newBuilder().setX((double) x).setY((double) y));
                if (x != width-square_size && y != height-square_size)
                    centroidVertexBuilders.add(Vertex.newBuilder().setX((double) x+10).setY((double) y+10));

            }
        }

        // Create all the segments builders
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                if (j != 24)
                    segmentBuilders.add(Segment.newBuilder().setV1Idx(j+i*25).setV2Idx(j+i*25+1));
                if (i != 24)
                    segmentBuilders.add(Segment.newBuilder().setV1Idx(j+i*25).setV2Idx(j+i*25+25));
            }
        }
        // Create all the polygon builders
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 47; j += 2) {
                int left = j + i*49;
                int top = left + 1;
                int right = left + 49;
                int bottom = left + 3;
                if (j == 46)
                    bottom--;
                if (i == 23)
                    right -= 0.5 * j;
                polygonBuilders.add(Polygon.newBuilder().addSegmentIdxs(0).addSegmentIdxs(1).addSegmentIdxs(2).addSegmentIdxs(3).setSegmentIdxs(0, left).setSegmentIdxs(1, top).setSegmentIdxs(2, right).setSegmentIdxs(3, bottom));
            }
        }
        // Add the centroid vertices to their respective polygons
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 24; j++) {
                polygonBuilders.get(j+25*i).setCentroidIdx(j+25*i+(vertexBuilders.size()));
            }
        }

        // Distribute vertex colors randomly
        Random bag = new Random();
        for(Vertex.Builder v : vertexBuilders){
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            v.addProperties(color);
        }

        // Set all the centroid vertices colors to black
        for(Vertex.Builder v : centroidVertexBuilders){
            Property color = Property.newBuilder().setKey("rgb_color").setValue("0,0,0").build();
            v.addProperties(color);
        }

        // Color segments by averaging out adjacent vertex colors
        for(Segment.Builder s : segmentBuilders){
            Vertex.Builder v1 = vertexBuilders.get(s.getV1Idx());
            Vertex.Builder v2 = vertexBuilders.get(s.getV1Idx());
            Color v1Color = extractColor(v1.getPropertiesList());
            Color v2Color = extractColor(v2.getPropertiesList());
            int segmentRed = (v1Color.getRed() + v2Color.getRed())/2;
            int segmentGreen = (v1Color.getGreen() + v2Color.getGreen())/2;
            int segmentBlue = (v1Color.getBlue() + v2Color.getBlue())/2;
            String colorCode = segmentRed + "," + segmentGreen + "," + segmentBlue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            s.addProperties(color);
        }
        // Build all vertices, segments, and polygons
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Vertex> centroidVertices = new ArrayList<>();
        ArrayList<Segment> segments = new ArrayList<>();
        ArrayList<Polygon> polygons = new ArrayList<>();
        for (Vertex.Builder v : vertexBuilders)
            vertices.add(v.build());
        for (Vertex.Builder cv: centroidVertexBuilders)
            centroidVertices.add(cv.build());
        for (Segment.Builder s : segmentBuilders)
            segments.add(s.build());
        for (Polygon.Builder p : polygonBuilders)
            polygons.add(p.build());

        // Add the list of centroidVertices to the list of vertices
        vertices.addAll(centroidVertices);
        
        return Mesh.newBuilder().addAllVertices(vertices).addAllSegments(segments).addAllPolygons(polygons).build();
    }


    private Color extractColor(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
                //System.out.println(p.getValue());
                val = p.getValue();
            }
        }
        if (val == null)
            return Color.BLACK;
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        return new Color(red, green, blue);
    }
}
