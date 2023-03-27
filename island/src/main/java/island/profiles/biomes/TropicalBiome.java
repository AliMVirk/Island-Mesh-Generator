package island.profiles.biomes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import island.Tile.Tile;
import island.Tile.Type;
import island.biomes.Arctic;
import island.biomes.Tropical;

import java.awt.*;
import java.util.List;

public class TropicalBiome {
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

            if (altitude > 10 && humidity > 20) {
                tile.setBiomes(Tropical.RAINFOREST);
                if (isNotWater)
                    tile.setColor(new Color(7, 101, 40));
            } else if (altitude > 10 && humidity > 5) {
                tile.setBiomes(Tropical.FOREST);
                if (isNotWater)
                    tile.setColor(new Color(14, 211, 107));
            } else if (altitude > 10 && humidity < 5) {
                tile.setBiomes(Tropical.SAVANNA);
                if (isNotWater)
                    tile.setColor(new Color(231, 218, 155));
            } else if (altitude > 5 && humidity > 10) {
                tile.setBiomes(Tropical.WETLAND);
                if (isNotWater)
                    tile.setColor(new Color(60, 80, 60));
            }else if (altitude > 5 && humidity < 10) {
                tile.setBiomes(Tropical.MANGROVES);
                if (isNotWater)
                    tile.setColor(new Color(110, 114, 6));
            } else {
                tile.setBiomes(Tropical.SAVANNA);
                if (isNotWater)
                    tile.setColor(new Color(231, 218, 155));
            }
        }

        return tiles;

    }
}
