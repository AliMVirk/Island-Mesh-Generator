package island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

public class LagoonGen {
    
    private final int width;
    private final int height;
    private final int lagoonRadius;
    private final int landRadius;

    public LagoonGen(int x, int y, int innerRadius, int outerRadius) {
        width = x;
        height = y;
        lagoonRadius = innerRadius;
        landRadius = outerRadius;
    }

    public Mesh.Builder generate() {
        
    }

}
