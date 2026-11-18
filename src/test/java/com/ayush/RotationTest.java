package com.ayush;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RotationTest {

    @Test
    public void Rotation(){
        Vector3 point = new Vector3(0, 1, 0);

        Vector3 rotated = point.rotateX(Math.toRadians(90));

        System.out.println("Original: "
                + point.x + ", " + point.y + ", " + point.z);

        System.out.println("Rotated: "
                + rotated.x + ", " + rotated.y + ", " + rotated.z);
    }
}
