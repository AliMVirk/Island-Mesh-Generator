# Assignment A2: Mesh Generator

- Ali Virk [virka9@mcmaster.ca]
- Hamza Abou Jaib [aboujaih@mcmaster.ca]
- Qamrosh Ahmad [ahmadq2@mcmaster.ca]

## Running the generator and visualizer

The following information shows the necessary commands to create a mesh and visualize the mesh in an SVG file. If needed, the mesh can be visualized in debug mode by including a `-X` flag.

Running the commands with no extra arguments will result in a grid mesh composed of squares, where the canvas size is 500x500 with a square size of 20.

When running the generator, the following arguments can be used to control the mesh generation:

```
java -jar generator/generator.jar generator/sample.mesh -t <mesh type> -d <canvas height> -w <canvas width> -s <square size> -n <number of polygons> -r <relaxation level>
```

To see all possible user configurations, run the above with argument ```-h``` or ```--help```

The following is an example of generating and visualizing a **regular** mesh:

```mvn install```

```java -jar generator/generator.jar generator/sample.mesh -t regular -d 600 -w 600 -s 15```

```java -jar visualizer/visualizer.jar generator/sample.mesh visualizer/sample.svg```

<br>
<img src="./diagrams/regular_mesh_example.png" width="400" />
<br>
Figure 1.0: Regular mesh rendered regularly
<br><br>

```java -jar visualizer/visualizer.jar generator/sample.mesh visualizer/sample.svg -X```

<br>
<img src="./diagrams/regular_mesh_debug_example.png" width="400" />
<br>
Figure 1.1: Regular mesh rendered in debug mode
<br><br>

The following is an example of generating and visualizing an **irregular** mesh:

```mvn install```

```java -jar generator/generator.jar generator/sample.mesh -t irregular -n 300 -r 3```

```java -jar visualizer/visualizer.jar generator/sample.mesh visualizer/sample.svg```

<br>
<img src="./diagrams/Irregular_mesh_example.png" width="400" />
<br>
Figure 2.0: Irregular mesh rendered regularly
<br><br>

```java -jar visualizer/visualizer.jar generator/sample.mesh visualizer/sample.svg -X```

<br>
<img src="./diagrams/Irregular_mesh_debug_example.png" width="400" />
<br>
Figure 2.1: Irregular mesh rendered in debug mode
<br><br>

## Generating islands

The following information shows how to transform an existing mesh by generating terrain.

When running the island generator, the following arguments can be used to control the island generation:

```
java -jar island/island.jar -i <mesh input path> -o <mesh output path> --mode <generation mode> --shape <island shape> --altitude <altitude profile> --lakes <max number of lakes> --rivers <number of rivers> --aquifers <number of aquifers> --soil <soil composition profile> --heatmap <heatmap category> --biomes <biomes profile>
```

To see all possible user configurations, run the above with argument ```-h``` or ```--help```

Pre-made shapes that can be used:  
```--shape circle, --shape ellipse, --shape rectangle, --shape square```

Pre-made altitude profiles that can be used:  
```--altitude volcano, --altitude hills, --altitude randomhills```

Number of lakes must be between 0 and 999:  
```--lakes 5```

Number of rivers must be between 0 and 999:  
```--rivers 5```

Number of aquifers must be between 0 and 999:  
```--aquifers 5```

Pre-made soil profiles that can be used:  
```--soil dry, --soil wet```

Pre-made heatmap profiles that can be used:  
```--heatmap altitude, --heatmap moisture, --heatmap humidity```

Pre-made biomes profiles that can be used:  
```--biomes arctic, --biomes tropical, --biomes desert, --biomes temperate, --biomes none```

Pre-generated seed can be selected to regenerate an island
```--seed <seed>```

The following is an example of generating and visualizing an island mesh:

```mvn install```

```java -jar generator/generator.jar generator/sample.mesh -t irregular -d 1000 -w 1000 -n 2000```

```java -jar island/island.jar -i generator/sample.mesh -o island/sample.mesh --mode lagoon```

```java -jar visualizer/visualizer.jar island/sample.mesh visualizer/sample.svg```

<br>
<img src="./diagrams/lagoon_island_example.png" width="400" />
<br>
Figure 3.0: Lagoon island visualized regularly
<br><br>

The following is an example of generating and visualizing an ellipse shaped island with four mountains on the edges mesh:
```mvn install```

```java -jar generator/generator.jar generator/sample.mesh -t irregular -d 1000 -w 1000 -n 2000```

```java -jar island/island.jar -i generator/sample.mesh -o island/sample.mesh --shape ellipse --altitude cornerMountains```

```java -jar visualizer/visualizer.jar island/sample.mesh visualizer/sample.svg```
<br>
<img src="./diagrams/ellipseIsland.png" width="400" />
<br>
Figure 3.1: Ellipse island with 4 mountains/peaks
<br><br>

