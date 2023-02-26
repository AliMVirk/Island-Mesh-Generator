import ca.mcmaster.cas.se2aa4.a2.generator.*;
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
        VoronoiGen vgen = new VoronoiGen();
        DelaunayTriangulationGen dgen = new DelaunayTriangulationGen();
        gen.generateVertices(myMeshBuilder);
        vgen.generate(myMeshBuilder);
        // Apply Lloyd relaxation
        for (int i = 0; i < 5; i++) {
            myMeshBuilder = gen.generateVertices(myMeshBuilder, vgen.getPolygons());
            vgen.generate(myMeshBuilder);
        }
        dgen.generate(myMeshBuilder);
        Mesh myMesh = myMeshBuilder.build();

        MeshFactory factory = new MeshFactory();
        factory.write(myMesh, args[0]);
    }

}
