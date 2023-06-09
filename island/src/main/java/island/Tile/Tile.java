package island.Tile;

import island.biomes.Biome;

import java.awt.Color;

public class Tile {
    private Type type;
    private Color color;
    private double altitude;
    private double humidity;
    private Biome biomes;

    public Tile (Type type, Color color){
        this.type = type;
        this.color = color;
        this.altitude = 0;
        this.humidity = 0;
        this.biomes = null;
    }

    public Tile(Type type, Color color, double humidity) {
        this(type, color);
        this.humidity = humidity;
    }

    public void setBiomes(Biome biomes) {
        this.biomes = biomes;
    }
    public String getBiomes() {
        return biomes.toString();
    }

    public String getType(){
        return this.type.toString();
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

    public double getHumidity() {
        return this.humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

}
