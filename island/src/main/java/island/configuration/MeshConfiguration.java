package island.configuration;

import java.awt.geom.Path2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import island.generators.*;
import island.profiles.altitude.AltitudeData;
import org.apache.commons.cli.ParseException;
import org.locationtech.jts.geom.Coordinate;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import island.Tile.River;
import island.Tile.Tile;
import island.profiles.altitude.*;
import island.profiles.soil.*;
import island.shapes.*;
import pathfinder.graph.Graph;
import pathfinder.graph.Node;

public class MeshConfiguration {

    private final Configuration config;
    private final Random rnd;
    private String genSeed;

    public MeshConfiguration(String[] args, long seed) throws ParseException {
        config = new Configuration(args);
        rnd = new Random(seed);
        genSeed = String.valueOf(seed);
    }

    public String generateIsland() throws IOException {
        // Fetch command line arguments
        String shape = config.export("shape");
        if (shape == null) shape = "circle";
        String mode = config.export("mode");
        if (mode == null) mode = "";
        String altProfile = config.export("altitude");
        if (altProfile == null) altProfile = "";
        String numLakes = config.export("lakes");
        if (numLakes == null) numLakes = "5";
        if (Integer.parseInt(numLakes) > 999) numLakes = "999";
        String numRivers = config.export("rivers");
        if (numRivers == null) numRivers = "5";
        if (Integer.parseInt(numRivers) > 999) numRivers = "999";
        String numAquifers = config.export("aquifers");
        if (numAquifers == null) numAquifers = "5";
        if (Integer.parseInt(numAquifers) > 999) numAquifers = "999";
        String soilProfile = config.export("soil");
        if (soilProfile == null) soilProfile = "wet";
        String biomesProfile = config.export("biomes");
        if (biomesProfile == null) biomesProfile = "";
        String heatmapView = config.export("heatmap");
        if (heatmapView == null) heatmapView = "";
        String numCities = config.export("cities");
        if (numCities == null) numCities = "10";
        String seed = config.export("seed");
        boolean random = true; // true if seed was not provided
        String configSeed = ""; // seed for configurability options if seed is not provided and only rng seed is generated
        if (seed != null) {
            // seed is of the format 0 0 0 000 000 000 0 0 000 0...0
            // 0:mode 0:shape 0:altitude 000:lakes 000:rivers 000:aquifers 0:soil 0:biome 000:cities 0...0:rng seed
            rnd.setSeed(Long.parseLong(seed.substring(17)));
            genSeed = seed;
            random = false;
        }

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
        if (random) {
            switch (shape) {
            case "rectangle":
                coords = rect.generateRectangle(width / 4, height / 4, width / 2, height / 3);
                islandBoundary = rect.build(coords);
                configSeed += "1";
                break;
            case "square":
                coords = rect.generateRectangle(width / 4, height / 4, width / 2, width / 2);
                islandBoundary = rect.build(coords);
                configSeed += "2";
                break;
            case "ellipse":
                islandBoundary = ellipse.build(ellipse.generateEllipse(width / 2, height / 2, width / 1.5, height / 2));
                configSeed += "3";
                break;
            default: // case "circle"
                islandBoundary = ellipse.build(ellipse.generateEllipse(width / 2, height / 2, width / 3, height / 3));
                configSeed += "0";
                break;
            }
        } else {
            switch (genSeed.charAt(1)) {
                case '1':
                    coords = rect.generateRectangle(width / 4, height / 4, width / 2, height / 3);
                    islandBoundary = rect.build(coords);
                    break;
                case '2':
                    coords = rect.generateRectangle(width / 4, height / 4, width / 2, width / 2);
                    islandBoundary = rect.build(coords);
                    break;
                case '3':
                    islandBoundary = ellipse.build(ellipse.generateEllipse(width / 2, height / 2, width / 1.5, height / 2));
                    break;
                default: // case "circle"
                    islandBoundary = ellipse.build(ellipse.generateEllipse(width / 2, height / 2, width / 3, height / 3));
                    break;
            }
        }
        
        coords.clear();
        LandGen lgn = new LandGen();
        tiles = lgn.createLand(originalMesh, islandBoundary);

        // Configure altitude
        Volcano volcano = new Volcano();
        CornerMountains valley = new CornerMountains();
        Hills hills = new Hills();
        AltitudeData altitudeData;

        if (random) {
            switch (altProfile) {
                case "volcano":
                    altitudeData = volcano.build(width, height, 1, 100, 0.8);
                    configSeed += "1";
                    break;
                case "hills":
                    altitudeData = valley.build(width, height, 4, 70, 0.9);
                    configSeed += "2";
                    break;
                default: // random hills
                    altitudeData = hills.build(width, height, 10, 50, 0.5, rnd);
                    configSeed += "0";
                    break;
            }
        } else {
            switch (genSeed.charAt(2)) {
                case '1':
                    altitudeData = volcano.build(width, height, 1, 100, 0.8);
                    break;
                case '2':
                    altitudeData = valley.build(width, height, 4, 70, 0.9);
                    break;
                default: // random hills
                    altitudeData = hills.build(width, height, 10, 50, 0.5, rnd);
                    break;
            }
        }
        AltitudeGen agen = new AltitudeGen();
        tiles = agen.transform(originalMesh, tiles, altitudeData, rnd);

        // Create lakes
        LakeGen lgen = new LakeGen();
        if (random) {
            tiles = lgen.transform(originalMesh, tiles, Integer.parseInt(numLakes), rnd);
            configSeed += String.format("%03d", Integer.parseInt(numLakes));
        } else
            tiles = lgen.transform(originalMesh, tiles, Integer.parseInt(genSeed.substring(3, 6)), rnd);

        // Create rivers
        RiverGen rgen = new RiverGen();
        River[] rivers;
        if (random) {
            rivers = rgen.createRivers(originalMesh, tiles, Integer.parseInt(numRivers), rnd);
            configSeed += String.format("%03d", Integer.parseInt(numRivers));
        } else
            rivers = rgen.createRivers(originalMesh, tiles, Integer.parseInt(genSeed.substring(6, 9)), rnd);

        // Create aquifers
        AquiferGen qgen = new AquiferGen();
        if (random) {
            tiles = qgen.transform(originalMesh, tiles, Integer.parseInt(numAquifers), rnd);
            configSeed += String.format("%03d", Integer.parseInt(numAquifers));
        } else
            tiles = qgen.transform(originalMesh, tiles, Integer.parseInt(genSeed.substring(9, 12)), rnd);

        // Enrich land with humidity, moisture, and vegetation
        EnrichmentGen egen = new EnrichmentGen();
        // Set the soil composition
        double[] composition;
        if (random) {
            switch (soilProfile) {
                case "dry":
                    composition = new Dry().defineComposition();
                    configSeed += "1";
                    break;
                default:
                    composition = new Wet().defineComposition();
                    configSeed += "0";
                    break;
            }
        } else {
            switch (genSeed.charAt(12)) {
                case '1':
                    composition = new Dry().defineComposition();
                    break;
                default:
                    composition = new Wet().defineComposition();
                    break;
            }
        }
        tiles = egen.enrichLand(originalMesh, tiles, rivers, composition);

        // Generate biomes
        // If biomeProfile == none then generate the island without specific biomes
        if (random) {
            if (!biomesProfile.equals("none")) {
                BiomesGen bgen = new BiomesGen();
                tiles = bgen.transform(tiles, biomesProfile);
                switch (biomesProfile) {
                    case "arctic":
                        configSeed += "1";
                        break;
                    case "tropical":
                        configSeed += "2";
                        break;
                    case "desert":
                        configSeed += "3";
                        break;
                    case "temperate":
                        configSeed += "4";
                        break;
                    default:
                        configSeed += "0";
                        break;
                }
            } else
                configSeed += "0";
        } else {
            if (genSeed.charAt(13) != '0') {
                BiomesGen bgen = new BiomesGen();
                tiles = bgen.transform(tiles, String.valueOf(genSeed.charAt(13)));
            }
        }

        // Generate heatmap
        HeatmapGen hmap = new HeatmapGen();
        tiles = hmap.transform(tiles, heatmapView);

        // Generate cities
        Graph g;
        if (random) {
            g = new CityGen().generate(originalMesh, tiles, Integer.parseInt(numCities), rnd);
            configSeed += String.format("%03d", Integer.parseInt(numCities));
        } else
            g = new CityGen().generate(originalMesh, tiles, Integer.parseInt(genSeed.substring(14, 17)), rnd);
        // Generate roads
        List<Segment> roads = new RoadGen().generate(originalMesh, tiles, g);

        // Turn tiles into polygon properties
        Mesh islandMesh = mutateMesh(originalMesh, tiles, rivers, g.getNodes(), roads);

        // Create lagoon island if specified
        if (random) {
            if (mode.equals("lagoon")) {
                LagoonGen lgngen = new LagoonGen(width, height, width / 5, width / 3);
                islandMesh = lgngen.transform(originalMesh).build();
                configSeed = "1" + configSeed;
            } else
                configSeed = "0" + configSeed;
        } else {
            if (genSeed.charAt(0) == '1') {
                LagoonGen lgngen = new LagoonGen(width, height, width / 5, width / 3);
                islandMesh = lgngen.transform(originalMesh).build();
            }
        }

        factory.write(islandMesh, config.export("o")); // Write to output mesh

        if (random)
            return configSeed + genSeed;
        else
            return genSeed;
    }

    private Mesh mutateMesh(Mesh oMesh, List<Tile> tiles, River[] rivers, List<Node> cities, List<Segment> roads) {
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
                double discharge = rivers[i].getDischarge() * 3;
                if (discharge > 3.5) discharge = 3.5;
                Property thickness = Property.newBuilder().setKey("thickness").setValue(String.valueOf(discharge)).build();
                s.addProperties(color).addProperties(thickness);
            }
            mesh.addSegments(s);
        }

        // Plot cities
        Property color = Property.newBuilder().setKey("rgb_color").setValue("0,0,255").build();
        for (Node n : cities) {
            if (Boolean.parseBoolean(n.get("isCity"))) {
                Vertex city = oMesh.getVertices(Integer.parseInt(n.get("vertexIndex")));
                Property thickness = Property.newBuilder().setKey("thickness").setValue(n.get("size")).build();
                Vertex v = Vertex.newBuilder().setX(city.getX()).setY(city.getY()).addProperties(color).addProperties(thickness).build();
                mesh.addVertices(v);
            }
        }

        mesh.addAllSegments(roads);

        return mesh.build();
    }

}
