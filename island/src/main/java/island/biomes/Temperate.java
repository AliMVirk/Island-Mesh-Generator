package island.biomes;

public enum Temperate implements Biome{

    FOREST("forest"), GRASSLAND("grass land"), CHAPARRAL("chaparral"), PLAINS("plains");
    private final String name;
    private Temperate(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
