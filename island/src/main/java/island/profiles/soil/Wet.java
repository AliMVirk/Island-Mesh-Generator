package island.profiles.soil;

public class Wet implements SoilProfile {
    
    private double[] composition = new double[]{0.8, 0.8, 3};

    public double[] defineComposition() {
        return composition;
    }
    
}
