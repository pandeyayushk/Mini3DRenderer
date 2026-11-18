
package com.ayush;

import java.awt.*;
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

        Vector2 p1 = Projection.perspective(a);
        Vector2 p2 = Projection.perspective(b);
        Vector2 p3 = Projection.perspective(c);

        int minX = Math.max(0, (int) Math.floor(Math.min(p1.x,
                Math.min(p2.x, p3.x))));
        int maxX = Math.min(image.getWidth() - 1,
                (int) Math.ceil(Math.max(p1.x, Math.max(p2.x, p3.x))));

        int minY = Math.max(0, (int) Math.floor(Math.min(p1.y,
                Math.min(p2.y, p3.y))));
        int maxY = Math.min(image.getHeight() - 1,
                (int) Math.ceil(Math.max(p1.y, Math.max(p2.y, p3.y))));

        double area = edge(p1, p2, p3);

        if (Math.abs(area) < 1e-10) return;

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {

                Vector2 p = new Vector2(x + 0.5, y + 0.5);

                double alpha = edge(p2, p3, p) / area;
                double beta = edge(p3, p1, p) / area;
                double gamma = edge(p1, p2, p) / area;

                if (alpha >= 0 && beta >= 0 && gamma >= 0) {

                    double z = alpha * a.z
                            + beta * b.z
                            + gamma * c.z;

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
