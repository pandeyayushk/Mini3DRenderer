package com.ayush;

public class Projection {

    public static Vector2 orthographic(Vector3 point) {

        return new Vector2(point.x, point.y);
    }
}