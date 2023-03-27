package island.profiles.biomes;

import island.Tile.Tile;
import island.Tile.Type;
import island.biomes.Arctic;
import island.biomes.Temperate;
import island.biomes.Tropical;

import java.awt.*;
import java.util.List;

public class TemperateBiome {
    public List<Tile> transform(List<Tile> tiles) {
        // assign biomes based on humidity and altitude of the tile.
        // Do not change the color of water tiles
        for (Tile tile : tiles) {
            // Get type, altitude and humidity of tile
            double humidity = tile.getHumidity();
            double altitude = tile.getAltitude();
            String type = tile.getType();

            // True if the tile is not a water tile
            boolean isNotWater = !(type.equals(Type.LAKE.toString()) || type.equals(Type.OCEAN.toString()));

            if (altitude > 20 && humidity > 30) {
                tile.setBiomes(Temperate.FOREST);
                if (isNotWater)
                    tile.setColor(new Color(11, 182, 73));
            }  else if (altitude > 5 && humidity < 50) {
                tile.setBiomes(Temperate.GRASSLAND);
                if (isNotWater)
                    tile.setColor(new Color(148, 227, 111));
            }  else if (altitude > 5 && humidity > 70) {
                tile.setBiomes(Temperate.CHAPARRAL);
                if (isNotWater)
                    tile.setColor(new Color(231, 173, 37));
            }else {
                tile.setBiomes(Arctic.PLAINS);
                if (isNotWater)
                    tile.setColor(new Color(236, 232, 148));
            }
        }

        return tiles;

    }
}
