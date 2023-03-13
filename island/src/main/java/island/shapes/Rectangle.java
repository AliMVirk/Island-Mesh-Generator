package island.shapes;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;

public class Rectangle extends Shape {
    
    public List<Coordinate> generateRectangle(double x, double y, double width, double height) {
        List<Coordinate> coords = new ArrayList<>();
        coords.add(new Coordinate(x, y));
        coords.add(new Coordinate(x + width, y));
        coords.add(new Coordinate(x + width, y + height));
        coords.add(new Coordinate(x, y + height));
        coords.add(new Coordinate(x, y));
        return coords;
    }

}
