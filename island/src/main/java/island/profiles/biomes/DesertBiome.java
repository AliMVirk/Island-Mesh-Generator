package island.profiles.biomes;

import island.Tile.Tile;
import island.Tile.Type;
import island.biomes.Arctic;
import island.biomes.Desert;

import java.awt.*;
import java.util.List;

public class DesertBiome {
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

            if (altitude > 30 && humidity > 50) {
                tile.setBiomes(Desert.SAVANNA);
                if (isNotWater)
                    tile.setColor(new Color(213, 252, 100));
            } else if (altitude > 10 && humidity > 5) {
                tile.setBiomes(Desert.DESERT);
                if (isNotWater)
                    tile.setColor(new Color(243, 223, 117));
            }  else {
                tile.setBiomes(Desert.SUBTROPICALDESERT);
                if (isNotWater)
                    tile.setColor(new Color(243, 186, 80));
            }
        }

        return tiles;

    }
}
