package com.ayush;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Renderer extends JPanel {
    private final int width = 800;
    private final int height = 600;
    private final double scale = 250;
    private double angle = 0;

    private final Vector3 v1 = new Vector3(0, 1, 0);
    private final Vector3 v2 = new Vector3(-1, -1, 1);
    private final Vector3 v3 = new Vector3(1, -1, 1);
    private final Vector3 v4 = new Vector3(0, -1, -1);

    private final DepthBuffer depthBuffer=new DepthBuffer(width, height);

    public Renderer(){
        setPreferredSize(new Dimension(800,600));
        setBackground(Color.BLACK);

        Timer timer = new Timer(16, e -> {
            angle += 0.02;
            repaint();
        });

        timer.start();

    }

    private Vector3 project(Vector3 v) {

        Vector3 rotated = v.rotateX(angle)
                .rotateY(angle);

        Vector3 translated = new Vector3(
                rotated.x,
                rotated.y,
                rotated.z + 4
        );

        double x = translated.x / translated.z;
        double y = translated.y / translated.z;

        return new Vector3(
                width / 2.0 + x * scale,
                height / 2.0 - y * scale,
                translated.z
        );
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage image =
                new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);

        depthBuffer.clear();

        Vector3 p1 = project(v1);
        Vector3 p2 = project(v2);
        Vector3 p3 = project(v3);
        Vector3 p4 = project(v4);

        Rasterizer.drawTriangle(
                image, depthBuffer, p1, p2, p3,
                Color.GREEN
        );

        Rasterizer.drawTriangle(
                image, depthBuffer, p1, p3, p4,
                Color.CYAN
        );

        Rasterizer.drawTriangle(
                image, depthBuffer, p1, p4, p2,
                Color.ORANGE
        );

        Rasterizer.drawTriangle(
                image, depthBuffer, p2, p4, p3,
                Color.MAGENTA
        );

        g.drawImage(image, 0, 0, null);
    }
}
