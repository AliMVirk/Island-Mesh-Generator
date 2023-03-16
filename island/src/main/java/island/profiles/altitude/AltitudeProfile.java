package island.profiles.altitude;

import org.locationtech.jts.geom.Coordinate;

import java.util.List;

public abstract interface AltitudeProfile {
    public List<Coordinate> build(double width, double height, int numOfMountains);
}
