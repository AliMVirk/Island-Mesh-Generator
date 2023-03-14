package island.Tiles;

import island.Tile.Tile;
import island.Tile.Type;

import java.awt.*;

public class LakeTile extends Tile {

    private final int humidity;

    public LakeTile(int humidity) {
        super(Type.LAKE, new Color(0, 0, 255));
        this.humidity = humidity;
    }

    public int getHumidity(){
        return this.humidity;
    }
}
