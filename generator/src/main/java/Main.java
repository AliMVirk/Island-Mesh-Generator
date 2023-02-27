import ca.mcmaster.cas.se2aa4.a2.generator.*;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {

        Options options = new Options();
        options.addOption("h", "help", false, "print this message");
        options.addOption("t", true, "type of mesh");
        options.addOption("w", true, "canvas width");
        options.addOption("d", true, "canvas height");
        options.addOption("s", true, "square size");
        options.addOption("n", true, "number of polygons");
        options.addOption("r", true, "relaxation level");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        
        if (cmd.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("generator", options);
            return;
        }
        String meshType = cmd.getOptionValue("t");
        String canvasWidth = cmd.getOptionValue("w");
        String canvasHeight = cmd.getOptionValue("d");
        String squareSize = cmd.getOptionValue("s");
        String relaxationLevel = cmd.getOptionValue("r");
        String polygonCount = cmd.getOptionValue("n");
        boolean irregular = false;
        int width = 500;
        int height = 500;
        int size = 20;
        int numRelaxations = 5;
        int numPolygons = 150;

        if (meshType != null && (meshType.toLowerCase().equals("irregular") || meshType.toLowerCase().equals("i")))
            irregular = true;
        if (canvasWidth != null)
            width = Integer.parseInt(canvasWidth);
        if (canvasHeight != null)
            height = Integer.parseInt(canvasHeight);
        if (squareSize != null)
            size = Integer.parseInt(squareSize);
        if (relaxationLevel != null)
            numRelaxations = Integer.parseInt(relaxationLevel);
        if (polygonCount != null)
            numPolygons = Integer.parseInt(polygonCount);

        Mesh.Builder myMeshBuilder = Mesh.newBuilder();
        if (irregular) {
            CentroidGen gen = new CentroidGen(numPolygons, width, height);
            VoronoiGen vgen = new VoronoiGen();
            DelaunayTriangulationGen dgen = new DelaunayTriangulationGen();
            myMeshBuilder = gen.generateVertices(myMeshBuilder);
            myMeshBuilder = vgen.generate(myMeshBuilder); // Compute Voronoi diagram
            // Apply Lloyd relaxation
            for (int i = 0; i < numRelaxations; i++) {
                myMeshBuilder = gen.generateVertices(myMeshBuilder, vgen.getPolygons());
                vgen.generate(myMeshBuilder);
            }
            dgen.generate(myMeshBuilder);
        } else {
            DotGen dotGenerator = new DotGen(width, height, size);
            SegmentGen segmentGenerator = new SegmentGen(width, height, size);
            PolygonGen polygonGenerator = new PolygonGen(width, height, size);
            myMeshBuilder = dotGenerator.generateVertices(myMeshBuilder);
            myMeshBuilder = segmentGenerator.generateSegments(myMeshBuilder);
            myMeshBuilder = polygonGenerator.generatePolygons(myMeshBuilder);
        }
        
        Property meshIrregularity = Property.newBuilder().setKey("mesh_type").setValue(String.valueOf(irregular)).build();
        myMeshBuilder.addProperties(meshIrregularity);

        Mesh myMesh = myMeshBuilder.build();

        MeshFactory factory = new MeshFactory();
        factory.write(myMesh, args[0]);
    }

}
