
package com.ayush;

public class Barycentric {

    public static double[] calculate(
            Vector2 a,
            Vector2 b,
            Vector2 c,
            Vector2 p
    ) {

        double denominator =
                (b.y - c.y) * (a.x - c.x)
                        + (c.x - b.x) * (a.y - c.y);

        if (Math.abs(denominator) < 1e-10) {
            return null;
        }

        double alpha =
                ((b.y - c.y) * (p.x - c.x)
                        + (c.x - b.x) * (p.y - c.y))
                        / denominator;

        double beta =
                ((c.y - a.y) * (p.x - c.x)
                        + (a.x - c.x) * (p.y - c.y))
                        / denominator;

        double gamma = 1 - alpha - beta;

        return new double[]{alpha, beta, gamma};
    }
}
