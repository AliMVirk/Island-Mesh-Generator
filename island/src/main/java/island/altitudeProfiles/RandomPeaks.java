package island.altitudeProfiles;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.locationtech.jts.geom.Coordinate;

public class RandomPeaks implements altitudeProfile {

    @Override
    public List<Coordinate> build(double width, double height, int numOfMountains) {
        List<Coordinate> coords = new ArrayList<>();
        Random rnd = new Random();
        for (int i = 0; i < numOfMountains; i++) {
            coords.add(new Coordinate(rnd.nextDouble() * width, rnd.nextDouble() * height));
        }
        return coords;
    }
}
