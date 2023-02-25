package ca.mcmaster.cas.se2aa4.a2.generator;

import java.util.ArrayList;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

public class VoronoiGen {

    private ArrayList<Geometry> polygons = new ArrayList<>();

    public Mesh.Builder generate(Mesh.Builder mesh) {

        VoronoiDiagramBuilder diagram = new VoronoiDiagramBuilder();
        PrecisionModel round = new PrecisionModel(100);
        ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
        // Set sites to be already existing vertices in the mesh
        for (Vertex v : mesh.getVerticesList())
            coords.add(new Coordinate(v.getX(), v.getY()));
        diagram.setSites(coords);

        polygons.clear();
        GeometryFactory factory = new GeometryFactory(round);
        Geometry g = new GeometryCollection(null, factory);
        // Compute Voronoi diagram and add each polygon to ArrayList
        g = diagram.getDiagram(factory);
        for (int i = 0; i < g.getNumGeometries(); i++)
            polygons.add(g.getGeometryN(i));

        // Add segments with appropriate vertices from Voronoi diagram
        int pointsMapped = 0;
        for (int i = 0; i < polygons.size(); i++) {
            int polygonSideNum = polygons.get(i).getCoordinates().length;
            for (int j = pointsMapped; j < polygonSideNum + pointsMapped; j++) {
                int v1 = j;
                int v2 = j+1;
                if (j == polygonSideNum + pointsMapped - 1)
                    v2 = pointsMapped;
                Segment s = Segment.newBuilder().setV1Idx(v1 + mesh.getVerticesCount()).setV2Idx(v2 + mesh.getVerticesCount()).build();
                mesh.addSegments(s);
            }
            pointsMapped += polygonSideNum;
        }

        // Add vertices computed by Voronoi diagram to mesh
        for (Coordinate c : g.getCoordinates())
            mesh.addVertices(Vertex.newBuilder().setX(round.makePrecise(c.x)).setY(round.makePrecise(c.y)).build());

        return mesh;

    }

    public ArrayList<Geometry> getPolygons() {
        return polygons;
    }

}