package edu.ntnu.idatt2001.gui.modals;

import edu.ntnu.idatt2001.models.Army;
import edu.ntnu.idatt2001.models.units.UnitType;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.util.function.BiConsumer;

/**
 * Modal for adding units to a given army.
 */
public class AddUnitModal extends Modal {
    private final Army army;
    private BiConsumer<UnitType, Integer> handler;

    public AddUnitModal(Stage owner, Army army, BiConsumer<UnitType, Integer> handler) {
        super(owner);
        this.army = army;
        this.handler = handler;
        populateScene();
    }

    /**
     * Fills the modal with components
     */
    private void populateScene() {
        setPrefSize(500, 300);
        addStylesheet("edu/ntnu/idatt2001/gui/styles/style.css");
        addStylesheet("edu/ntnu/idatt2001/gui/styles/modal.css");

        GridPane root = getGridPaneRoot();

        Label title = new Label("Add Units to: \"" + army.getName() + "\"");
        Label unitChoiceLabel = new Label("Choose Unit Type:");
        ChoiceBox<UnitType> unitChoice = new ChoiceBox<>();
        Label amountLabel = new Label("Amount to add:");
        TextField amount = new TextField();
        Button exit = new Button("Close");
        Button place = new Button("Add Unit");

        title.setId("title");
        unitChoice.getItems().addAll(UnitType.values());
        unitChoice.setValue(UnitType.INFANTRY_UNIT);
        place.setOnAction(actionEvent -> {
            try {
                int amountOfUnits = Integer.parseInt(amount.getText());
                handler.accept(unitChoice.getValue(), amountOfUnits);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });
        exit.setOnAction(actionEvent -> close());

        GridPane.setConstraints(title, 0, 0, 2, 1);

        root.addRow(0, title);
        root.addRow(1, unitChoiceLabel, amountLabel);
        root.addRow(2, unitChoice, amount);
        root.addRow(3, exit, place);

        RowConstraints titleHeight = new RowConstraints();
        titleHeight.setPercentHeight(30);
        RowConstraints buttonHeight = new RowConstraints();
        buttonHeight.setPercentHeight(30);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.SOMETIMES);
        columnConstraints.setHalignment(HPos.CENTER);

        root.getRowConstraints().addAll(titleHeight, new RowConstraints(), new RowConstraints(), buttonHeight);
        root.getColumnConstraints().addAll(columnConstraints, columnConstraints);
    }
}
