package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.awt.geom.Line2D;


public class GraphicRenderer {

    private static final int THICKNESS = 3;
    public void render(Mesh aMesh, Graphics2D canvas) {
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.5f);
        canvas.setStroke(stroke);
        // Draw vertices
        for (Vertex v: aMesh.getVerticesList()) {
            double centre_x = v.getX() - (THICKNESS/2.0d);
            double centre_y = v.getY() - (THICKNESS/2.0d);
            Color old = canvas.getColor();
            canvas.setColor(extractColor(v.getPropertiesList()));
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, THICKNESS, THICKNESS);
            canvas.fill(point);
            canvas.setColor(old);
        }
        canvas.setStroke(new BasicStroke(0.25f));
        // Draw segments
        for (Segment s : aMesh.getSegmentsList()) {
            Vertex v1 = aMesh.getVertices(s.getV1Idx());
            Vertex v2 = aMesh.getVertices(s.getV2Idx());
            Color old = canvas.getColor();
            canvas.setColor(extractColor(s.getPropertiesList())); // change color to average of v1 and v2
            Line2D line = new Line2D.Double(v1.getX(), v1.getY(), v2.getX(), v2.getY());
            canvas.draw(line);
            canvas.setColor(old);
        }
        // Draw polygons
        /*canvas.setColor(Color.RED);
        for (Polygon p : aMesh.getPolygonsList()) {
            Segment s1 = aMesh.getSegments(p.getSegmentIdxs(0));
            Segment s2 = aMesh.getSegments(p.getSegmentIdxs(1));
            Segment s3 = aMesh.getSegments(p.getSegmentIdxs(2));
            Segment s4 = aMesh.getSegments(p.getSegmentIdxs(3));
            for (Segment s : new Segment[] {s1, s2, s3, s4}) {
                Vertex v1 = aMesh.getVertices(s.getV1Idx());
                Vertex v2 = aMesh.getVertices(s.getV2Idx());
                Line2D line = new Line2D.Double(v1.getX(), v1.getY(), v2.getX(), v2.getY());
                canvas.draw(line);
            }
        }*/
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
