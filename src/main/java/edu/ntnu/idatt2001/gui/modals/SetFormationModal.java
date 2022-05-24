package edu.ntnu.idatt2001.gui.modals;

import edu.ntnu.idatt2001.models.Army;
import edu.ntnu.idatt2001.models.formation.Formation;
import edu.ntnu.idatt2001.models.formation.FormationType;
import edu.ntnu.idatt2001.models.formation.RectangleFormation;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 * Modal for setting formation on an army.
 */
public class SetFormationModal extends Modal {

    private final Army army;

    public SetFormationModal(Stage owner, Army army) {
        super(owner);
        this.army = army;
        populateScene();
    }

    private void populateScene() {
        // TODO : check if number
        setPrefSize(500, 500);
        addStylesheet("edu/ntnu/idatt2001/gui/styles/style.css");
        addStylesheet("edu/ntnu/idatt2001/gui/styles/modal.css");
        GridPane root = (GridPane) getScene().getRoot();

        Label title = new Label("Set Formation for: \"" + army.getName() + "\"");
        ChoiceBox<FormationType> formatChoice = new ChoiceBox<>();

        TextField x = new TextField();
        Label xLabel = new Label("X-pos: ");
        TextField y = new TextField();
        Label yLabel = new Label("Y-pos: ");
        TextField width = new TextField();
        Label widthLabel = new Label("Width: ");
        TextField height = new TextField();
        Label heightLabel = new Label("Height: ");

        Button exit = new Button("Close");
        Button apply = new Button("Set Formation");

        title.setId("title");
        formatChoice.getItems().addAll(FormationType.values());
        formatChoice.setValue(FormationType.RECTANGLE);
        exit.setOnAction(actionEvent -> close());

        apply.setOnAction(actionEvent -> {
            try {
                int xVal = Integer.parseInt(x.getText());
                int yVal = Integer.parseInt(y.getText());
                int widthVal = Integer.parseInt(width.getText());
                int heightVal = Integer.parseInt(height.getText());

                army.applyFormation(getFormation(formatChoice.getValue(), xVal, yVal, widthVal, heightVal));
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });

        GridPane.setHgrow(title, Priority.ALWAYS);

        root.addRow(0, title);
        root.addRow(1, xLabel, yLabel);
        root.addRow(2, x, y);
        root.addRow(3, widthLabel, heightLabel);
        root.addRow(4, width, height);
        root.addRow(5, exit, apply);

    }

    private Formation getFormation(FormationType formationType, int x, int y, int width, int height) {
        Formation formation;
        switch (formationType) {
        default -> formation = new RectangleFormation(x, y, x + width, y + height);
        }

        return formation;
    }
}
