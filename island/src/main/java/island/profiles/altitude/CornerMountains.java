package island.profiles.altitude;
import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Coordinate;

public class CornerMountains implements AltitudeProfile {

    @Override
    public List<Coordinate> buildCoordinates(double width, double height, int numOfMountains) {
        List<Coordinate> coords = new ArrayList<>();
        coords.add(new Coordinate(0, 0));
        coords.add(new Coordinate(width, 0));
        coords.add(new Coordinate(width, height));
        coords.add(new Coordinate(0, height));
        return coords;
    }

    @Override
    public AltitudeData build(double width, double height, int numOfMountains, double maxAltitude, double steepnessFactor) {
        return new AltitudeData(buildCoordinates(width, height, numOfMountains), maxAltitude, steepnessFactor);
    }
}
