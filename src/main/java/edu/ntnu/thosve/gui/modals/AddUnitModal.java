package edu.ntnu.thosve.gui.modals;

import edu.ntnu.thosve.models.Army;
import edu.ntnu.thosve.models.formations.FormationType;
import edu.ntnu.thosve.models.units.UnitType;
import javafx.geometry.HPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import org.controlsfx.control.spreadsheet.Grid;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class AddUnitModal extends Modal {
    private final Army army;
    private BiConsumer<UnitType, Integer> handler;

    public AddUnitModal(Stage owner, Army army, BiConsumer<UnitType, Integer> handler) {
        super(owner);
        this.army = army;
        this.handler = handler;
        populateScene();
    }

    private void populateScene() {
        setPrefSize(600, 400);
        addStylesheet("edu/ntnu/thosve/gui/styles/style.css");
        addStylesheet("edu/ntnu/thosve/gui/styles/add-units.css");

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
        place.setOnAction(actionEvent -> handler.accept(unitChoice.getValue(), Integer.parseInt(amount.getText())));
        exit.setOnAction(actionEvent -> close());

        GridPane.setConstraints(title,0 ,0 ,2, 1);
        GridPane.setHgrow(title, Priority.ALWAYS);
        GridPane.setHalignment(title, HPos.CENTER);
        GridPane.setHalignment(exit, HPos.RIGHT);

        root.addRow(0, title);
        root.addRow(1, unitChoiceLabel, amountLabel);
        root.addRow(2, unitChoice, amount);
        root.addRow(3, exit, place);

        RowConstraints titleHeight = new RowConstraints();
        titleHeight.setPercentHeight(30);
        root.getRowConstraints().add(titleHeight);

        root.getRowConstraints().add(new RowConstraints());
        root.getRowConstraints().add(new RowConstraints());

        RowConstraints buttonHeight = new RowConstraints();
        buttonHeight.setPercentHeight(30);
        root.getRowConstraints().add(buttonHeight);

        ColumnConstraints first = new ColumnConstraints();
        first.setHgrow(Priority.ALWAYS);

        ColumnConstraints second = new ColumnConstraints();
        second.setHgrow(Priority.ALWAYS);

        root.getColumnConstraints().addAll(first, second);


    }
}
