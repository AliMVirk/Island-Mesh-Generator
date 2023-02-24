import ca.mcmaster.cas.se2aa4.a2.generator.CentroidGen;
import ca.mcmaster.cas.se2aa4.a2.generator.DotGen;
import ca.mcmaster.cas.se2aa4.a2.generator.PolygonGen;
import ca.mcmaster.cas.se2aa4.a2.generator.SegmentGen;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        DotGen dotGenerator = new DotGen();
        SegmentGen segmentGenerator = new SegmentGen();
        PolygonGen polygonGenerator = new PolygonGen();
        Mesh.Builder myMeshBuilder = Mesh.newBuilder();
        /*myMeshBuilder = dotGenerator.generateVertices(myMeshBuilder);
        myMeshBuilder = segmentGenerator.generateSegments(myMeshBuilder);
        myMeshBuilder = polygonGenerator.generatePolygons(myMeshBuilder);*/
        CentroidGen gen = new CentroidGen();
        gen.generateVertices(myMeshBuilder);
        Mesh myMesh = myMeshBuilder.build();

        MeshFactory factory = new MeshFactory();
        factory.write(myMesh, args[0]);
    }

}
