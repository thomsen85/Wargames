package edu.ntnu.thosve.gui;

import edu.ntnu.thosve.Army;
import edu.ntnu.thosve.formations.RectangleFormation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class AddUnitsForm extends JFrame {
    private JLabel formHeader;
    private JComboBox armyChoice;
    private JComboBox unitChoice;
    private JTextField numberChoice;
    private JTextField coordinatesChoice;
    private JTextField sizeChoice;
    private JButton addButton;

    private HashMap<String, Army> armies;
    private HashMap<String, Class> units;

    private Panel parent;

    private JPanel panel;

    public AddUnitsForm(Panel parent, HashMap<String, Army> armies, HashMap<String, Class> units) {
        super("Add Units");

        this.parent = parent;
        this.armies = armies;
        this.units = units;

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        addFields();
        add(panel);
        pack();
        setVisible(true);
    }

    private void addFields() {
        formHeader = new JLabel("Add Units", SwingConstants.LEFT);
        formHeader.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
        panel.add(formHeader);

        armyChoice = new JComboBox(armies.keySet().toArray());
        panel.add(armyChoice);

        unitChoice = new JComboBox(units.keySet().toArray());
        panel.add(unitChoice);


        panel.add(new JLabel("Number:"));
        numberChoice = new JTextField();
        numberChoice.setSize(50,10);
        panel.add(numberChoice);

        panel.add(new JLabel("Coordinates(x,y):"));
        coordinatesChoice = new JTextField();
        panel.add(coordinatesChoice);

        panel.add(new JLabel("Size (width, height):"));
        sizeChoice = new JTextField();
        panel.add(sizeChoice);




        addButton = new JButton("Add Units");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = Integer.parseInt(coordinatesChoice.getText().split(",")[0]);
                int y = Integer.parseInt(coordinatesChoice.getText().split(",")[1]);

                int width = Integer.parseInt(sizeChoice.getText().split(",")[0]);
                int height = Integer.parseInt(sizeChoice.getText().split(",")[1]);

                parent.addUnits(armies.get(armyChoice.getSelectedItem()), units.get(unitChoice.getSelectedItem()), "U",
                        Integer.parseInt(numberChoice.getText()), new RectangleFormation( x, y, x+width, y+height));

            }});

        panel.add(addButton);
    }

}