<br>
<img src="./diagrams/ellipseIslandWithAltitudesShowing.png" width="400" />
<br>
Figure 3.2: Ellipse island with 4 mountains/peaks showing the altitudes (white is highest point, cyan is lowest point)
<br><br>

The following is an example of generating and visualizing an ellipse shaped island with four mountains on the edges mesh with 5 lakes, 5 rivers, 5 aquifers, wet soil and an arctic biome:
```mvn install```

```java -jar generator/generator.jar generator/sample.mesh -t irregular -d 1000 -w 1000 -n 2000```

```java -jar island/island.jar -i generator/sample.mesh -o island/sample.mesh --shape ellipse --altitude cornerMountains --lakes 5 --rivers 5 --aquifers 5 --soil wet --biome arctic```

```java -jar visualizer/visualizer.jar island/sample.mesh visualizer/sample.svg```
<br>
<img src="./diagrams/arctic.png" width="400" />
<br>
Figure 4.1: Arctic biome with 5 lakes, 5 rivers, 5 aquifers and wet soil
<br><br>

When an island is generated a seed is outputted which can be used to recreate the same island.
<br>
<img src="./diagrams/seed.png" width="900" />
<br>
Figure 4.2: Seed outputted when island is generated
<br>

To use this seed to recreate the island run:  
```mvn install```

```java -jar generator/generator.jar generator/sample.mesh -t irregular -d 1000 -w 1000 -n 2000```

```java -jar island/island.jar -i generator/sample.mesh -o island/sample.mesh --seed 0300050050050109013514```

```java -jar visualizer/visualizer.jar island/sample.mesh visualizer/sample.svg```

The following is an example of generating and visualizing the heatmap of an ellipse shaped island with four mountains on the edges mesh with 5 lakes, 5 rivers, 5 aquifers, wet soil and an arctic biome:
```mvn install```

```java -jar generator/generator.jar generator/sample.mesh -t irregular -d 1000 -w 1000 -n 2000```

```java -jar island/island.jar -i generator/sample.mesh -o island/sample.mesh --shape ellipse --altitude cornerMountains --lakes 5 --rivers 5 --aquifers 5 --soil wet --biome arctic --heatmap altitude```

```java -jar visualizer/visualizer.jar island/sample.mesh visualizer/sample.svg```
<br>
<img src="./diagrams/heatmap.png" width="400" />
<br>
Figure 4.3: Altitude heatmap for arctic biomes with 5 lakes, 5 rivers, 5 aquifers and wet soil
<br><br>


## How to contribute to the project

When you develop features and enrich the product, remember that you have first to `package` (as in `mvn package`) it so that the `jar` file is re-generated by maven.

## Backlog

### Definition of Done

If a feature works as intended without bugs and the code is clean and concise, it is considered done.

### Product Backlog

