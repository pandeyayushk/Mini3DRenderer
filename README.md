# Mini3DRenderer

A small 3D software renderer written in Java. It rotates and draws a four-sided, triangular-faced object using a CPU rasterizer and displays the result in a Swing window. The project keeps the rendering pipeline and mathematical operations implemented directly in Java, without an external graphics engine.

## Features

- `Vector3` rotations around the X, Y, and Z axes.
- Orthographic and perspective projection helpers.
- Triangle coverage using barycentric weights (computed from edge functions in the rasterizer).
- A per-pixel depth buffer and perspective-correct depth interpolation.
- Continuous rotation in a Swing timer, drawn into a reusable `BufferedImage`.

## Technology

- Java 21
- Maven
- Swing and AWT (`Graphics2D`, `BufferedImage`)
- JUnit 4 for lightweight unit tests

## Rendering pipeline

`Main` creates a Swing window containing `Renderer`. A Swing timer advances the rotation angle and requests a repaint. On each paint, the renderer clears its framebuffer and depth buffer, rotates the four model vertices, translates them along Z, and performs a perspective divide. It sends the resulting screen coordinates and camera-space depths to `Rasterizer` for the four faces. The rasterizer bounds each triangle to the image, evaluates edge functions at pixel centers, interpolates reciprocal depth, and writes a pixel when it is nearer than the stored depth.

`Projection` provides standalone orthographic and perspective projection helpers. The live demo currently performs its perspective projection directly in `Renderer.project`.

## Mathematical concepts

- **Axis rotation:** Each `Vector3.rotateX`, `rotateY`, and `rotateZ` method applies the standard sine and cosine rotation formula for that axis. Angles are in radians.
- **Perspective projection:** The demo divides the translated X and Y coordinates by Z, making objects appear smaller as their depth increases. The `Projection.perspective` helper returns `null` for a null, non-finite, or non-positive-depth point.
- **Barycentric coordinates:** Three weights describe a point relative to a triangle's vertices. `Barycentric.calculate` returns `null` for a degenerate triangle. The rasterizer uses equivalent edge-function ratios to test whether pixel centers are inside a triangle.
- **Depth interpolation:** For screen-space barycentric weights, the rasterizer interpolates reciprocal vertex depth (`1 / z`) and takes its reciprocal to obtain the fragment depth. The depth buffer keeps the smallest positive depth at each pixel.

## Project structure

```text
src/
  main/java/com/ayush/
    Barycentric.java
    DepthBuffer.java
    Main.java
    Projection.java
    Rasterizer.java
    Renderer.java
    Triangle.java
    Vector2.java
    Vector3.java
  test/java/com/ayush/
    RendererMathTest.java
pom.xml
```

`Triangle` is currently a simple geometry data class and is not used by the live demo, which passes vertices directly to the rasterizer. It is retained as a useful representation for future geometry work.

## Demo recording

[Watch the short demo recording](Demo.mp4).

## Build, test, and run

Install a Java 21 JDK and Maven, then run these commands from the project root:

```sh
mvn test
mvn package
java -cp target/classes com.ayush.Main
```

## Current limitations

- The demo renders one fixed four-vertex object with flat face colors.
- The renderer has no general camera, model loader, lighting, texture mapping, clipping, or near-plane handling.
- Triangles with a vertex at or behind the camera are skipped by the rasterizer rather than clipped.
- The Swing animation is a simple timer-driven repaint loop; frame timing is not synchronized to a target display refresh rate.

## Credits and inspiration

This project is an educational implementation by its author, using standard vector rotation, projection, barycentric coverage, and depth-buffering concepts. No external rendering engine or graphics library is used.
