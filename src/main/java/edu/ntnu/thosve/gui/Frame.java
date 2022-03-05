package edu.ntnu.thosve.gui;

import javax.swing.JFrame;

/**
 * The Window Frame that holds the content.
 */
public class Frame extends JFrame {
    public Frame(String title, int width, int height) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        Panel content = new Panel(width, height);
        add(content);
        pack();
    }
}
