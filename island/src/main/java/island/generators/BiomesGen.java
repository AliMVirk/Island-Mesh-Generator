package island.generators;

import island.Tile.Biomes;
import island.Tile.Tile;
import island.Tile.Type;

import java.awt.*;
import java.util.List;

public class BiomesGen {

    public List<Tile> transform(List<Tile> tiles) {

        // assign biomes based on humidity and altitude
        for (Tile tile : tiles) {
            double humidity = tile.getHumidity();
            double altitude = tile.getAltitude();
            String type = tile.getType();

            if (altitude > 80 && humidity > 70) {
                tile.setBiomes(Biomes.ARCTIC);
                if (type.equals(Type.LAKE.toString()))
                    tile.setColor(new Color(215, 239, 255));
                else
                    tile.setColor(new Color(207, 222, 255));
            } else if (altitude > 80 && humidity > 40) {
                tile.setBiomes(Biomes.ARCTIC);
                if (type.equals(Type.LAKE.toString()))
                    tile.setColor(new Color(173, 223, 255));
                else
                    tile.setColor(new Color(237, 241, 255));
            } else if (altitude > 80 && humidity > 0) {
                tile.setBiomes(Biomes.TUNDRA);
                if (type.equals(Type.LAKE.toString()))
                    tile.setColor(new Color(99,230,248));
                else
                    tile.setColor(new Color(255, 255, 255));
            } else if (altitude > 40 && humidity > 70) {
                tile.setBiomes(Biomes.RAINFOREST);
                if (!type.equals(Type.LAKE.toString()))
                    tile.setColor(new Color(8,249,54));
            } else if (altitude > 40 && humidity > 40) {
                tile.setBiomes(Biomes.FOREST);
                if (!type.equals(Type.LAKE.toString()))
                    tile.setColor(new Color(46,177,83));
            } else if (altitude > 40 && humidity > 0) {
                tile.setBiomes(Biomes.DESERT);
                if (!type.equals(Type.LAKE.toString()))
                    tile.setColor(new Color(250,219,8));
            } else if (altitude > 0 && humidity > 70) {
                tile.setBiomes(Biomes.MANGROVES);
                if (!type.equals(Type.LAKE.toString()))
                    tile.setColor(new Color(133,127,47));
            } else if (altitude > 0 && humidity > 40) {
                tile.setBiomes(Biomes.PLAINS);
                if (!type.equals(Type.LAKE.toString()))
                    tile.setColor(new Color(205,170,114));
            } else {
                tile.setBiomes(Biomes.SUBTROPICALDESERT);
                if (!type.equals(Type.LAKE.toString()))
                    tile.setColor(new Color(249,148,24));
            }

        }

        return tiles;
    }

}
