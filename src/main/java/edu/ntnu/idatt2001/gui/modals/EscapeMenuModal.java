package edu.ntnu.idatt2001.gui.modals;

import edu.ntnu.idatt2001.gui.View;
import edu.ntnu.idatt2001.models.units.UnitType;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

/**
 * Typical menu that pops-up when pressing esc.
 */
public class EscapeMenuModal extends Modal{

    private final View view;

    public EscapeMenuModal(Stage owner, View view) {
        super(owner);
        this.view = view;
        populateScene();
    }

    /**
     * Fills the modal with components
     */
    private void populateScene() {
        setPrefSize(200, 300);
        addStylesheet("edu/ntnu/idatt2001/gui/styles/style.css");
        addStylesheet("edu/ntnu/idatt2001/gui/styles/modal.css");

        GridPane root = getGridPaneRoot();

        Button mainMenu = new Button("Exit to menu");
        Button exit = new Button("Exit to desktop");
        Button close = new Button("Close");
        mainMenu.setOnAction(actionEvent -> view.setCurrentScene(View.MAIN_MENU_VIEW));
        exit.setOnAction(actionEvent -> System.exit(0));
        close.setOnAction(actionEvent -> close());
        root.addRow(0, mainMenu);
        root.addRow(1, exit);
        root.addRow(2, close);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.SOMETIMES);
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.SOMETIMES);
        columnConstraints.setHalignment(HPos.CENTER);

        root.getRowConstraints().addAll(rowConstraints, rowConstraints,rowConstraints);
        root.getColumnConstraints().add(columnConstraints);
    }
}
