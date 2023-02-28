package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;

import java.util.ArrayList;
import java.util.List;

public class DelaunayTriangulationGen {

    public List<Polygon> generate(Mesh.Builder mesh){
        GeometryFactory geometryFactory = new GeometryFactory();
        List<Coordinate> coords = new ArrayList<>();
        // Set sites to be already existing vertices in the mesh
        for (Structs.Polygon p : mesh.getPolygonsList()) {
            Structs.Vertex centroid = mesh.getVertices(p.getCentroidIdx());
            coords.add(new Coordinate(centroid.getX(), centroid.getY()));
        }

        DelaunayTriangulationBuilder triangleBuilder = new DelaunayTriangulationBuilder();
        triangleBuilder.setSites(coords);

        // Creates the triangles
        Geometry triangles = triangleBuilder.getTriangles(geometryFactory);
        // Polygon here is JTS Polygon
        List<Polygon> trianglesProduced = new ArrayList<>();

        if(triangles instanceof GeometryCollection geometryCollection) {
            for(int i = 0; i < geometryCollection.getNumGeometries(); i++) {
                // Convert triangles from Geometry into Polygons and add them to triangles produced list
                trianglesProduced.add((Polygon) geometryCollection.getGeometryN(i));
            }
        }

        return trianglesProduced;
    }
}
