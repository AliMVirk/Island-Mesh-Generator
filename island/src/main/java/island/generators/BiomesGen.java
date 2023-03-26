package island.generators;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import island.Tile.Biomes;
import island.Tile.Tile;
import island.Tile.Type;

import java.awt.*;
import java.util.List;

public class BiomesGen {

    public List<Tile> transform(Structs.Mesh oMesh, List<Tile> tiles, double precip, double avgTemp) {

        // assign biomes based on humidity, precipitation, average temperature and altitude of the tile.
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            double humidity = precip > 0 ? tile.getHumidity() - 20 + precip*0.1: tile.getHumidity();
            double altitude = avgTemp > 0 ? tile.getAltitude() - 2*avgTemp : tile.getAltitude();
            altitude = avgTemp < 0 ? tile.getAltitude() + (-3) * avgTemp : altitude;
            altitude = altitude < 0 ? 0 : altitude;
            String type = tile.getType();

            boolean isNotWater = !(type.equals(Type.LAKE.toString()) || type.equals(Type.OCEAN.toString()));

            if (altitude > 80 && humidity >= 70) {
                tile.setBiomes(Biomes.ARCTIC);
                if (isNotWater)
                    tile.setColor(new Color(155, 188, 255));
            } else if (altitude > 80 && humidity > 40) {
                tile.setBiomes(Biomes.ARCTIC);
                if (isNotWater)
                    tile.setColor(new Color(218, 228, 255));
            } else if (altitude > 80 && humidity < 40 || altitude > 40 && altitude < 80 && humidity < 30) {
                tile.setBiomes(Biomes.TUNDRA);
                if (isNotWater)
                    tile.setColor(new Color(99,230,248));
            } else if (altitude > 20 && altitude < 80 && humidity >= 70) {
                tile.setBiomes(Biomes.RAINFOREST);
                if (isNotWater)
                    tile.setColor(new Color(21, 124, 49));
            } else if (altitude > 40 && altitude < 80 && humidity >= 30 || altitude > 20 && altitude < 40 && humidity >= 30) {
                tile.setBiomes(Biomes.FOREST);
                if (isNotWater)
                    tile.setColor(new Color(46,177,83));
            } else if (altitude > 20 && altitude < 40 && humidity < 30) {
                tile.setBiomes(Biomes.DESERT);
                if (isNotWater)
                    tile.setColor(new Color(252, 215, 85));
            } else if (altitude < 20 && humidity > 60) {
                boolean mangroves = false;
                for (int n : oMesh.getPolygons(i).getNeighborIdxsList()){
                    if (tiles.get(n).getType().equals(Type.LAKE.toString())){
                        mangroves = true;
                    }
                }
                if (mangroves) {
                    tile.setBiomes(Biomes.MANGROVES);
                    if (isNotWater)
                        tile.setColor(new Color(101, 133, 47));
                } else {
                    tile.setBiomes(Biomes.FOREST);
                    if (isNotWater)
                        tile.setColor(new Color(21, 124, 49));
                }

            }  else if (altitude < 20 && humidity < 60 && humidity >= 30) {
                tile.setBiomes(Biomes.TAIGA);
                if (isNotWater)
                    tile.setColor(new Color(5,102,33));
            } else if (altitude < 20 && humidity < 30 && humidity > 15) {
                tile.setBiomes(Biomes.PLAINS);
                if (isNotWater)
                    tile.setColor(new Color(225, 222, 139));
            } else {
                tile.setBiomes(Biomes.SUBTROPICALDESERT);
                if (isNotWater)
                    tile.setColor(new Color(253, 203, 0));
            }

        }

        return tiles;
    }

}
