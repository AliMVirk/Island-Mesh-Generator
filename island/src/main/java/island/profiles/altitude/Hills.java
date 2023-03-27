package island.profiles.altitude;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.locationtech.jts.geom.Coordinate;

public class Hills {

    public List<Coordinate> buildCoordinates(double width, double height, int numOfMountains, Random rnd) {
        List<Coordinate> coords = new ArrayList<>();
        for (int i = 0; i < numOfMountains; i++) {
            coords.add(new Coordinate(rnd.nextDouble() * width, rnd.nextDouble() * height));
        }
        return coords;
    }

    public AltitudeData build(double width, double height, int numOfMountains, double maxAltitude, double steepnessFactor, Random rnd) {
        return new AltitudeData(buildCoordinates(width, height, numOfMountains, rnd), maxAltitude, steepnessFactor);
    }
}
