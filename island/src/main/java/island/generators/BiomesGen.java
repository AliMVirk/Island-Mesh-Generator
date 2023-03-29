package island.generators;

import island.Tile.Tile;
import island.biomes.Biomes;
import island.profiles.biomes.ArcticBiome;
import island.profiles.biomes.DesertBiome;
import island.profiles.biomes.TemperateBiome;
import island.profiles.biomes.TropicalBiome;

import java.util.List;

public class BiomesGen {

    public List<Tile> transform(List<Tile> tiles, String biomesProfile) {

        // Select a biome and create its sub-biomes
        if (biomesProfile.equals(Biomes.ARCTIC.toString()) || biomesProfile.equals("1")){
            ArcticBiome aBiome = new ArcticBiome();
            tiles = aBiome.transform(tiles);
        } else if (biomesProfile.equals(Biomes.TROPICAL.toString()) || biomesProfile.equals("2")) {
            TropicalBiome tBiome = new TropicalBiome();
            tiles = tBiome.transform(tiles);
        } else if (biomesProfile.equals(Biomes.DESERT.toString()) || biomesProfile.equals("3")){
            DesertBiome dBiome = new DesertBiome();
            tiles = dBiome.transform(tiles);
        } else if (biomesProfile.equals(Biomes.TEMPERATE.toString()) || biomesProfile.equals("4")){
            TemperateBiome tBiome = new TemperateBiome();
            tiles = tBiome.transform(tiles);
        }

        return tiles;
    }

}
