package island.Tile;

import java.awt.Color;

public class River {
    
    private final Color color = new Color(1, 64, 98);
    private double discharge;
    private double humidity;

    public River(double dischargeLevel) {
        this.discharge = dischargeLevel;
        this.humidity = this.discharge * 10;
    }

    public String getColor(){
        return String.valueOf(this.color.getRed()) + "," + String.valueOf(this.color.getGreen()) + "," + String.valueOf(this.color.getBlue());
    }

    public double getHumidity(){
        return this.humidity;
    }

    public double getDischarge(){
        return this.discharge;
    }

    public void setDischarge(int val){
        this.discharge = val;
        this.humidity = this.discharge * 10;
    }

}
