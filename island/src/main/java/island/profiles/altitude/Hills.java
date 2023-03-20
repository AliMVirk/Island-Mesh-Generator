package island.profiles.altitude;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.locationtech.jts.geom.Coordinate;

public class Hills implements AltitudeProfile {

    @Override
    public List<Coordinate> buildCoordinates(double width, double height, int numOfMountains) {
        List<Coordinate> coords = new ArrayList<>();
        Random rnd = new Random();
        for (int i = 0; i < numOfMountains; i++) {
            coords.add(new Coordinate(rnd.nextDouble() * width, rnd.nextDouble() * height));
        }
        return coords;
    }

    @Override
    public AltitudeData build(double width, double height, int numOfMountains, double maxAltitude, double steepnessFactor) {
        return new AltitudeData(buildCoordinates(width, height, numOfMountains), maxAltitude, steepnessFactor);
    }
}
