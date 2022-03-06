package edu.ntnu.thosve.gui;

import edu.ntnu.thosve.Army;
import edu.ntnu.thosve.formations.Formation;
import edu.ntnu.thosve.formations.RectangleFormation;
import edu.ntnu.thosve.units.UnitTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * Small window for adding units. This is for testing purposes only and wil be changed.
 */
public class AddUnitsForm extends JFrame {
    private JLabel formHeader;
    private JComboBox armyChoice;
    private JComboBox unitChoice;
    private JTextField numberChoice;
    private JTextField coordinatesChoice;
    private JTextField sizeChoice;
    private JButton addButton;

    private HashMap<String, Army> armies;
    private HashMap<String, UnitTypes> units;

    private Panel parent;

    private JPanel panel;

    public AddUnitsForm(Panel parent, HashMap<String, Army> armies) {
        super("Add Units");

        this.parent = parent;
        this.armies = armies;
        this.units = getUnits();

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        addComponents();
        add(panel);
        pack();
        setVisible(true);
    }

    /**
     * Method that returns all unit types.
     * @return
     */
    private HashMap<String, UnitTypes> getUnits() {
        HashMap<String, UnitTypes> units = new HashMap<>();
        units.put(UnitTypes.INFANTRY_UNIT.toString(), UnitTypes.INFANTRY_UNIT);
        units.put(UnitTypes.RANGED_UNIT.toString(), UnitTypes.RANGED_UNIT);
        units.put(UnitTypes.CAVALRY_UNIT.toString(), UnitTypes.CAVALRY_UNIT);
        units.put(UnitTypes.COMMANDER_UNIT.toString(), UnitTypes.COMMANDER_UNIT);
        return units;
    }

    /**
     * Method for adding the necessary components.
     */
    private void addComponents() {
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
                try {
                    Army army = armies.get((String) armyChoice.getSelectedItem());
                    UnitTypes unit = units.get((String) unitChoice.getSelectedItem());
                    String name = "Unit";

                    int number = Integer.parseInt(numberChoice.getText());

                    int x = Integer.parseInt(coordinatesChoice.getText().replaceAll("\\s+","").split(",")[0]);
                    int y = Integer.parseInt(coordinatesChoice.getText().replaceAll("\\s+","").split(",")[1]);

                    int width = Integer.parseInt(sizeChoice.getText().replaceAll("\\s+","").split(",")[0]);
                    int height = Integer.parseInt(sizeChoice.getText().replaceAll("\\s+","").split(",")[1]);

                    if (army == null || unit == null) {
                        throw new IllegalStateException("Make sure to have selected a unit and army.");
                    } else if (number < 1) {
                        throw new IllegalArgumentException("Number of Units can not be less than one");
                    }
                    Formation formation = new RectangleFormation(x, y, x + width, y + height);

                    parent.addUnits(army, unit, name, number, formation);
                } catch (IllegalArgumentException err) {
                    JOptionPane.showMessageDialog(null, "Error on input:\n" + err.getMessage(),
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                }

            }});

        panel.add(addButton);
    }

}
