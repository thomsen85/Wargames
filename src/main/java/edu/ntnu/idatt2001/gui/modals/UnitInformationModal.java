package edu.ntnu.idatt2001.gui.modals;

import edu.ntnu.idatt2001.models.units.Unit;
import edu.ntnu.idatt2001.models.units.UnitType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UnitInformationModal extends Modal {

    private final Unit unit;

    public UnitInformationModal(Stage owner, Unit unit) {
        super(owner);
        this.unit = unit;
        populateScene();
    }

    private void populateScene() {
        addStylesheet("edu/ntnu/idatt2001/gui/styles/style.css");
        addStylesheet("edu/ntnu/idatt2001/gui/styles/modal.css");

        GridPane root = getGridPaneRoot();

        Label name = new Label(unit.getName());
        ProgressBar health = new ProgressBar(unit.getHealth() / unit.getStartHealth());
        Label type = new Label("Type: " + UnitType.getTypeOfUnit(unit).toString());
        Button close = new Button("Close");
        close.setOnAction(actionEvent -> close());
        root.addRow(0, health);
        root.addRow(1, name);
        root.addRow(2, type);
        root.addRow(3, close);
    }

}
