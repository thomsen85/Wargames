package edu.ntnu.thosve.gui.modals;

import edu.ntnu.thosve.models.Army;
import edu.ntnu.thosve.models.formations.FormationType;
import edu.ntnu.thosve.models.units.UnitType;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class AddUnitModal extends Modal{
    private final Army army;
    private BiConsumer<UnitType, Integer> handler;

    public AddUnitModal(Stage owner, Army army, BiConsumer<UnitType, Integer> handler) {
        super(owner);
        this.army = army;
        this.handler = handler;
        populateScene();
    }

    private void populateScene() {
        setPrefSize(500, 500);
        addStylesheet("edu/ntnu/thosve/gui/styles/style.css");
        addStylesheet("edu/ntnu/thosve/gui/styles/add-units.css");

        GridPane root = (GridPane) getScene().getRoot();

        Label title = new Label("Add Units to: \"" + army.getName() + "\"");
        ChoiceBox<UnitType> unitChoice = new ChoiceBox<>();
        TextField amount = new TextField();
        Button exit = new Button("Close");
        Button place = new Button("Place Units");

        title.setId("title");
        unitChoice.getItems().addAll(UnitType.values());
        place.setOnAction(actionEvent -> handler.accept(unitChoice.getValue(), Integer.parseInt(amount.getText())));
        exit.setOnAction(actionEvent -> close());

        GridPane.setHgrow(title, Priority.ALWAYS);

        root.addRow(0, title);
        root.addRow(1, unitChoice, amount);
        root.addRow(3, exit, place);



    }

    private void place() {

    }
}
