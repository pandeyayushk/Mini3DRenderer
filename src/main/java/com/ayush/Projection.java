package com.ayush;

public class Projection {

    public static Vector2 orthographic(Vector3 point) {

        return new Vector2(point.x, point.y);
    }

    public static Vector2 perspective(Vector3 point) {

        double x = point.x / point.z;
        double y = point.y / point.z;

        return new Vector2(x, y);
    }
}