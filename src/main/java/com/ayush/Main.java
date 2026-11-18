package com.ayush;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            JFrame frame=new JFrame("Mini 3D Renderer");

            Renderer renderer=new Renderer();


            frame.setVisible(true);

            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            frame.setLocationRelativeTo(null);

            frame.setResizable(false);


            frame.add(renderer);


            frame.pack();
        });
    }
}