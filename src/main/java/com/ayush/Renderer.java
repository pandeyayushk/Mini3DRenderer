package com.ayush;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JPanel {

    private final double scale = 250;

    private final Vector3 v1 = new Vector3(0, 1, 3);
    private final Vector3 v2 = new Vector3(-1, -1, 4);
    private final Vector3 v3 = new Vector3(1, -1, 4);
    private final Vector3 v4 = new Vector3(0, -1, 2);

    public Renderer(){
        setPreferredSize(new Dimension(800,600));
        setBackground(Color.BLACK);
    }

    private Point project(Vector3 v) {

        Vector2 p = Projection.perspective(v);

        int screenX = (int) (400 + p.x * scale);
        int screenY = (int) (300 - p.y * scale);

        return new Point(screenX, screenY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.GREEN);
        g2.setStroke(new BasicStroke(2));

        Point p1 = project(v1);
        Point p2 = project(v2);
        Point p3 = project(v3);
        Point p4 = project(v4);

        // Six edges of the tetrahedron
        g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        g2.drawLine(p1.x, p1.y, p3.x, p3.y);
        g2.drawLine(p1.x, p1.y, p4.x, p4.y);

        g2.drawLine(p2.x, p2.y, p3.x, p3.y);
        g2.drawLine(p2.x, p2.y, p4.x, p4.y);
        g2.drawLine(p3.x, p3.y, p4.x, p4.y);
    }
}
