package com.ayush;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JPanel {
    public Renderer(){
        setSize(new Dimension(800,600));
        setBackground(Color.BLACK);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.drawString("Mini 3D Renderer", 20, 30);
    }
}
