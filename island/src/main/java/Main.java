import java.io.IOException;
import org.apache.commons.cli.ParseException;
import island.configuration.MeshConfiguration;

public class Main {
    
    public static void main(String[] args) throws ParseException, IOException {
        
        MeshConfiguration meshConfig = new MeshConfiguration(args);
        meshConfig.generateIsland();

    }
}
