package island.Tile;

import java.awt.*;

public class Tile {
    private Type type;
    private Color color;
    private double altitude;

    public Tile (Type type, Color color){
        this.type = type;
        this.color = color;
        this.altitude = 0;
    }

    public String getType(){
        return this.type.toString();
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getColor(){
        return Integer.toString(this.color.getRGB());
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getAltitude(){
        return this.altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

}
