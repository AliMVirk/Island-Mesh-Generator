import ca.mcmaster.cas.se2aa4.a2.generator.*;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

import java.io.IOException;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.locationtech.jts.geom.Polygon;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {

        Options options = new Options();
        // Add command line options
        options.addOption("h", "help", false, "print this message");
        options.addOption("t", true, "type of mesh");
        options.addOption("w", true, "canvas width");
        options.addOption("d", true, "canvas height");
        options.addOption("s", true, "square size");
        options.addOption("n", true, "number of polygons");
        options.addOption("r", true, "relaxation level");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        
        // -h output
        if (cmd.hasOption("h")) {
            System.out.println("usage: generator <mesh path>\n-d <height>    canvas height (integer value)\n-h,--help      print this message\n-n <amount>    number of polygons (integer value)\n-r <level>     relaxation level (integer value)\n-s <size>      square size (integer value)\n-t <type>      type of mesh: '-t i' or '-t irregular' for irregular mesh, regular mesh is the default\n-w <width>     canvas width (integer value)");
            return;
        }
        // Store appropriate command line arguments
        String meshType = cmd.getOptionValue("t");
        String canvasWidth = cmd.getOptionValue("w");
        String canvasHeight = cmd.getOptionValue("d");
        String squareSize = cmd.getOptionValue("s");
        String relaxationLevel = cmd.getOptionValue("r");
        String polygonCount = cmd.getOptionValue("n");
        // Default options
        boolean irregular = false;
        int width = 500;
        int height = 500;
        int size = 20;
        int numRelaxations = 5;
        int numPolygons = 150;

        // User options
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
        myMeshBuilder.addProperties(Property.newBuilder().setKey("width").setValue(String.valueOf(width)).build());
        myMeshBuilder.addProperties(Property.newBuilder().setKey("height").setValue(String.valueOf(height)).build());

        if (irregular) {
            // Irregular mesh generation
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
            List<Polygon> trianglesProduced = dgen.generate(myMeshBuilder);
            IrregularNeighborGen ngen = new IrregularNeighborGen();
            ngen.generate(myMeshBuilder, trianglesProduced);
        } else {
            // Grid mesh generation
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
