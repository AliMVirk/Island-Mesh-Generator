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
            drawVertices(aMesh, canvas, 625, Color.RED);
            // Draw polygons in black when debug mode is active
            drawPolygons(aMesh, canvas);
            // Draw neighboring relations in grey when debug mode is active
            drawNeighborRelations(aMesh, canvas);
        } else {
            // Draw vertices
            drawVertices(aMesh, canvas, 0, null);
            // Draw segments
            drawSegments(aMesh, canvas);
        }
    }

    private void drawVertices(Mesh aMesh, Graphics2D canvas, int offset, Color staticColor) {
        for (int i = offset; i < aMesh.getVerticesCount(); i++) {
            Vertex v = aMesh.getVertices(i);
            float thickness = extractThickness(v.getPropertiesList());
            double centre_x = getX(v) - (thickness / 2.0d);
            double centre_y = getY(v) - (thickness / 2.0d);
            Color old = canvas.getColor();
            // If there is no static color choice, get color from vertex properties
            if (staticColor == null)
                canvas.setColor(extractColor(v.getPropertiesList()));
            else
                canvas.setColor(staticColor);
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, thickness, thickness);
            canvas.fill(point);
            canvas.setColor(old);
        }
    }

    private void drawSegments(Mesh aMesh, Graphics2D canvas) {
        for (Segment s : aMesh.getSegmentsList()) {
            canvas.setStroke(new BasicStroke(extractThickness(s.getPropertiesList())));
            Vertex v1 = aMesh.getVertices(s.getV1Idx());
            Vertex v2 = aMesh.getVertices(s.getV2Idx());
            Color old = canvas.getColor();
            canvas.setColor(extractColor(s.getPropertiesList()));
            Line2D line = new Line2D.Double(getX(v1), getY(v1), getX(v2), getY(v2));
            canvas.draw(line);
            canvas.setColor(old);
        }
    }

    private void drawPolygons(Mesh aMesh, Graphics2D canvas) {
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
                Line2D line = new Line2D.Double(getX(v1), getY(v1), getX(v2), getY(v2));
                canvas.draw(line);
            }
        }
    }

    private void drawNeighborRelations(Mesh aMesh, Graphics2D canvas) {
        canvas.setColor(new Color(150, 150, 150));
        canvas.setStroke(new BasicStroke(0.25f));
        for (Polygon p : aMesh.getPolygonsList()) {
            Vertex v1 = aMesh.getVertices(p.getCentroidIdx());
            for (int q : p.getNeighborIdxsList()) {
                Vertex v2 = aMesh.getVertices(aMesh.getPolygons(q).getCentroidIdx());
                Line2D line = new Line2D.Double(getX(v1), getY(v1), getX(v2), getY(v2));
                canvas.draw(line);
            }
        }
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

    private float extractThickness(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("thickness"))
                val = p.getValue();
        }
        if (val == null)
            return 0.5f;
        return (float) Math.round(Float.parseFloat(val) * 100) / 100;
    }

    private double getX(Vertex v) {
        return Math.round(v.getX() * 100) / 100;
    }

    private double getY(Vertex v) {
        return Math.round(v.getY() * 100) / 100;
    }

}
