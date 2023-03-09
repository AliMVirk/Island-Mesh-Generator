package ca.mcmaster.cas.se2aa4.a2.generator;

import java.util.ArrayList;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

public class PolygonGen {

    private final int width;
    private final int height;
    private final int square_size;

    public PolygonGen(int x, int y, int size) {
        width = x;
        height = y;
        square_size = size;
    }

    public Mesh.Builder generatePolygons(Mesh.Builder mesh){
        // Create all the polygon builders
        ArrayList<Structs.Polygon.Builder> polygonBuilders = initializePolygons();
        // Attribute thickness and color to polygons
        polygonBuilders = addPolygonProperties(polygonBuilders, "0,0,0", 0.5f);
        // Add the centroid vertices to their respective polygons
        polygonBuilders = addCentroidVertices(polygonBuilders);
        // Include neighbor indices for all polygons in no particular order
        polygonBuilders = addNeighbors(polygonBuilders);

        // Build all the vertices and add them to mesh
        for (Polygon.Builder p : polygonBuilders)
            mesh.addPolygons(p.build());

        return mesh;
    }

    private ArrayList<Polygon.Builder> initializePolygons() {
        ArrayList<Polygon.Builder> polygonBuilders = new ArrayList<>();
        
        for (int i = 0; i < (width/square_size)-1; i++) {
            for (int j = 0; j < ((height/square_size)-1)*2-1; j += 2) {
                int left = j + i*((height/square_size)*2-1);
                int top = left + 1;
                int right = left + ((height/square_size)*2-1);
                int bottom = left + 3;
                if (j == (height/square_size)*2-4)
                    bottom--;
                if (i == width/square_size-2)
                    right -= 0.5 * j;
                polygonBuilders.add(Polygon.newBuilder().addSegmentIdxs(0).addSegmentIdxs(1).addSegmentIdxs(2).addSegmentIdxs(3).setSegmentIdxs(0, left).setSegmentIdxs(1, top).setSegmentIdxs(2, right).setSegmentIdxs(3, bottom));
            }
        }

        return polygonBuilders;
    }

    private ArrayList<Polygon.Builder> addCentroidVertices(ArrayList<Polygon.Builder> polygonBuilders) {
        for (int i = 0; i < width/square_size - 1; i++) {
            for (int j = 0; j < height/square_size - 1; j++) {
                polygonBuilders.set(j+(height/square_size - 1)*i, polygonBuilders.get(j+(height/square_size - 1)*i).setCentroidIdx(j+(height/square_size - 1)*i+(width*height/((int) Math.pow(square_size,2)))));
            }
        }
        return polygonBuilders;
    }

    private ArrayList<Polygon.Builder> addNeighbors(ArrayList<Polygon.Builder> polygonBuilders) {
        for (int i = 0; i < width/square_size - 1; i++) {
            for (int j = 0; j < height/square_size - 1; j++) {
                int current = j + (height/square_size - 1)*i;
                int left = current - (height/square_size - 1);
                int top = current - 1;
                int right = current + (height/square_size - 1);
                int bottom = current + 1;
                Polygon.Builder p = polygonBuilders.get(current);
                if (i == 0 && j == 0)
                    p.addNeighborIdxs(0).addNeighborIdxs(1).addNeighborIdxs(2).setNeighborIdxs(0, bottom).setNeighborIdxs(1, right).setNeighborIdxs(2, right + 1); // no left or top
                else if (i == 0 && j == height/square_size - 2)
                    p.addNeighborIdxs(0).addNeighborIdxs(1).addNeighborIdxs(2).setNeighborIdxs(0, top).setNeighborIdxs(1, right).setNeighborIdxs(2, right-1); // no left or bottom
                else if (i == width/square_size - 2 && j == 0)
                    p.addNeighborIdxs(0).addNeighborIdxs(1).addNeighborIdxs(2).setNeighborIdxs(0, bottom).setNeighborIdxs(1, left).setNeighborIdxs(2, left+1); // no right or top
                else if (i == width/square_size - 2 && j == height/square_size - 2)
                    p.addNeighborIdxs(0).addNeighborIdxs(1).addNeighborIdxs(2).setNeighborIdxs(0, top).setNeighborIdxs(1, left).setNeighborIdxs(2, left - 1); // no right or bottom
                else if (i == 0)
                    p.addNeighborIdxs(0).addNeighborIdxs(1).addNeighborIdxs(2).addNeighborIdxs(3).addNeighborIdxs(4).setNeighborIdxs(0, top).setNeighborIdxs(1, bottom).setNeighborIdxs(2, right).setNeighborIdxs(3, right-1).setNeighborIdxs(4, right+1); // no left
                else if (i == width/square_size - 2)
                    p.addNeighborIdxs(0).addNeighborIdxs(1).addNeighborIdxs(2).addNeighborIdxs(3).addNeighborIdxs(4).setNeighborIdxs(0, top).setNeighborIdxs(1, bottom).setNeighborIdxs(2, left).setNeighborIdxs(3, left+1).setNeighborIdxs(4, left-1); // no right
                else if (j == 0)
                    p.addNeighborIdxs(0).addNeighborIdxs(1).addNeighborIdxs(2).addNeighborIdxs(3).addNeighborIdxs(4).setNeighborIdxs(0, bottom).setNeighborIdxs(1, left).setNeighborIdxs(2, right).setNeighborIdxs(3, left+1).setNeighborIdxs(4, right+1); // no top
                else if (j == height/square_size - 2)
                    p.addNeighborIdxs(0).addNeighborIdxs(1).addNeighborIdxs(2).addNeighborIdxs(3).addNeighborIdxs(4).setNeighborIdxs(0, top).setNeighborIdxs(1, left).setNeighborIdxs(2, right).setNeighborIdxs(3, left-1).setNeighborIdxs(4, right-1); // no bottom
                else
                    p.addNeighborIdxs(0).addNeighborIdxs(1).addNeighborIdxs(2).addNeighborIdxs(3).addNeighborIdxs(4).addNeighborIdxs(5).addNeighborIdxs(6).addNeighborIdxs(7).setNeighborIdxs(0, left).setNeighborIdxs(1, top).setNeighborIdxs(2, right).setNeighborIdxs(3, bottom).setNeighborIdxs(4, right+1).setNeighborIdxs(5, right-1).setNeighborIdxs(6, left+1).setNeighborIdxs(7, left-1); // all 8 neighbours
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
