package edu.ntnu.thosve.gui.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * WIP class. Not Yet implemented.
 */
public class GameController {
    @FXML
    private Label army1Label;
    @FXML
    private Label army2Label;
    @FXML
    private ProgressBar army1HealthBar;
    @FXML
    private ProgressBar army2HealthBar;
    @FXML
    private Canvas canvas;
    @FXML
    private BorderPane window;

    /**
     * Method that gets called when window is loaded.
     */
    public void initialize() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        window.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                System.out.println(keyEvent.getCode());
                if (keyEvent.getCode().equals(KeyCode.G)) {
                    System.out.println("Cube");
                    gc.setFill(Color.BLUE);
                    gc.fillRect(0,0,400,400);

                }
            }
        });
    }


    @FXML
    private void simulate() {

    }

    @FXML
    private void army1AddUnits() {

    }

    @FXML
    private void army2AddUnits() {

    }


    @FXML
    private void army1RemoveUnit() {

    }

    @FXML
    private void army2RemoveUnit() {

    }

    @FXML
    private void army1ClearArmy() {

    }

    @FXML
    private void army2ClearArmy() {

    }

    @FXML
    private void army1SaveArmy() {

    }

    @FXML
    private void army2SaveArmy() {

    }

    @FXML
    private void army1LoadArmy() {

    }

    @FXML
    private void army2LoadArmy() {

    }



}
