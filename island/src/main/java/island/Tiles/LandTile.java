package island.Tiles;

import java.awt.Color;

import island.Tile.Tile;
import island.Tile.Type;

public class LandTile extends Tile {
    
    private double moisture;
    private double vegetation;

    public LandTile() {
        super(Type.LAND, new Color(252, 244, 137));
        this.moisture = 0;
        this.vegetation = 0;
    }

    public LandTile(double humidity) {
        super(Type.AQUIFER, new Color(144, 137, 53), humidity);
        this.moisture = 0;
        this.vegetation = 0;
    }

    public double getMoisture() {
        return this.moisture;
    }

    public void setMoisture(double moisture) {
        this.moisture = moisture;
        this.vegetation = moisture;
        adjustVegetation();
    }

    public double getVegetation() {
        return this.vegetation;
    }

    private void adjustVegetation() {
        if (this.vegetation > 80)
            this.setColor(new Color(65, 124, 43));
        else if (this.vegetation > 60)
            this.setColor(new Color(97, 178, 62));
        else if (this.vegetation > 30)
            this.setColor(new Color(143, 175, 33));
        else if (this.vegetation > 5)
            this.setColor(new Color(144, 137, 53));
        else
            this.setColor(new Color(252, 244, 137));
    }

}
