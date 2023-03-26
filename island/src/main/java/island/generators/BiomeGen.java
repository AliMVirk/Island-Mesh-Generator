package island.generators;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import island.Tile.Biome;
import island.Tile.Tile;
import island.Tile.Type;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class BiomeGen {

    public List<Tile> transform(Structs.Mesh oMesh, List<Tile> tiles) {

        for(int i = 0; i < tiles.size(); i++){
            Tile tile = tiles.get(i);
            double humidity = tile.getHumidity();
            double altitude = tile.getAltitude();
            String type = tile.getType();

            if (altitude > 80 && humidity > 70){
                tile.setBiome(Biome.ARCTIC);
                if (type.equals(Type.LAKE.toString()))
                    tile.setColor(new Color(215, 239, 255));
                else
                    tile.setColor(new Color(207, 222, 255));
            }
            else if (altitude > 80 && humidity > 40){
                tile.setBiome(Biome.ARCTIC);
                if (type.equals(Type.LAKE.toString()))
                    tile.setColor(new Color(215, 239, 255));
                else
                    tile.setColor(new Color(237, 241, 255));
            }
            else if (altitude > 80 && humidity > 0){
                tile.setBiome(Biome.ARCTIC);
                if (type.equals(Type.LAKE.toString()))
                    tile.setColor(new Color(215, 239, 255));
                else
                    tile.setColor(new Color(255, 255, 255));
            }
            else if (altitude > 40 && humidity > 70){
                tile.setBiome(Biome.FOREST);
                if (!type.equals(Type.LAKE.toString()))
                    tile.setColor(new Color(6, 122, 63));
            }
            else if (altitude > 40 && humidity > 40){
                tile.setBiome(Biome.FOREST);
                if (!type.equals(Type.LAKE.toString()))
                    tile.setColor(new Color(15, 175, 91));
            }
            else if (altitude > 40 && humidity > 0){
                tile.setBiome(Biome.DESERT);
                if (!type.equals(Type.LAKE.toString()))
                    tile.setColor(new Color(250, 240, 138));
            }
            else if (altitude > 0 && humidity > 70){
                tile.setBiome(Biome.MANGROVES);
                if (!type.equals(Type.LAKE.toString()))
                    tile.setColor(new Color(168, 167, 13));
            }
            else if (altitude > 20 && humidity > 40){
                tile.setBiome(Biome.PLAINS);
                if (!type.equals(Type.LAKE.toString()))
                    tile.setColor(new Color(184, 241, 170));
            }
            else if (altitude > 0 && humidity > 0){
                tile.setBiome(Biome.DESERT);
                if (!type.equals(Type.LAKE.toString()))
                    tile.setColor(new Color(250, 240, 138));
            }

        }

        return tiles;
    }

}
