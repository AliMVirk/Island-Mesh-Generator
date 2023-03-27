package island.biomes;

public enum Tropical implements Biome {
    MANGROVES("mangroves"), RAINFOREST("rain forest"), FOREST("forest"), WETLAND("wet land"), SAVANNA("savanna");
    private final String name;
    private Tropical(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
