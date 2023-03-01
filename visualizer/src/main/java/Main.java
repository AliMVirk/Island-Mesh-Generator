import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.visualizer.GraphicRenderer;
import ca.mcmaster.cas.se2aa4.a2.visualizer.MeshDump;
import ca.mcmaster.cas.se2aa4.a2.visualizer.SVGCanvas;

import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        // Extracting command line parameters
        String input = args[0];
        String output = args[1];
        // Debug logic
        boolean debugToggle = args.length >= 3 && args[2].equals("-X");
        // Getting width and height for the canvas
        Structs.Mesh aMesh = new MeshFactory().read(input);
        int width = 0; int height = 0;
        for (Property p : aMesh.getPropertiesList()) {
            if (p.getKey().equals("width"))
                width = Integer.parseInt(p.getValue());
            else if (p.getKey().equals("height"))
                height = Integer.parseInt(p.getValue());
        }
        // Creating the Canvas to draw the mesh
        Graphics2D canvas = SVGCanvas.build(width, height);
        GraphicRenderer renderer = new GraphicRenderer();
        // Painting the mesh on the canvas
        renderer.render(aMesh, canvas, debugToggle);
        // Storing the result in an SVG file
        SVGCanvas.write(canvas, output);
        // Dump the mesh to stdout
        MeshDump dumper = new MeshDump();
        dumper.dump(aMesh);
    }
}
