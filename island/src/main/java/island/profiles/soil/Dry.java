package island.profiles.soil;

public class Dry implements SoilProfile {
    
    private double[] composition = new double[]{0.1, 0.1, 1};

    public double[] defineComposition() {
        return composition;
    }
    
}
