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

    public void render(Mesh aMesh, Graphics2D canvas, boolean debugMode) {
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.5f);
        canvas.setStroke(stroke);
        if (debugMode){
            // Draw centroid vertices in red when debug mode is active
            for (int i = 625; i < aMesh.getVerticesCount(); i++) {
                Vertex v = aMesh.getVertices(i);
                float thickness = extractThickness(v.getPropertiesList());
                double centre_x = v.getX() - (thickness/2.0d);
                double centre_y = v.getY() - (thickness/2.0d);
                canvas.setColor(Color.RED);
                Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, thickness, thickness);
                canvas.fill(point);
            }
            // Draw polygons in black when debug mode is active
            for (Polygon p : aMesh.getPolygonsList()) {
                canvas.setColor(extractColor(p.getPropertiesList()));
                canvas.setStroke(new BasicStroke(extractThickness(p.getPropertiesList())));
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
            }
            // Draw neighboring relations in grey when debug mode is active
            canvas.setColor(new Color(150, 150, 150));
            canvas.setStroke(new BasicStroke(0.25f));
            for (Polygon p : aMesh.getPolygonsList()) {
                Vertex v1 = aMesh.getVertices(p.getCentroidIdx());
                for (int q : p.getNeighborIdxsList()) {
                    Vertex v2 = aMesh.getVertices(aMesh.getPolygons(q).getCentroidIdx());
                    Line2D line = new Line2D.Double(v1.getX(), v1.getY(), v2.getX(), v2.getY());
                    canvas.draw(line);
                }
            }
        } else {
            // Draw vertices
            for (Vertex v : aMesh.getVerticesList()) {
                float thickness = extractThickness(v.getPropertiesList());
                double centre_x = v.getX() - (thickness / 2.0d);
                double centre_y = v.getY() - (thickness / 2.0d);
                Color old = canvas.getColor();
                canvas.setColor(extractColor(v.getPropertiesList()));
                Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, thickness, thickness);
                canvas.fill(point);
                canvas.setColor(old);
            }
            // Draw segments
            for (Segment s : aMesh.getSegmentsList()) {
                canvas.setStroke(new BasicStroke(extractThickness(s.getPropertiesList()))); // Set new stroke according to thickness
                Vertex v1 = aMesh.getVertices(s.getV1Idx());
                Vertex v2 = aMesh.getVertices(s.getV2Idx());
                Color old = canvas.getColor();
                canvas.setColor(extractColor(s.getPropertiesList())); // change color to average of v1 and v2
                Line2D line = new Line2D.Double(v1.getX(), v1.getY(), v2.getX(), v2.getY());
                canvas.draw(line);
                canvas.setColor(old);
            }
        }
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

    private float extractThickness(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("thickness"))
                val = p.getValue();
        }
        if (val == null)
            return 0.5f;
        return Float.parseFloat(val);
    }

}
