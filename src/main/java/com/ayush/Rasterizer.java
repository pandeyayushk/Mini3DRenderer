
package com.ayush;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Rasterizer {

    public static void drawTriangle(
            BufferedImage image,
            DepthBuffer depthBuffer,
            Vector3 a,
            Vector3 b,
            Vector3 c,
            Color color
    ) {

        if (image == null || depthBuffer == null || a == null || b == null
                || c == null || color == null
                || !Double.isFinite(a.x) || !Double.isFinite(a.y) || !Double.isFinite(a.z)
                || !Double.isFinite(b.x) || !Double.isFinite(b.y) || !Double.isFinite(b.z)
                || !Double.isFinite(c.x) || !Double.isFinite(c.y) || !Double.isFinite(c.z)
                || a.z <= 0 || b.z <= 0 || c.z <= 0) {
            return;
        }

        int minX = Math.max(0, (int) Math.floor(
                Math.min(a.x, Math.min(b.x, c.x))));

        int maxX = Math.min(image.getWidth() - 1, (int) Math.ceil(
                Math.max(a.x, Math.max(b.x, c.x))));

        int minY = Math.max(0, (int) Math.floor(
                Math.min(a.y, Math.min(b.y, c.y))));

        int maxY = Math.min(image.getHeight() - 1, (int) Math.ceil(
                Math.max(a.y, Math.max(b.y, c.y))));

        Vector2 p1 = new Vector2(a.x, a.y);
        Vector2 p2 = new Vector2(b.x, b.y);
        Vector2 p3 = new Vector2(c.x, c.y);

        double area = edge(p1, p2, p3);

        if (Math.abs(area) < 1e-10) return;

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {

                Vector2 p = new Vector2(x + 0.5, y + 0.5);

                double alpha = edge(p2, p3, p) / area;
                double beta = edge(p3, p1, p) / area;
                double gamma = edge(p1, p2, p) / area;

                if (alpha >= 0 && beta >= 0 && gamma >= 0) {

                    // Perspective-correct depth interpolation
                    double inverseZ =
                            alpha / a.z +
                                    beta / b.z +
                                    gamma / c.z;

                    if (inverseZ <= 0) continue;

                    double z = 1.0 / inverseZ;

                    if (depthBuffer.testAndSet(x, y, z)) {
                        image.setRGB(x, y, color.getRGB());
                    }
                }
            }
        }
    }

    private static double edge(Vector2 a, Vector2 b, Vector2 p) {
        return (p.x - a.x) * (b.y - a.y)
                - (p.y - a.y) * (b.x - a.x);
    }
}
