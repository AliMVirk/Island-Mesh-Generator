package island.shapes;

import java.awt.geom.Ellipse2D;

public class Ellipse extends Shape {

    public Ellipse2D generateEllipse(double centre_x, double centre_y, double radiusA, double radiusB) {
        Ellipse2D ellipse = new Ellipse2D.Double(centre_x, centre_y, radiusA, radiusB);
        return ellipse;
    }

}
