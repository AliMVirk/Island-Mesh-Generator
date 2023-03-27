package island.generators;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import island.biomes.Biome;
import island.Tile.Tile;
import island.biomes.Biomes;
import island.biomes.Temperate;
import island.profiles.biomes.ArcticBiome;
import island.profiles.biomes.DesertBiome;
import island.profiles.biomes.TemperateBiome;
import island.profiles.biomes.TropicalBiome;

import java.util.List;

public class BiomesGen {

    public List<Tile> transform(Structs.Mesh oMesh, List<Tile> tiles, String biomesProfile) {

        if (biomesProfile.equals(Biomes.ARCTIC.toString())){
            ArcticBiome aBiome = new ArcticBiome();
            tiles = aBiome.transform(tiles);
        } else if (biomesProfile.equals(Biomes.TROPICAL.toString())) {
            TropicalBiome tBiome = new TropicalBiome();
            tiles = tBiome.transform(tiles);
        } else if (biomesProfile.equals(Biomes.DESERT.toString())){
            DesertBiome dBiome = new DesertBiome();
            tiles = dBiome.transform(tiles);
        } else if (biomesProfile.equals(Biomes.TEMPERATE.toString())){
            TemperateBiome tBiome = new TemperateBiome();
            tiles = tBiome.transform(tiles);
        }

        return tiles;
    }

}
