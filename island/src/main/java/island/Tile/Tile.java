package island.Tile;

import java.awt.*;

public class Tile {
    private final Type type;
    private final Color color;

    public Tile (Type type, Color color){
        this.type = type;
        this.color = color;
    }

    public String getType(){
        return this.type.toString();
    }

    public String getColor(){
        return Integer.toString(this.color.getRGB());
    }

}
