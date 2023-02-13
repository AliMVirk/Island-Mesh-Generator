# Assignment A2: Mesh Generator

  - Ali Virk [virka9@mcmaster.ca]
  - Hamza Abou Jaib [aboujaih@mcmaster.ca]
  - Qamrosh Ahmad [ahmadq2@mcmaster.ca]

## How to run the product

_This section needs to be edited to reflect how the user can interact with the feature released in your project_

### Installation instructions

This product is handled by Maven, as a multi-module project. We assume here that you have cloned the project in a directory named `A2`

To install the different tooling on your computer, simply run:

```
mvn install
```

After installation, you'll find an application named `generator.jar` in the `generator` directory, and a file named `visualizer.jar` in the `visualizer` one. 

### Generator

To run the generator, go to the `generator` directory, and use `java -jar` to run the product. The product takes one single argument (so far), the name of the file where the generated mesh will be stored as binary.

```
cd generator 
java -jar generator.jar sample.mesh
ls -lh sample.mesh
-rw-r--r--  1 mosser  staff    29K 29 Jan 10:52 sample.mesh
```

### Visualizer

To visualize an existing mesh, go the the `visualizer` directory, and use `java -jar` to run the product. The product take two arguments (so far): the file containing the mesh, and the name of the file to store the visualization (as an SVG image).

```
cd visualizer 
java -jar visualizer.jar ../generator/sample.mesh sample.svg

... (lots of debug information printed to stdout) ...

ls -lh sample.svg
-rw-r--r--  1 mosser  staff    56K 29 Jan 10:53 sample.svg
```
To viualize the SVG file:

  - Open it with a web browser
  - Convert it into something else with tool slike `rsvg-convert`

## How to contribute to the project

When you develop features and enrich the product, remember that you have first to `package` (as in `mvn package`) it so that the `jar` file is re-generated by maven.

## Backlog

### Definition of Done

If a feature works as intended without bugs and the code is clean and concise, it is considered done.

### Product Backlog

|  Id   | Feature title                                                                                                                                 | Who?    | Start | End | Status     |
| :---: | --------------------------------------------------------------------------------------------------------------------------------------------- | ------- | ----- | --- | ---------- |
|  F01  | All squares in the mesh grid are polygons                                                                                                     | Qamrosh |       |     | P          |
|  F02  | Polygons includes indexes to all segments, including those shared by neighbours                                                               | Ali     |       |     | B(F01)     |
|  F03  | Polygons list their segments consecutively                                                                                                    | Ali     |       |     | B(F02)     |
|  F04  | Polygons include an index to their center vertex                                                                                              | Hamza   |       |     | B(F01)     |
|  F05  | Mesh contains no duplicate vertices, segments, or polygons                                                                                    | Hamza   |       |     | B(F01)     |
|  F06  | Vertices, segments, and polygons should have colors and thickness information                                                                 | Qamrosh |       |     | P          |
|  F07  | The default way the renderer displays the mesh is based on color and thickness                                                                | Ali     |       |     | B(F06)     |
|  F08  | Debug mode, toggled by -X flag as cmd line argument, shows polygons in black, centroids in red, and neighbourhood relationships in light grey | Hamza   |       |     | B(F02,F04) |


