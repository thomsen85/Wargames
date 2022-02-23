package edu.ntnu.thosve.gui;

import edu.ntnu.thosve.Army;
import edu.ntnu.thosve.Battle;
import edu.ntnu.thosve.units.InfantryUnit;
import edu.ntnu.thosve.units.RangedUnit;
import edu.ntnu.thosve.units.Unit;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;



public class Panel extends JPanel implements ActionListener {
    private Timer timer;
    private Battle battle;
    private Army armyOne;
    private Army armyTwo;

    private int unitRadius = 4;

    private int zoomFactor;
    private int[] zoomOrigin;

    private boolean simulating = false;
    private boolean done = false;

    private final int DELAY = 20;

    private Random random;

    BufferedImage background;


    public Panel(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        random = new Random();

        armyOne = new Army("Army One");
        armyTwo = new Army("Army Two");

        for(int i = 0; i < 1000; i++) {
            armyOne.add(new InfantryUnit("Infantry", 100));
        }

        armyOne.spreadUnitsEvenly(100,100,400, 900);

        for(int i = 0; i < 1000; i++) {
            armyTwo.add(new RangedUnit("Ranged", 30));
        }

        armyTwo.spreadUnitsEvenly(600,100,900, 800);

        try {
            background = ImageIO.read(new File("src/main/resources/Field.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        zoomOrigin = new int[]{500,500};
        zoomFactor = 2;


        startGUI();
        addComponents();
    }

    private void addComponents() {
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                battle = new Battle(armyOne, armyTwo);
                simulating = true;
            }
        });
        add(startButton);

    }

    public void startGUI() {
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // --- Background ---
        g.drawImage(background,0,0,null);

        if (armyOne.hasUnits()) {
            g.setColor(Color.GREEN);
            for(Unit unit : armyOne.getAllUnits()) {
                g.fillOval((int) unit.getX(), (int) unit.getY(), unitRadius * zoomFactor, unitRadius * zoomFactor);
            }
        }

        g.setColor(Color.RED);
        for(Unit unit : armyTwo.getAllUnits()) {
            g.fillOval((int) unit.getX(), (int) unit.getY(), unitRadius * zoomFactor, unitRadius * zoomFactor);
        }

        if (done) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Blackadder ITC", Font.BOLD, 50));
            if (armyOne.hasUnits()) {
                g.drawString("Army One won!", 50, 500);
            } else {
                g.drawString("Army Two won", 500, 500);
            }

        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (simulating) {
            simulating = battle.manualSimulate((double) DELAY / 1000);
            done = !simulating;
        }
        repaint();
    }
}
