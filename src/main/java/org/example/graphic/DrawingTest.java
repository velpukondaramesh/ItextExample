package org.example.graphic;

import javax.swing.*;

public class DrawingTest {

    public static void main(String[] args) {
        int w = 640;
        int h = 480;
        Cloud cloud = new Cloud();
        JFrame frame = new JFrame();
        DrawingCanvas dc = new DrawingCanvas(w, h, cloud);
        frame.setSize(w, h);
        frame.setTitle("Drawing in Java");
        frame.add(dc);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
