package island.shapes;

import java.awt.geom.Path2D;
import java.awt.geom.Ellipse2D;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;

public class Shape {

    public Path2D build(List<Coordinate> coords) {
        Path2D boundary = new Path2D.Double();
        Coordinate c = coords.get(0);
        boundary.moveTo(c.x, c.y);
        for (int i = 1; i < coords.size(); i++) {
            c = coords.get(i);
            boundary.lineTo(c.x, c.y);
        }
        boundary.closePath();
        return boundary;
    }

    public Path2D build(Ellipse2D ellipse) {
        Path2D boundary = new Path2D.Double();
        boundary.append(ellipse, false);
        return boundary;
    }

}
