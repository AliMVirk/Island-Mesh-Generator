import java.io.IOException;
import org.apache.commons.cli.ParseException;
import island.configuration.MeshConfiguration;
import java.util.Random;

public class Main {
    
    public static void main(String[] args) throws ParseException, IOException {
        
        MeshConfiguration meshConfig = new MeshConfiguration(args, new Random().nextInt(9999999));
        String seed = meshConfig.generateIsland();
        System.out.println("Seed used " + seed);

    }
}
