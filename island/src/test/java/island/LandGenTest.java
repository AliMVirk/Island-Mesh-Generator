package island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

import island.shapes.Ellipse;
import island.shapes.Rectangle;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;

import static org.junit.jupiter.api.Assertions.*;

public class LandGenTest {
    
    private LandGen lgn = new LandGen();
    private Ellipse ellipse = new Ellipse();
    private Rectangle rect = new Rectangle();

    @Test
    private void landInRegion() {
        // Create test mesh
        Vertex v1 = Vertex.newBuilder().setX(0).setY(0).build();
        Vertex v2 = Vertex.newBuilder().setX(250).setY(250).build();
        Polygon p1 = Polygon.newBuilder().setCentroidIdx(0).build(); // Land polygon
        Polygon p2 = Polygon.newBuilder().setCentroidIdx(1).build(); // Non-land polygon
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addPolygons(p1).addPolygons(p2).build();

        // Create path
        Path2D path = new Path2D.Double();
        path.moveTo(0, 0);
        path.lineTo(50, 0);
        path.lineTo(0, 50);
        path.lineTo(0, 0);
        path.closePath();

        // Check for correct polygon types
        aMesh = lgn.createLand(aMesh, path).build();
        for (Property p : aMesh.getPolygons(0).getPropertiesList()) {
            if (p.getKey().equals("tile_type"))
                assertEquals(p.getValue(), "land");
        }
        for (Property p : aMesh.getPolygons(1).getPropertiesList()) {
            if (p.getKey().equals("tile_type"))
                assertNotEquals(p.getValue(), "land");
        }
    }

    @Test
    public void checkRectangleBoundary() {
        // Create test mesh
        Vertex v1 = Vertex.newBuilder().setX(0).setY(0).build();
        Vertex v2 = Vertex.newBuilder().setX(49).setY(0).build();
        Vertex v3 = Vertex.newBuilder().setX(51).setY(0).build();
        Polygon p1 = Polygon.newBuilder().setCentroidIdx(0).build(); // Land polygon
        Polygon p2 = Polygon.newBuilder().setCentroidIdx(1).build(); // Land polygon
        Polygon p3 = Polygon.newBuilder().setCentroidIdx(2).build(); // Non-land polygon
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addVertices(v3).addPolygons(p1).addPolygons(p2).addPolygons(p3).build();

        // Create path
        List<Coordinate> coords = new ArrayList<>();
        coords = rect.generateRectangle(0, 0, 50, 50);
        Path2D path = rect.build(coords);

        // Check for correct polygon types
        aMesh = lgn.createLand(aMesh, path).build();
        for (Property p : aMesh.getPolygons(0).getPropertiesList()) {
            if (p.getKey().equals("tile_type"))
                assertEquals(p.getValue(), "land");
        }
        for (Property p : aMesh.getPolygons(1).getPropertiesList()) {
            if (p.getKey().equals("tile_type"))
                assertEquals(p.getValue(), "land");
        }
        for (Property p : aMesh.getPolygons(2).getPropertiesList()) {
            if (p.getKey().equals("tile_type"))
                assertNotEquals(p.getValue(), "land");
        }
    }


    @Test
    public void checkEllipseBoundary() {
        // Create test mesh
        Vertex v1 = Vertex.newBuilder().setX(0).setY(0).build();
        Vertex v2 = Vertex.newBuilder().setX(24).setY(0).build();
        Vertex v3 = Vertex.newBuilder().setX(26).setY(0).build();
        Polygon p1 = Polygon.newBuilder().setCentroidIdx(0).build(); // Land polygon
        Polygon p2 = Polygon.newBuilder().setCentroidIdx(1).build(); // Land polygon
        Polygon p3 = Polygon.newBuilder().setCentroidIdx(2).build(); // Non-land polygon
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addVertices(v3).addPolygons(p1).addPolygons(p2).addPolygons(p3).build();

        // Create path
        Ellipse2D shape = ellipse.generateEllipse(0, 0, 50, 50);
        Path2D path = ellipse.build(shape);

        // Check for correct polygon types
        aMesh = lgn.createLand(aMesh, path).build();
        for (Property p : aMesh.getPolygons(0).getPropertiesList()) {
            if (p.getKey().equals("tile_type"))
                assertEquals(p.getValue(), "land");
        }
        for (Property p : aMesh.getPolygons(1).getPropertiesList()) {
            if (p.getKey().equals("tile_type"))
                assertEquals(p.getValue(), "land");
        }
        for (Property p : aMesh.getPolygons(2).getPropertiesList()) {
            if (p.getKey().equals("tile_type"))
                assertNotEquals(p.getValue(), "land");
        }
    }

}
