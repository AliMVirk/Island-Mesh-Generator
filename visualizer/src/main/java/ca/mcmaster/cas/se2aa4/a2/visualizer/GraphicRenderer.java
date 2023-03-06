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
import java.util.ArrayList;
import java.util.List;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;


public class GraphicRenderer {

    public void render(Mesh aMesh, Graphics2D canvas, boolean debugMode) {
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.5f);
        canvas.setStroke(stroke);
        // Fetch relevant mesh properties
        boolean irregular = false;
        int divider = 0;
        for (Property p : aMesh.getPropertiesList()) {
            if (p.getKey().equals("divider"))
                divider = Integer.parseInt(p.getValue());
            else if (p.getKey().equals("mesh_type"))
                irregular = Boolean.parseBoolean(p.getValue());
        }
        if (debugMode){
            // Draw centroid vertices in red when debug mode is active
            if (irregular)
                drawVertices(aMesh, canvas, 0, divider, null);
            else
                drawVertices(aMesh, canvas, divider, aMesh.getVerticesCount(), Color.RED);
            // Draw polygons in black when debug mode is active
            drawPolygons(aMesh, canvas);
            // Draw neighboring relations in grey when debug mode is active
            drawNeighborRelations(aMesh, canvas);
        } else {
            // Draw tiles
            drawTiles(aMesh, canvas, irregular);
            // Draw vertices
            drawVertices(aMesh, canvas, 0, divider, null);
            // Draw segments
            drawSegments(aMesh, canvas);
        }
    }

    private void drawVertices(Mesh aMesh, Graphics2D canvas, int start, int end, Color staticColor) {
        for (int i = start; i < end; i++) {
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
            for (int i : p.getSegmentIdxsList()) {
                Segment s = aMesh.getSegments(i);
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

    private void drawTiles(Mesh aMesh, Graphics2D canvas, boolean isIrregular) {
        for (Polygon p : aMesh.getPolygonsList()) {
            Color color = Color.WHITE;
            // Set color based on polygon tile type
            for (Property q : p.getPropertiesList()) {
                if (q.getKey().equals("tile_type")) {
                    if (q.getValue().equals("land"))
                        color = Color.ORANGE;
                    else if (q.getValue().equals("water"))
                        color = Color.BLUE;
                    else if (q.getValue().equals("lagoon"))
                        color = Color.CYAN;
                    else if (q.getValue().equals("beach"))
                        color = Color.YELLOW;
                }
            }
            canvas.setColor(color);
            canvas.setStroke(new BasicStroke(extractThickness(p.getPropertiesList())));
            // Create polygon shape to fill
            Path2D path = new Path2D.Double();
            // Initialize path with v0
            Vertex v0 = aMesh.getVertices(aMesh.getSegments(p.getSegmentIdxs(0)).getV1Idx());
            path.moveTo(getX(v0), getY(v0));
            // Create shape to draw based on mesh regularity type
            for (int i = (isIrregular) ? 0 : 1; i < p.getSegmentIdxsCount(); i++) {
                Segment s = aMesh.getSegments(p.getSegmentIdxsList().get(i));
                Vertex v;
                if (i != p.getSegmentIdxsCount() - 1 || isIrregular)
                    v = aMesh.getVertices(s.getV2Idx());
                else
                    v = aMesh.getVertices(s.getV1Idx());
                path.lineTo(getX(v), getY(v));
            }
            path.closePath();
            canvas.fill(path);
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
