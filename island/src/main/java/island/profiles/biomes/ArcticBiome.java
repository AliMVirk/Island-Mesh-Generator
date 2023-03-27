package island.profiles.biomes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import island.Tile.Tile;
import island.Tile.Type;
import island.biomes.Arctic;

import java.awt.*;
import java.util.List;

public class ArcticBiome {

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

            if (altitude > 40 && humidity > 40) {
                tile.setBiomes(Arctic.GLACIER);
                if (isNotWater)
                    tile.setColor(new Color(155, 188, 255));
            } else if (altitude > 40 && humidity > 5) {
                tile.setBiomes(Arctic.ALPINETUNDRA);
                if (isNotWater)
                    tile.setColor(new Color(153, 248, 215));
            }else if (altitude > 20 && humidity > 40) {
                tile.setBiomes(Arctic.BOREALFOREST);
                if (isNotWater)
                    tile.setColor(new Color(3, 91, 46));
            } else if (altitude > 10 && humidity > 10) {
                tile.setBiomes(Arctic.ARCTICTUNDRA);
                if (isNotWater)
                    tile.setColor(new Color(136, 199, 227));
            }  else {
                tile.setBiomes(Arctic.PLAINS);
                if (isNotWater)
                    tile.setColor(new Color(255, 255, 255));
            }
        }

        return tiles;

    }
}
