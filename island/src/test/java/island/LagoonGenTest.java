package island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class LagoonGenTest {

    private LagoonGen lgn = new LagoonGen(500, 500, 100, 200);

    @Test
    public void meshIsNotNull() {
        Mesh aMesh = lgn.transform(Mesh.newBuilder().build()).build();
        assertNotNull(aMesh);
    }

    @Test
    public void lagoonInCircle() {
        // Create test mesh
        Vertex v1 = Vertex.newBuilder().setX(0).setY(0).build();
        Vertex v2 = Vertex.newBuilder().setX(250).setY(250).build();
        Polygon p1 = Polygon.newBuilder().setCentroidIdx(0).build(); // Non-lagoon polygon
        Polygon p2 = Polygon.newBuilder().setCentroidIdx(1).build(); // Lagoon polygon
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addPolygons(p1).addPolygons(p2).build();

        // Check for correct polygon types
        aMesh = lgn.transform(aMesh).build();
        for (Property p : aMesh.getPolygons(0).getPropertiesList()) {
            if (p.getKey().equals("tile_type"))
                assertNotEquals(p.getValue(), "lagoon");
        }
        for (Property p : aMesh.getPolygons(1).getPropertiesList()) {
            if (p.getKey().equals("tile_type"))
                assertEquals(p.getValue(), "lagoon");
        }
    }

    @Test
    public void landInCircle() {
        // Create test mesh
        Vertex v1 = Vertex.newBuilder().setX(0).setY(0).build();
        Vertex v2 = Vertex.newBuilder().setX(100).setY(250).build(); // Halfway in left edge of ring
        Polygon p1 = Polygon.newBuilder().setCentroidIdx(0).build(); // Non-land polygon
        Polygon p2 = Polygon.newBuilder().setCentroidIdx(1).build(); // Land polygon
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addPolygons(p1).addPolygons(p2).build();

        // Check for correct polygon types
        aMesh = lgn.transform(aMesh).build();
        for (Property p : aMesh.getPolygons(0).getPropertiesList()) {
            if (p.getKey().equals("tile_type"))
                assertNotEquals(p.getValue(), "land");
        }
        for (Property p : aMesh.getPolygons(1).getPropertiesList()) {
            if (p.getKey().equals("tile_type"))
                assertEquals(p.getValue(), "land");
        }
    }

    @Test
    public void checkLagoonRadius() {
        // Create test mesh
        Vertex v1 = Vertex.newBuilder().setX(150).setY(250).build(); // On left edge of circle
        Vertex v2 = Vertex.newBuilder().setX(250).setY(150).build(); // On top edge of circle
        Vertex v3 = Vertex.newBuilder().setX(149).setY(250).build(); // Outside left edge of circle
        Polygon p1 = Polygon.newBuilder().setCentroidIdx(0).build(); // Lagoon polygon
        Polygon p2 = Polygon.newBuilder().setCentroidIdx(1).build(); // Lagoon polygon
        Polygon p3 = Polygon.newBuilder().setCentroidIdx(2).build(); // Non-lagoon polygon
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addVertices(v3).addPolygons(p1).addPolygons(p2).addPolygons(p3).build();

        // Check for correct polygon types
        aMesh = lgn.transform(aMesh).build();
        for (Property p : aMesh.getPolygons(0).getPropertiesList()) {
            if (p.getKey().equals("tile_type"))
                assertEquals(p.getValue(), "lagoon");
        }
        for (Property p : aMesh.getPolygons(1).getPropertiesList()) {
            if (p.getKey().equals("tile_type"))
                assertEquals(p.getValue(), "lagoon");
        }
        for (Property p : aMesh.getPolygons(2).getPropertiesList()) {
            if (p.getKey().equals("tile_type"))
                assertNotEquals(p.getValue(), "lagoon");
        }
    }

    @Test
    public void checkLandRadius() {
        // Create test mesh
        Vertex v1 = Vertex.newBuilder().setX(50).setY(250).build(); // On left edge of circle
        Vertex v2 = Vertex.newBuilder().setX(250).setY(50).build(); // On top edge of circle
        Vertex v3 = Vertex.newBuilder().setX(49).setY(250).build(); // Outside left edge of circle
        Polygon p1 = Polygon.newBuilder().setCentroidIdx(0).build(); // Land polygon
        Polygon p2 = Polygon.newBuilder().setCentroidIdx(1).build(); // Land polygon
        Polygon p3 = Polygon.newBuilder().setCentroidIdx(2).build(); // Non-land polygon
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addVertices(v3).addPolygons(p1).addPolygons(p2).addPolygons(p3).build();

        // Check for correct polygon types
        aMesh = lgn.transform(aMesh).build();
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
    public void beachExists() {
        // Create test mesh
        Vertex v1 = Vertex.newBuilder().setX(0).setY(0).build();
        Vertex v2 = Vertex.newBuilder().setX(100).setY(250).build(); // Halfway in left edge of ring
        Polygon p1 = Polygon.newBuilder().setCentroidIdx(0).build(); // Water polygon
        Polygon p2 = Polygon.newBuilder().setCentroidIdx(1).addNeighborIdxs(0).setNeighborIdxs(0, 0).build(); // Land polygon
        Mesh aMesh = Mesh.newBuilder().addVertices(v1).addVertices(v2).addPolygons(p1).addPolygons(p2).build();

        // Check for correct polygon types
        aMesh = lgn.transform(aMesh).build();
        for (Property p : aMesh.getPolygons(0).getPropertiesList()) {
            if (p.getKey().equals("tile_type"))
                assertNotEquals(p.getValue(), "land");
        }
        for (Property p : aMesh.getPolygons(1).getPropertiesList()) {
            if (p.getKey().equals("tile_type"))
                assertEquals(p.getValue(), "beach");
        }
    }

}
