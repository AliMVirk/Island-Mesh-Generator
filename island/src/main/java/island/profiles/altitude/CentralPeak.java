package island.profiles.altitude;
import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Coordinate;

public class CentralPeak implements AltitudeProfile {

    @Override
    public List<Coordinate> build(double width, double height, int numOfMountains) {
        double centre_x = width/2;
        double centre_y = height/2;
        List<Coordinate> coords = new ArrayList<>();
        coords.add(new Coordinate(centre_x, centre_y));
        return coords;
    }
}
