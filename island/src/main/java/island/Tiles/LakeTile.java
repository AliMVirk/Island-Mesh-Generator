package island.Tiles;

import island.Tile.Tile;
import island.Tile.Type;

import java.awt.*;

public class LakeTile extends Tile {

    private final int humidity;

    public LakeTile(Type type, Color color, int humidity) {
        super(type, color);
        this.humidity = humidity;
    }

    public int getHumidity(){
        return this.humidity;
    }
}
