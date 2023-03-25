package island.Tile;

public enum Type {
    LAGOON("lagoon"), LAND("land"), OCEAN("ocean"), LAKE("lake"), BEACH("beach"), AQUIFER("aquifer");
    private String name;
    private Type(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