|  Id   | Feature title                                                                                                                                 | Who?    | Start     | End       | Status |
| :---: | --------------------------------------------------------------------------------------------------------------------------------------------- | ------- | --------- | --------- | ------ |
|  F01  | All squares in the mesh grid are polygons                                                                                                     | Qamrosh | 2/15/2023 | 2/15/2023 | D      |
|  F02  | Polygons includes indexes to all segments, including those shared by neighbours                                                               | Ali     | 2/17/2023 | 2/17/2023 | D      |
|  F03  | Polygons list their segments consecutively                                                                                                    | Qamrosh | 2/15/2023 | 2/15/2023 | D      |
|  F04  | Polygons include an index to their center vertex                                                                                              | Hamza   | 2/17/2023 | 2/17/2023 | D      |
|  F05  | Mesh contains no duplicate vertices, segments, or polygons                                                                                    | Qamrosh | 2/15/2023 | 2/15/2023 | D      |
|  F06  | Vertices, segments, and polygons should have colors and thickness information                                                                 | Ali     | 2/18/2023 | 2/18/2023 | D      |
|  F07  | Debug mode, toggled by -X flag as cmd line argument, shows polygons in black, centroids in red, and neighbourhood relationships in light grey | Hamza   | 2/19/2023 | 2/19/2023 | D      |
|  F08  | Generate random points, one for each expected polygon                                                                                         | Hamza   | 2/23/2023 | 2/23/2023 | D      |
|  F09  | Compute the Voronoi Diagram of the generated points to create a mesh                                                                          | Qamrosh | 2/24/2023 | 2/25/2023 | D      |
|  F10  | Apply and repeat Lloyd relaxation to the mesh until it is smoothened                                                                          | Qamrosh | 2/25/2023 | 2/25/2023 | D      |
|  F11  | Compute neighbourhood relationships using Delaunay’s triangulation                                                                            | Hamza   | 2/25/2023 | 2/26/2023 | D      |
|  F12  | For each irregular polygon, reorder its segments by computing its Convex Hull so that the consecutiveness invariants holds                    | Ali     | 2/26/2023 | 2/26/2023 | D      |
|  F13  | User can control the type of mesh and relevant configurations                                                                                 | Qamrosh | 2/26/2023 | 2/27/2023 | D      |
|  F14  | Introduce a property to differentiate types of polygon tiles as "water and "land" tiles                                                       | Ali     | 2/28/2023 | 3/03/2023 | D      |
|  F15  | Add "lagoon" tiles                                                                                                                            | Hamza   | 3/06/2023 | 3/06/2023 | D      |
|  F16  | Modify tiles on the boundary to be "beach" tiles                                                                                              | Qamrosh | 3/06/2023 | 3/06/2023 | D      |
|  F17  | Implement command line configuration of input and output mesh                                                                                 | Ali     | 2/28/2023 | 3/02/2023 | D      |
|  F18  | Implement user configurable island shapes                                                                                                     | Ali     | 3/13/2023 | 3/13/2023 | D      |
|  F19  | Implement differing altitude for tiles                                                                                                        | Qamrosh | 3/15/2023 | 3/15/2023 | D      |
|  F20  | Allow user to configure altitude profiles                                                                                                     | Hamza   | 3/16/2023 | 3/18/2023 | D      |
|  F21  | Implement water-type tiles as lakes tiles                                                                                                     | Hamza   | 3/20/2023 | 3/22/2023 | D      |
|  F22  | Introduce humidity to tiles near lake tiles                                                                                                   | Ali     | 3/23/2023 | 3/23/2023 | D      |
|  F23  | Vegetation can be influenced by nearby lake tiles                                                                                             | Qamrosh | 3/23/2023 | 3/23/2023 | D      |
|  F24  | The maximum number of lakes can be controlled via command line arguments                                                                      | Hamza   | 3/22/2023 | 3/22/2023 | D      |
|  F25  | Add rivers that start at a random vertex and follow a line of edges                                                                           | Qamrosh | 3/21/2023 | 3/22/2023 | D      |
|  F26  | Rivers stop flowing at lowest point or when an ocean tile is reached                                                                          | Ali     | 3/22/2023 | 3/22/2023 | D      |
|  F27  | Introduce humidity to tiles near the river                                                                                                    | Qamrosh | 3/23/2023 | 3/23/2023 | D      |
|  F28  | Vegetation can be influenced by nearby river tiles                                                                                            | Hamza   | 3/23/2023 | 3/23/2023 | D      |
|  F29  | Implement discharge level, which controls thickness, and adjust it when rivers merge                                                          | Ali     | 3/22/2023 | 3/22/2023 | D      |
|  F30  | The number of rivers can be controlled via command line arguments                                                                             | Qamrosh | 3/22/2023 | 3/22/2023 | D      |
|  F31  | The bigger the river the more the more moisture it brings to the surrounding soil                                                             | Hamza   | 3/23/2023 | 3/23/2023 | D      |
|  F32  | Introduce aquifers (underground bodies of water), located randomly around the island                                                          | Ali     | 3/23/2023 | 3/23/2023 | D      |
|  F33  | Aquifers bring moisture to surrounding tiles                                                                                                  | Qamrosh | 3/23/2023 | 3/23/2023 | D      |
|  F34  | The number of aquifers is controlled via command line arguments                                                                               | Hamza   | 3/23/2023 | 3/23/2023 | D      |
|  F35  | The speed at which soil absorbs humidity is dependent on its composition                                                                      | Qamrosh | 3/25/2023 | 3/25/2023 | D      |
|  F36  | The absorption profile is decided via a command line argument                                                                                 | Hamza   | 3/25/2023 | 3/25/2023 | D      |
|  F37  | Group collections of tiles as biomes based on temperature and humidity                                                                        | Hamza   | 3/25/2023 | 3/26/2023 | D      |
|  F38  | Different biomes should be represented with different colours                                                                                 | Hamza   | 3/25/2023 | 3/26/2023 | D      |
|  F39  | Allow user to configure the island biome                                                                                                      | Hamza   | 3/26/2023 | 3/26/2023 | D      |
|  F40  | Fetch temperature and humidity information based on user configured biomes                                                                    | Hamza   | 3/26/2023 | 3/26/2023 | D      |
|  F41  | Attribute a seed for each random configuration and island generation                                                                          | Qamrosh | 3/26/2023 | 3/26/2023 | D      |
|  F42  | Allow user to input a seed that will reproduce the same island generation method                                                              | Qamrosh | 3/26/2023 | 3/26/2023 | D      |
|  F43  | Introduce user configurable heatmaps for humidity, moisture, and vegetation                                                                   | Qamrosh | 3/26/2023 | 3/26/2023 | D      |
