package island.configuration;

import java.io.IOException;

import org.apache.commons.cli.ParseException;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import island.TilesGen;

public class MeshConfiguration {
    
    private Configuration config;

    public MeshConfiguration(String[] args) throws ParseException {
        config = new Configuration(args);
    }

    public Mesh generateIsland() throws IOException {
        String mode = config.export("m");
        if (mode == null) mode = "lagoon";
        
        MeshFactory factory = new MeshFactory();
        Mesh originalMesh = factory.read(config.export("i")); // Read input mesh
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

        factory.write(myMeshBuilder.build(), config.export("o")); // Write to output mesh
        return myMeshBuilder.build();
    }

}
