package island.biomes;

public enum Biomes implements Biome {

    ARCTIC("arctic"), TROPICAL("tropical"), DESERT("desert"), TEMPERATE("temperate");
    private final String name;
    private Biomes(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
