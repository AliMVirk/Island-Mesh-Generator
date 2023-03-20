package island.profiles.altitude;

import org.locationtech.jts.geom.Coordinate;

import java.util.List;

public interface AltitudeProfile {

    List<Coordinate> buildCoordinates(double width, double height, int numOfMountains);

    AltitudeData build(double width, double height, int numOfMountains, double maxAltitude, double steepnessFactor);
}
