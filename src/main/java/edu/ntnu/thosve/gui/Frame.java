package edu.ntnu.thosve.gui;

import javax.swing.*;

public class Frame extends JFrame {

    public Frame(String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new Panel(1000, 1000));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
