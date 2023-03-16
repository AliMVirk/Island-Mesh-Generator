package island.altitudeProfiles;
import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Coordinate;

public class CornerPeaks implements altitudeProfile {

    @Override
    public List<Coordinate> build(double width, double height, int numOfMountains) {
        List<Coordinate> coords = new ArrayList<>();
        coords.add(new Coordinate(0, 0));
        coords.add(new Coordinate(width, 0));
        coords.add(new Coordinate(width, height));
        coords.add(new Coordinate(0, height));
        return coords;
    }
}
