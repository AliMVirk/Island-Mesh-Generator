package island.configuration;

import java.awt.geom.Path2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.ParseException;
import org.locationtech.jts.geom.Coordinate;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import island.TilesGen;
import island.shapes.Ellipse;
import island.shapes.Rectangle;
import island.LandGen;

public class MeshConfiguration {
    
    private Configuration config;

    public MeshConfiguration(String[] args) throws ParseException {
        config = new Configuration(args);
    }

    public void generateIsland() throws IOException {
        String shape = config.export("shape");
        if (shape == null) shape = "polygon";
        String mode = config.export("mode");
        if (mode == null) mode = "";
        
        MeshFactory factory = new MeshFactory();
        Mesh originalMesh = factory.read(config.export("i")); // Read input mesh
        Mesh.Builder myMeshBuilder = Mesh.newBuilder();
        Path2D islandBoundary;

        int width = 0; int height = 0;
        for (Property p : originalMesh.getPropertiesList()) {
            if (p.getKey().equals("width"))
                width = Integer.parseInt(p.getValue());
            else if (p.getKey().equals("height"))
                height = Integer.parseInt(p.getValue());
        }

        List<Coordinate> coords = new ArrayList<>();
        Rectangle rect = new Rectangle();
        Ellipse ellipse = new Ellipse();
        switch (shape) {
            case "rectangle":
                coords = rect.generateRectangle(width / 4, height / 4, width / 2, height / 3);
                islandBoundary = rect.build(coords);
                break;
            case "square":
                coords = rect.generateRectangle(width / 4, height / 4, width / 2, width / 2);
                islandBoundary = rect.build(coords);
                break;
            case "ellipse":
                islandBoundary = ellipse.build(ellipse.generateEllipse(width / 2, height / 2, width / 1.5, height / 2));
                break;
            default: // case "circle"
                islandBoundary = ellipse.build(ellipse.generateEllipse(width / 3, height / 3, width / 3, height / 3));
                break;
        }
        LandGen lgn = new LandGen();
        myMeshBuilder = lgn.createLand(originalMesh, islandBoundary);

        factory.write(myMeshBuilder.build(), config.export("o")); // Write to output mesh
    }

}
