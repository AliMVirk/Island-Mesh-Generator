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

public class Main {
    
    public static void main(String[] args) throws ParseException, IOException {
        
        Options options = new Options();
        options.addOption("i", true, "mesh input");
        options.addOption("o", true, "mesh output");
        options.addOption("m", "mode", true, "island generation mode");
        options.addOption("h", "help", false, "print this message");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("h") || !cmd.hasOption("i") || !cmd.hasOption("o")) {
            System.out.println("usage: island -i <file path> -o <file path>\n-h,--help           print this message\n-i <file path>      mesh input\n-m,--mode <arg>     island generation mode\n-o <file path>      mesh output");
            return;
        }
        String mode = cmd.getOptionValue("m");
        if (mode == null) mode = "lagoon";
        
        MeshFactory factory = new MeshFactory();
        Mesh originalMesh = factory.read(cmd.getOptionValue("i"));
        Mesh.Builder myMeshBuilder = Mesh.newBuilder();

        int width = 0; int height = 0;
        for (Property p : originalMesh.getPropertiesList()) {
            if (p.getKey().equals("width"))
                width = Integer.parseInt(p.getValue());
            else if (p.getKey().equals("height"))
                height = Integer.parseInt(p.getValue());
        }

        if (mode.equals("lagoon")) {
            TilesGen tgen = new TilesGen(width, height, width / 4, width / 2);
            myMeshBuilder = tgen.transform(originalMesh);
        }

        factory.write(myMeshBuilder.build(), cmd.getOptionValue("o"));

    }
}
