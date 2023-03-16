package island.altitudeProfiles;

import org.locationtech.jts.geom.Coordinate;

import java.util.List;

public abstract interface altitudeProfile {
    public List<Coordinate> build(double width, double height, int numOfMountains);
}
