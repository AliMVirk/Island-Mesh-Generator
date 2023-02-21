package ca.mcmaster.cas.se2aa4.a2.generator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
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
        // Create all the vertices builders
        ArrayList<Vertex.Builder> vertexBuilders = initializeVertices();
        ArrayList<Vertex.Builder> centroidVertexBuilders = initializeCentroidVertices();
        // Create all the segments builders
        ArrayList<Segment.Builder> segmentBuilders = initializeSegments();
        // Create all the polygon builders
        ArrayList<Polygon.Builder> polygonBuilders = initializePolygons();
        // Add the centroid vertices to their respective polygons
        polygonBuilders = addCentroidVertices(polygonBuilders);
        // Include neighbor indices for all polygons in no particular order
        polygonBuilders = addNeighbors(polygonBuilders);
        // Attribute thickness and random colors to vertices
        vertexBuilders = addVertexProperties(vertexBuilders, null, 3);
        // Attribute thickness and color to centroid vertices
        centroidVertexBuilders = addVertexProperties(centroidVertexBuilders, "0,0,0", 1.5f);
        // Attribute thickness and color to segments
        segmentBuilders = addSegmentProperties(segmentBuilders, vertexBuilders, null, 0.25f);
        // Attribute thickness and color to polygons
        polygonBuilders = addPolygonProperties(polygonBuilders, "0,0,0", 0.5f);

        // Add the list of centroidVertices to the list of vertices
        vertexBuilders.addAll(centroidVertexBuilders);

        // Build all vertices, segments, and polygons
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Segment> segments = new ArrayList<>();
        ArrayList<Polygon> polygons = new ArrayList<>();
        for (Vertex.Builder v : vertexBuilders)
            vertices.add(v.build());
        for (Segment.Builder s : segmentBuilders)
            segments.add(s.build());
        for (Polygon.Builder p : polygonBuilders)
            polygons.add(p.build());

        return Mesh.newBuilder().addAllVertices(vertices).addAllSegments(segments).addAllPolygons(polygons).build();
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

    private ArrayList<Segment.Builder> initializeSegments() {
        ArrayList<Segment.Builder> segmentBuilders = new ArrayList<Segment.Builder>();
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                if (j != 24)
                    segmentBuilders.add(Segment.newBuilder().setV1Idx(j+i*25).setV2Idx(j+i*25+1));
                if (i != 24)
                    segmentBuilders.add(Segment.newBuilder().setV1Idx(j+i*25).setV2Idx(j+i*25+25));
            }
        }
        return segmentBuilders;
    }

    private ArrayList<Polygon.Builder> initializePolygons() {
        ArrayList<Polygon.Builder> polygonBuilders = new ArrayList<Polygon.Builder>();
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
        return polygonBuilders;
    }

    private ArrayList<Polygon.Builder> addCentroidVertices(ArrayList<Polygon.Builder> polygonBuilders) {
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 24; j++) {
                polygonBuilders.set(j+24*i, polygonBuilders.get(j+24*i).setCentroidIdx(j+24*i+625));
            }
        }
        return polygonBuilders;
    }

    private ArrayList<Polygon.Builder> addNeighbors(ArrayList<Polygon.Builder> polygonBuilders) {
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 24; j++) {
                int current = j + 24*i;
                int left = current - 24;
                int top = current - 1;
                int right = current + 24;
                int bottom = current + 1;
                Polygon.Builder p = polygonBuilders.get(current);
                if (i == 0 && j == 0)
                    p.addNeighborIdxs(0).addNeighborIdxs(1).setNeighborIdxs(0, bottom).setNeighborIdxs(1, right); // no left or top
                else if (i == 0 && j == 23)
                    p.addNeighborIdxs(0).addNeighborIdxs(1).setNeighborIdxs(0, top).setNeighborIdxs(1, right); // no left or bottom
                else if (i == 23 && j == 0)
                    p.addNeighborIdxs(0).addNeighborIdxs(1).setNeighborIdxs(0, bottom).setNeighborIdxs(1, left); // no right or top
                else if (i == 23 && j == 23)
                    p.addNeighborIdxs(0).addNeighborIdxs(1).setNeighborIdxs(0, top).setNeighborIdxs(1, left); // no right or bottom
                else if (i == 0)
                    p.addNeighborIdxs(0).addNeighborIdxs(1).addNeighborIdxs(2).setNeighborIdxs(0, top).setNeighborIdxs(1, bottom).setNeighborIdxs(2, right); // no left
                else if (i == 23)
                    p.addNeighborIdxs(0).addNeighborIdxs(1).addNeighborIdxs(2).setNeighborIdxs(0, top).setNeighborIdxs(1, bottom).setNeighborIdxs(2, left); // no right
                else if (j == 0)
                    p.addNeighborIdxs(0).addNeighborIdxs(1).addNeighborIdxs(2).setNeighborIdxs(0, bottom).setNeighborIdxs(1, left).setNeighborIdxs(2, right); // no top
                else if (j == 23)
                    p.addNeighborIdxs(0).addNeighborIdxs(1).addNeighborIdxs(2).setNeighborIdxs(0, top).setNeighborIdxs(1, left).setNeighborIdxs(2, right); // no bottom
                else
                    p.addNeighborIdxs(0).addNeighborIdxs(1).addNeighborIdxs(2).addNeighborIdxs(3).setNeighborIdxs(0, left).setNeighborIdxs(1, top).setNeighborIdxs(2, right).setNeighborIdxs(3, bottom); // all 4 neighbours
                polygonBuilders.set(current, p);
            }
        }
        return polygonBuilders;
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

    private ArrayList<Segment.Builder> addSegmentProperties(ArrayList<Segment.Builder> segmentBuilders, ArrayList<Vertex.Builder> vertexBuilders, String rgbValue, float thickness) {
        String colorCode = rgbValue;
        for(Segment.Builder s : segmentBuilders){
            // Color segments by averaging out adjacent vertex colors if there is no given color
            if (rgbValue == null) {
                Vertex.Builder v1 = vertexBuilders.get(s.getV1Idx());
                Vertex.Builder v2 = vertexBuilders.get(s.getV1Idx());
                Color v1Color = extractColor(v1.getPropertiesList());
                Color v2Color = extractColor(v2.getPropertiesList());
                int segmentRed = (v1Color.getRed() + v2Color.getRed())/2;
                int segmentGreen = (v1Color.getGreen() + v2Color.getGreen())/2;
                int segmentBlue = (v1Color.getBlue() + v2Color.getBlue())/2;
                colorCode = segmentRed + "," + segmentGreen + "," + segmentBlue;
            }
            // Set segment color
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            s.addProperties(color);
            // Assign segment thickness
            s.addProperties(Property.newBuilder().setKey("thickness").setValue(String.valueOf(thickness)).build());
        }
        return segmentBuilders;
    }

    private ArrayList<Polygon.Builder> addPolygonProperties(ArrayList<Polygon.Builder> polygonBuilders, String rgbValue, float thickness) {
        for (Polygon.Builder p : polygonBuilders) {
            p.addProperties(Property.newBuilder().setKey("rgb_color").setValue(rgbValue).build());
            p.addProperties(Property.newBuilder().setKey("thickness").setValue(String.valueOf(thickness)).build());
        }
        return polygonBuilders;
    }

    private Color extractColor(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color"))
                val = p.getValue();
        }
        if (val == null)
            return Color.BLACK;
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        int transparency = 255;
        if (raw.length == 4)
            transparency = Integer.parseInt(raw[3]);
        return new Color(red, green, blue, transparency);
    }
}
