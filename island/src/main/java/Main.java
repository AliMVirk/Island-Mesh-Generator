import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import island.*;
import island.configuration.MeshConfiguration;

public class Main {
    
    public static void main(String[] args) throws ParseException, IOException {
        
        MeshConfiguration meshConfig = new MeshConfiguration(args);
        meshConfig.generateIsland();

    }
}
