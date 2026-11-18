
package com.ayush;

import java.util.Arrays;

public class DepthBuffer {

    private final double[][] depth;
    private final int width;
    private final int height;

    public DepthBuffer(int width, int height) {
        this.width = width;
        this.height = height;

        depth = new double[height][width];

        clear();
    }

    public void clear() {
        for (double[] row : depth) {
            Arrays.fill(row, Double.POSITIVE_INFINITY);
        }
    }

    public boolean testAndSet(int x, int y, double z) {

        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false;
        }

        if (z < depth[y][x]) {
            depth[y][x] = z;
            return true;
        }

        return false;
    }
}
