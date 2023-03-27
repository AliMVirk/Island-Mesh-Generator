package island.biomes;

public enum Arctic implements Biome {
    PLAINS("plains"), GLACIER("glacier"), TAIGA("taiga"), ARCTICTUNDRA("arctic tundra"), ALPINETUNDRA("alpine tundra"), BOREALFOREST("boreal forest");

    private final String name;
    private Arctic(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
