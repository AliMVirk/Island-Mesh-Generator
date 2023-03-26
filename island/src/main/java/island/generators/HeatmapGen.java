package island.generators;

import java.awt.Color;
import java.util.List;

import island.Tile.Tile;
import island.Tile.Type;
import island.Tiles.LandTile;

public class HeatmapGen {
    
    public List<Tile> transform(List<Tile> tiles, String category) {

        // Get minimum and maximum values for altitude, humidity, and moisture
        double minAltitude = Double.MAX_VALUE; double maxAltitude = 0;
        double minHumidity = Double.MAX_VALUE; double maxHumidity = 0;
        double minMoisture = Double.MAX_VALUE; double maxMoisture = 0;
        for (Tile t : tiles) {
            double altitude = t.getAltitude();
            double humidity = t.getHumidity();
            if (altitude < minAltitude)
                minAltitude = altitude;
            if (humidity < minHumidity)
                minHumidity = humidity;
            if (altitude > maxAltitude)
                maxAltitude = altitude;
            if (humidity > maxHumidity)
                maxHumidity = humidity;
            if (t.getType().equals(Type.LAND.toString())) {
                double moisture = ((LandTile) t).getMoisture();
                if (moisture < minMoisture)
                    minMoisture = moisture;
                if (moisture > maxMoisture)
                    maxMoisture = moisture;
            }
        }

        switch (category) {
            case "altitude":
                for (Tile t : tiles) {
                    if (!t.getType().equals(Type.OCEAN.toString()))
                        t.setColor(fetchColor(t.getAltitude(), minAltitude, maxAltitude));
                    else
                        t.setColor(Color.BLACK);
                }
                break;
            case "humidity":
                for (Tile t : tiles) {
                    if (!t.getType().equals(Type.OCEAN.toString()))
                        t.setColor(fetchColor(t.getHumidity(), minHumidity, maxHumidity));
                    else
                        t.setColor(Color.BLACK);
                }
                break;
            case "moisture":
                for (Tile t : tiles) {
                    if (t.getType().equals(Type.LAND.toString()))
                        t.setColor(fetchColor(((LandTile) t).getMoisture(), minMoisture, maxMoisture));
                    else
                        t.setColor(Color.BLACK);
                }
                break;
        }

        return tiles;
    }

    private Color fetchColor(double value, double min, double max) {
        double tenth = (max - min) / 10;
        int intensityLevel = (int) Math.ceil((max - value) / tenth);
        return new Color(intensityLevel * 18, intensityLevel * 18, 255);
    }

}
