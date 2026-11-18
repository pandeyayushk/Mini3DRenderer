package com.ayush;

import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;

import static org.junit.Assert.*;

public class RendererMathTest {
    private static final double EPSILON = 1e-9;

    @Test
    public void rotatesAroundEachAxis() {
        Vector3 x = new Vector3(0, 1, 0).rotateX(Math.PI / 2);
        assertEquals(0, x.y, EPSILON);
        assertEquals(1, x.z, EPSILON);

        Vector3 y = new Vector3(1, 0, 0).rotateY(Math.PI / 2);
        assertEquals(0, y.x, EPSILON);
        assertEquals(-1, y.z, EPSILON);

        Vector3 z = new Vector3(1, 0, 0).rotateZ(Math.PI / 2);
        assertEquals(0, z.x, EPSILON);
        assertEquals(1, z.y, EPSILON);
    }

    @Test
    public void projectsOrthographicAndPerspectivePoints() {
        Vector2 orthographic = Projection.orthographic(new Vector3(2, 3, 4));
        assertEquals(2, orthographic.x, EPSILON);
        assertEquals(3, orthographic.y, EPSILON);

        Vector2 perspective = Projection.perspective(new Vector3(2, 4, 2));
        assertEquals(1, perspective.x, EPSILON);
        assertEquals(2, perspective.y, EPSILON);
        assertNull(Projection.perspective(new Vector3(1, 1, 0)));
        assertNull(Projection.perspective(new Vector3(1, 1, -1)));
    }

    @Test
    public void calculatesBarycentricCoordinates() {
        double[] weights = Barycentric.calculate(
                new Vector2(0, 0), new Vector2(2, 0), new Vector2(0, 2),
                new Vector2(0.5, 0.5));
        assertNotNull(weights);
        assertEquals(0.5, weights[0], EPSILON);
        assertEquals(0.25, weights[1], EPSILON);
        assertEquals(0.25, weights[2], EPSILON);
        assertNull(Barycentric.calculate(new Vector2(0, 0), new Vector2(1, 1),
                new Vector2(2, 2), new Vector2(0, 0)));
    }

    @Test
    public void depthBufferKeepsNearestDepthAndClears() {
        DepthBuffer depth = new DepthBuffer(1, 1);
        assertTrue(depth.testAndSet(0, 0, 3));
        assertFalse(depth.testAndSet(0, 0, 4));
        assertTrue(depth.testAndSet(0, 0, 2));
        assertFalse(depth.testAndSet(-1, 0, 1));
        depth.clear();
        assertTrue(depth.testAndSet(0, 0, 4));
    }

    @Test
    public void rasterizerHandlesDegenerateAndOverlappingTriangles() {
        BufferedImage image = new BufferedImage(8, 8, BufferedImage.TYPE_INT_RGB);
        DepthBuffer depth = new DepthBuffer(8, 8);
        Rasterizer.drawTriangle(image, depth, new Vector3(1, 1, 1),
                new Vector3(2, 2, 1), new Vector3(3, 3, 1), Color.RED);
        assertEquals(Color.BLACK.getRGB(), image.getRGB(2, 2));

        Vector3 a = new Vector3(1, 1, 2);
        Vector3 b = new Vector3(6, 1, 2);
        Vector3 c = new Vector3(1, 6, 2);
        Rasterizer.drawTriangle(image, depth, a, b, c, Color.RED);
        Rasterizer.drawTriangle(image, depth,
                new Vector3(1, 1, 3), new Vector3(6, 1, 3), new Vector3(1, 6, 3),
                Color.BLUE);
        assertEquals(Color.RED.getRGB(), image.getRGB(2, 2));
    }
}
