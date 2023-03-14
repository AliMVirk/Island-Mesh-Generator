package island.shapes;

import java.awt.geom.Ellipse2D;

public class Ellipse extends Shape {

    public Ellipse2D generateEllipse(double centre_x, double centre_y, double width, double height) {
        Ellipse2D ellipse = new Ellipse2D.Double(centre_x - width / 2, centre_y - height / 2, width, height);
        return ellipse;
    }

}
