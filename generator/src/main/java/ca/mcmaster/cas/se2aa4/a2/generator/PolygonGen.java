package ca.mcmaster.cas.se2aa4.a2.generator;

import java.util.ArrayList;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

public class PolygonGen {
    public Mesh generate(Mesh mesh){
        // Create all the polygon builders
        ArrayList<Structs.Polygon.Builder> polygonBuilders = initializePolygons();
        // Attribute thickness and color to polygons
        polygonBuilders = addPolygonProperties(polygonBuilders, "0,0,0", 0.5f);
        // Add the centroid vertices to their respective polygons
        polygonBuilders = addCentroidVertices(polygonBuilders);
        // Include neighbor indices for all polygons in no particular order
        polygonBuilders = addNeighbors(polygonBuilders);
        ArrayList<Polygon> polygons = new ArrayList<>();
        for (Polygon.Builder p : polygonBuilders)
            polygons.add(p.build());

        return Mesh.newBuilder().addAllVertices(mesh.getVerticesList()).addAllSegments(mesh.getSegmentsList()).addAllPolygons(polygons).build();
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

    private ArrayList<Polygon.Builder> addPolygonProperties(ArrayList<Polygon.Builder> polygonBuilders, String rgbValue, float thickness) {
        for (Polygon.Builder p : polygonBuilders) {
            p.addProperties(Property.newBuilder().setKey("rgb_color").setValue(rgbValue).build());
            p.addProperties(Property.newBuilder().setKey("thickness").setValue(String.valueOf(thickness)).build());
        }
        return polygonBuilders;
    }
}
