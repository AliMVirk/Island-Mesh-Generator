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

public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;

    public Mesh generate() {
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Segment> segments = new ArrayList<>();
        // Create all the vertices
        for(int x = 0; x < width; x += square_size) {
            for(int y = 0; y < height; y += square_size) {
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y).build());
            }
        }
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 24; j++) {
                segments.add(Segment.newBuilder().setV1Idx(j+i*25).setV2Idx(j+i*25+1).build());
                if (i != 24)
                    segments.add(Segment.newBuilder().setV1Idx(j+i*25).setV2Idx(j+i*25+25).build());
            }
        }
        // Distribute colors randomly. Vertices are immutable, need to enrich them
        ArrayList<Vertex> verticesWithColors = new ArrayList<>();
        ArrayList<Segment> segmentsWithColors = new ArrayList<>();
        Random bag = new Random();
        for(Vertex v: vertices){
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex colored = Vertex.newBuilder(v).addProperties(color).build();
            verticesWithColors.add(colored);
        }

        for(Segment s: segments){
            Vertex v1 = verticesWithColors.get(s.getV1Idx());
            Vertex v2 = verticesWithColors.get(s.getV2Idx());
            Color v1Color = extractColor(v1.getPropertiesList());
            Color v2Color = extractColor(v2.getPropertiesList());
            int segmentRed = (v1Color.getRed() + v2Color.getRed())/2;
            int segmentGreen = (v1Color.getGreen() + v2Color.getGreen())/2;
            int segmentBlue = (v1Color.getBlue() + v2Color.getBlue())/2;
            String colorCode = segmentRed + "," + segmentGreen + "," + segmentBlue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Segment colored = Segment.newBuilder(s).addProperties(color).build();
            segmentsWithColors.add(colored);
        }

        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segmentsWithColors).build();
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
