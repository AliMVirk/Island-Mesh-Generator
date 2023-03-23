package island.configuration;

import java.awt.geom.Path2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import island.profiles.altitude.AltitudeData;
import org.apache.commons.cli.ParseException;
import org.locationtech.jts.geom.Coordinate;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import island.Tile.River;
import island.Tile.Tile;
import island.profiles.altitude.Volcano;
import island.profiles.altitude.CornerMountains;
import island.profiles.altitude.Hills;
import island.shapes.Ellipse;
import island.shapes.Rectangle;
import island.generators.AltitudeGen;
import island.generators.LandGen;
import island.generators.RiverGen;
import island.generators.LagoonGen;
import island.generators.LakeGen;

public class MeshConfiguration {

    private Configuration config;

    public MeshConfiguration(String[] args) throws ParseException {
        config = new Configuration(args);
    }

    public void generateIsland() throws IOException {
        // Fetch command line arguments
        String shape = config.export("shape");
        if (shape == null) shape = "polygon";
        String mode = config.export("mode");
        if (mode == null) mode = "";
        String altProfile = config.export("altitude");
        if (altProfile == null) altProfile = "";
        String numLakes = config.export("lakes");
        if (numLakes == null) numLakes = "5";
        String numRivers = config.export("rivers");
        if (numRivers == null) numRivers = "5";

        MeshFactory factory = new MeshFactory();
        Mesh originalMesh = factory.read(config.export("i")); // Read input mesh
        Path2D islandBoundary;
        List<Tile> tiles = new ArrayList<>();

        int width = 0; int height = 0;
        for (Property p : originalMesh.getPropertiesList()) {
            if (p.getKey().equals("width"))
                width = Integer.parseInt(p.getValue());
            else if (p.getKey().equals("height"))
                height = Integer.parseInt(p.getValue());
        }

        // Configure island shape
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
                islandBoundary = ellipse.build(ellipse.generateEllipse(width / 2, height / 2, width / 3, height / 3));
                break;
        }
        coords.clear();
        LandGen lgn = new LandGen();
        tiles = lgn.createLand(originalMesh, islandBoundary);

        // Configure altitude
        Volcano volcano = new Volcano();
        CornerMountains valley = new CornerMountains();
        Hills hills = new Hills();

        AltitudeData altitudeData;

        switch (altProfile) {
            case "volcano":
                altitudeData = volcano.build(width, height, 1, 100, 0.8);
                break;
            case "Hills":
                altitudeData = valley.build(width, height, 4, 70, 0.9);
                break;
            default: // random hills
                altitudeData = hills.build(width, height, 10, 50, 0.5);
                break;
        }
        AltitudeGen agen = new AltitudeGen();
        tiles = agen.transform(originalMesh, tiles, altitudeData);

        // Create lakes
        LakeGen lgen = new LakeGen();
        tiles = lgen.transform(originalMesh, tiles, Integer.parseInt(numLakes));

        // Create rivers
        RiverGen rgen = new RiverGen();
        River[] rivers = rgen.createRivers(originalMesh, tiles, Integer.parseInt(numRivers));

        // Turn tiles into polygon properties
        Mesh islandMesh = mutateMesh(originalMesh, tiles, rivers);

        // Create lagoon island if specified
        if (mode.equals("lagoon")) {
            LagoonGen lgngen = new LagoonGen(width, height, width / 5, width / 3);
            islandMesh = lgngen.transform(originalMesh).build();
        }

        factory.write(islandMesh, config.export("o")); // Write to output mesh
    }

    private Mesh mutateMesh(Mesh oMesh, List<Tile> tiles, River[] rivers) {
        // Extract mesh
        Mesh.Builder mesh = Mesh.newBuilder();
        mesh.addAllVertices(oMesh.getVerticesList()).addAllProperties(oMesh.getPropertiesList());

        // Extract polygon properties from tile attributes
        for (int i = 0; i < oMesh.getPolygonsCount(); i++) {
            Polygon oPoly = oMesh.getPolygons(i);
            // Duplicate polygon from original mesh
            Polygon.Builder p = Polygon.newBuilder().addAllNeighborIdxs(oPoly.getNeighborIdxsList()).addAllSegmentIdxs(oPoly.getSegmentIdxsList()).addAllProperties(oPoly.getPropertiesList()).setCentroidIdx(oPoly.getCentroidIdx());
            // Set tile properties for corresponding polygon
            Property tileType = Property.newBuilder().setKey("tile_type").setValue(tiles.get(i).getType()).build();
            Property tileColor = Property.newBuilder().setKey("tile_color").setValue(tiles.get(i).getColor()).build();
            Property tileAltitude = Property.newBuilder().setKey("tile_altitude").setValue(Double.toString(tiles.get(i).getAltitude())).build();
            p.addProperties(tileType).addProperties(tileColor).addProperties(tileAltitude);
            mesh.addPolygons(p.build());
        }

        // Extract segment properties from river attributes
        for (int i = 0; i < oMesh.getSegmentsCount(); i++) {
            Segment oSegment = oMesh.getSegments(i);
            // Duplicate segment from original mesh
            Segment.Builder s = Segment.newBuilder().setV1Idx(oSegment.getV1Idx()).setV2Idx(oSegment.getV2Idx());
            if (rivers[i] == null) {
                s.addAllProperties(oSegment.getPropertiesList()); // Non-river segment
            } else {
                // Set river properties for corresponding segment
                Property color = Property.newBuilder().setKey("rgb_color").setValue(rivers[i].getColor()).build();
                Property thickness = Property.newBuilder().setKey("thickness").setValue(String.valueOf(rivers[i].getDischarge())).build();
                s.addProperties(color).addProperties(thickness);
            }
            mesh.addSegments(s);
        }

        return mesh.build();
    }

}
