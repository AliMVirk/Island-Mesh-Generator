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

    public void render(Mesh aMesh, Graphics2D canvas) {
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.5f);
        canvas.setStroke(stroke);
        // Draw vertices
        for (Vertex v: aMesh.getVerticesList()) {
            float thickness = extractThickness(v.getPropertiesList());
            double centre_x = v.getX() - (thickness/2.0d);
            double centre_y = v.getY() - (thickness/2.0d);
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
