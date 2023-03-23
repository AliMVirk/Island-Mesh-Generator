package island.Tiles;

import java.awt.Color;

import island.Tile.Tile;
import island.Tile.Type;

public class LandTile extends Tile {
    
    private double moisture;
    private double vegetation;

    public LandTile() {
        super(Type.LAND, new Color(144, 137, 53));
        moisture = 0;
        vegetation = 0;
    }

    public double getMoisture() {
        return this.moisture;
    }

    public void setMoisture(double moisture) {
        this.moisture = moisture;
    }

    public double getVegetation() {
        return this.vegetation;
    }

    public void setVegetation(double vegetation) {
        this.vegetation = vegetation;
    }

}
