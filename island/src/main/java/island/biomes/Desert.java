package island.biomes;

public enum Desert implements Biome{

    DESERT("desert"), SUBTROPICALDESERT("subtropical desert"), SAVANNA("savanna");
    private final String name;
    private Desert(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
