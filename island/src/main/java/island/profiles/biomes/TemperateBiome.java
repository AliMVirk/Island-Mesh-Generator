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
        for (Tile tile : tiles) {
            double humidity = tile.getHumidity();
            double altitude = tile.getAltitude();
            String type = tile.getType();

            boolean isNotWater = !(type.equals(Type.LAKE.toString()) || type.equals(Type.OCEAN.toString()));

            if (altitude > 10 && humidity > 10) {
                tile.setBiomes(Temperate.FOREST);
                if (isNotWater)
                    tile.setColor(new Color(11, 182, 73));
            } else if (altitude > 10 && humidity > 5) {
                tile.setBiomes(Temperate.CHAPARRAL);
                if (isNotWater)
                    tile.setColor(new Color(231, 173, 37));
            } else if (altitude > 10 && humidity < 5) {
                tile.setBiomes(Temperate.GRASSLAND);
                if (isNotWater)
                    tile.setColor(new Color(148, 227, 111));
            }  else {
                tile.setBiomes(Arctic.PLAINS);
                if (isNotWater)
                    tile.setColor(new Color(236, 232, 148));
            }
        }

        return tiles;

    }
}
