package com.ayush;

public class Projection {

    public static Vector2 orthographic(Vector3 point) {

        return new Vector2(point.x, point.y);
    }

    public static Vector2 perspective(Vector3 point) {

        if (point == null || !Double.isFinite(point.x)
                || !Double.isFinite(point.y) || !Double.isFinite(point.z)
                || point.z <= 0) {
            return null;
        }

        double x = point.x / point.z;
        double y = point.y / point.z;

        if (!Double.isFinite(x) || !Double.isFinite(y)) {
            return null;
        }

        return new Vector2(x, y);
    }
}
