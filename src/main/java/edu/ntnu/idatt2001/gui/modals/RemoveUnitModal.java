package edu.ntnu.idatt2001.gui.modals;

import edu.ntnu.idatt2001.models.Army;
import edu.ntnu.idatt2001.models.units.Unit;
import edu.ntnu.idatt2001.models.units.UnitType;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RemoveUnitModal extends Modal {

    private final Army army;
    private VBox contentList;

    public RemoveUnitModal(Stage owner, Army army) {
        super(owner);
        this.army = army;
        populateScene();

    }

    private void populateScene() {
        setPrefSize(700, 500);
        addStylesheet("edu/ntnu/idatt2001/gui/styles/style.css");
        addStylesheet("edu/ntnu/idatt2001/gui/styles/modal.css");

        GridPane root = getGridPaneRoot();
        ScrollPane scrollPane = new ScrollPane();
        contentList = new VBox();
        Button close = new Button("Close");
        close.setOnAction(actionEvent -> getStage().close());
        fillList();

        scrollPane.setContent(contentList);
        GridPane.setHgrow(scrollPane, Priority.ALWAYS);
        GridPane.setVgrow(scrollPane, Priority.ALWAYS);
        GridPane.setHalignment(scrollPane, HPos.CENTER);
        GridPane.setValignment(scrollPane, VPos.CENTER);
        root.add(scrollPane, 0, 0);
        root.add(close, 0, 1);

    }

    private void fillList() {
        for (Unit unit : army.getAllUnits()) {
            GridPane content = new GridPane();
            Label name = new Label(unit.getName());
            Label type = new Label(UnitType.getTypeOfUnit(unit).toString());
            Button remove = new Button("Remove");
            remove.setOnAction(actionEvent -> {
                army.remove(unit);
                contentList.getChildren().remove(content);
            });
            name.setPrefWidth(250);
            type.setPrefWidth(250);
            GridPane.setHgrow(remove, Priority.ALWAYS);
            GridPane.setHalignment(remove, HPos.RIGHT);
            content.addRow(0, name, type, remove);
            contentList.getChildren().add(content);
        }
    }
}
