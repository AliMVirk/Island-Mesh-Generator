package island.Tile;

public enum Biome {
    ARCTIC("arctic"), FOREST("forest"), FIELDS("fields"), TUNDRA("tundra"), MANGROVES("mangroves"), DESERT("desert");

    private String name;
    private Biome(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }

}
