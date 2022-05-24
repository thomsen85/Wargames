package edu.ntnu.idatt2001.gui.controllers;

import edu.ntnu.idatt2001.gui.View;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MainMenuController {
    View view = View.getInstance();

    @FXML
    private BorderPane borderPane;

    @FXML
    private ImageView logo;

    @FXML
    private Pane logoParent;

    /**
     * Method that gets called when window is loaded.
     */
    public void initialize() {
        logo.fitWidthProperty().bind(logoParent.widthProperty());
        borderPane.widthProperty().addListener((observableValue, number, t1) -> borderPane
                .setStyle("-fx-font: " + observableValue.getValue().intValue() / 40 + " \"Hoefler Text\""));
    }

    @FXML
    private void onQuitPress() {
        view.quit();
    }


    @FXML
    private void onNewGamePress() {
        view.setCurrentScene(View.NEW_GAME_VIEW);
    }
}
