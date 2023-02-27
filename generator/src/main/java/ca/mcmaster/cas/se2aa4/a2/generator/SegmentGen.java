package ca.mcmaster.cas.se2aa4.a2.generator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

public class SegmentGen {

    private final int width;
    private final int height;
    private final int square_size;

    public SegmentGen(int x, int y, int size) {
        width = x;
        height = y;
        square_size = size;
    }

    public Mesh.Builder generateSegments(Mesh.Builder mesh){
        //Create all the segments builders
        ArrayList<Segment.Builder> segmentBuilders = initializeSegments();
        // Attribute thickness and color to segments
        segmentBuilders = addSegmentProperties(segmentBuilders, mesh.getVerticesList(), null, 0.25f);

        // Build all the vertices and add them to mesh
        for (Segment.Builder s : segmentBuilders)
            mesh.addSegments(s.build());

        return mesh;
    }

    private ArrayList<Segment.Builder> initializeSegments() {
        ArrayList<Segment.Builder> segmentBuilders = new ArrayList<>();
        for (int i = 0; i < width / square_size; i++) {
            for (int j = 0; j < height / square_size; j++) {
                if (j != height / square_size - 1)
                    segmentBuilders.add(Segment.newBuilder().setV1Idx(j+i*(height/square_size)).setV2Idx(j+i*(height/square_size) + 1));
                if (i != width / square_size - 1)
                    segmentBuilders.add(Segment.newBuilder().setV1Idx(j+i*(height/square_size)).setV2Idx(j+(i+1)*(height/square_size)));
            }
        }
        return segmentBuilders;
    }

    private ArrayList<Segment.Builder> addSegmentProperties(ArrayList<Segment.Builder> segmentBuilders, List<Vertex> vertexBuilders, String rgbValue, float thickness) {
        String colorCode = rgbValue;
        for(Segment.Builder s : segmentBuilders){
            // Color segments by averaging out adjacent vertex colors if there is no given color
            if (rgbValue == null) {
                Vertex v1 = vertexBuilders.get(s.getV1Idx());
                Vertex v2 = vertexBuilders.get(s.getV1Idx());
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
