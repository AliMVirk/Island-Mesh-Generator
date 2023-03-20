package island.profiles.altitude;

import org.locationtech.jts.geom.Coordinate;

import java.util.List;

public class AltitudeData {

    private final List<Coordinate> coords;
    private final double maxAltitude;
    private final double steepnessFactor;

    public AltitudeData(List<Coordinate> coords, double maxAltitude, double steepnessFactor){
        this.maxAltitude = maxAltitude;
        this.steepnessFactor = steepnessFactor;
        this.coords = coords;
    }

    public List<Coordinate> getCoords() {
        return coords;
    }

    public double getMaxAltitude() {
        return maxAltitude;
    }

    public double getSteepnessFactor() {
        return steepnessFactor;
    }
}
