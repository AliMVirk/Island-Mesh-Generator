package island.Tile;

public enum Biomes {
    ARCTIC("arctic"), RAINFOREST("rainForest"), FOREST("forest"), PLAINS("plains"), TAIGA("taiga"), TUNDRA("tundra"), MANGROVES("mangroves"), DESERT("desert"), SUBTROPICALDESERT("subtropicalDesert");

    private String name;
    private Biomes(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }

}
