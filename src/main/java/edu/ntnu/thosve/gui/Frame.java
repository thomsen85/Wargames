package edu.ntnu.thosve.gui;

import javax.swing.JFrame;

public class Frame extends JFrame {
    public Frame(String title, int width, int height) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        add(new Panel(width, height));
        pack();
    }
}
