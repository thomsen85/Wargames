package edu.ntnu.thosve.gui.controllers;

import edu.ntnu.thosve.gui.View;
import edu.ntnu.thosve.gui.components.CameraMove;
import edu.ntnu.thosve.gui.components.GameCanvas;
import edu.ntnu.thosve.gui.modals.*;
import edu.ntnu.thosve.models.formations.RectangleFormation;
import edu.ntnu.thosve.models.Army;
import edu.ntnu.thosve.models.Battle;
import edu.ntnu.thosve.models.units.*;
import edu.ntnu.thosve.gui.Models;
import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.stage.FileChooser;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * WIP class. Not Yet implemented.
 */
public class GameController {
    Models models = Models.getInstance();
    View view = View.getInstance();

    @FXML
    private Label army1Label;
    @FXML
    private Label army2Label;
    @FXML
    private ProgressBar army1HealthBar;
    @FXML
    private ProgressBar army2HealthBar;
    @FXML
    private Label army1HealthLabel;
    @FXML
    private Label army2HealthLabel;
    @FXML
    private Canvas canvas;
    @FXML
    private BorderPane window;
    @FXML
    private Label speedIndicator;
    @FXML
    private Button simulateButton;
    @FXML
    private FontIcon pauseButtonIcon;
    @FXML
    private Button pauseButton;
    @FXML
    private Button increaseSpeedButton;
    @FXML
    private Button decreaseSpeedButton;
    @FXML
    private Pane canvasParent;

    private boolean simulationRunning = false;
    private boolean pausedSimulation = false;
    private boolean simulationDone = false;
    private GameLoop gameLoop;
    private double fps = 0;
    private double speedMultiplier = 1;

    private int army1StartUnitsNumber;
    private int army2StartUnitsNumber;
    private GameCanvas gameCanvas;

    private class GameLoop extends AnimationTimer {

        private final long FPS = 60L;
        private final long INTERVAL = 1_000_000_000L / FPS;

        private long last = 0;

        private final HashMap<Long, Runnable> timeQueue = new HashMap<>();

        @Override
        public void handle(long now) {
            if (now - last > INTERVAL) {
                double deltaTimeSeconds = (((double) (now - last)) / 1_000_000_000L);
                updateQueue(now);
                update(deltaTimeSeconds);
                draw();
                last = now;
            }
        }

        private void updateQueue(long now) {
            Set<Long> remover = new HashSet<>();
            timeQueue.keySet().stream().filter(time -> now > time).forEach(time -> {
                timeQueue.get(time).run();
                remover.add(time);
            });

            remover.forEach(timeQueue::remove);
        }

        public void addEventToTimeQueue(Runnable event, double secondsTo) {
            long time = last + ((long) secondsTo) * 1_000_000_000;
            timeQueue.put(time, event);
        }
    }

    /**
     * Method that gets called when window is loaded.
     */
    public void initialize() {
        canvasParent.widthProperty().addListener(
                (observableValue, number, t1) -> canvas.setWidth(observableValue.getValue().doubleValue()));
        canvasParent.heightProperty().addListener(
                ((observableValue, number, t1) -> canvas.setHeight(observableValue.getValue().doubleValue())));
        this.gameLoop = new GameLoop();
        this.gameCanvas = new GameCanvas(canvas, models.getCurrentBattle());

        // loadTestData();
        updateArmyLabels();
        gameCanvas.addOnUnitPress(this::showUnitInformation);

        gameLoop.start();
    }

    private void showUnitInformation(Unit unit) {
        Point2D canvasPoint = gameCanvas.getCanvasPosition(unit);
        Modal modal = new UnitInformationModal(view.getStage(), unit);
        modal.setX(canvasPoint.getX());
        modal.setY(canvasPoint.getY());
        modal.getScene().setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                modal.close();
            }
        });
        modal.show();
    }

    private void updateArmyLabels() {
        army1Label.setText(gameCanvas.getBattle().getArmyOne().getName());
        army2Label.setText(gameCanvas.getBattle().getArmyTwo().getName());
    }

    private void loadTestData() {
        Battle battle = gameCanvas.getBattle();
        Army armyOne = battle.getArmyOne();
        Army armyTwo = battle.getArmyTwo();

        armyOne.addAll(UnitFactory.getUnits(UnitType.CAVALRY_UNIT, 500, 100d));
        armyTwo.addAll(UnitFactory.getUnits(UnitType.CAVALRY_UNIT, 550, 80d));

        armyOne.applyFormation(new RectangleFormation(200, 200, 400, 500));
        armyTwo.applyFormation(new RectangleFormation(1600, 200, 1800, 500));
    }

    /**
     * Gets called by the game controller before draw
     * 
     * @param deltaTime
     *            in seconds
     */
    private void update(double deltaTime) {
        fps = 1 / deltaTime;
        deltaTime *= speedMultiplier;

        if (simulationRunning && !pausedSimulation && !simulationDone) {
            simulationStep(deltaTime);
        }
    }

    private void simulationStep(double deltaTime) {
        Battle battle = models.getCurrentBattle();
        if (!battle.simulateStep(deltaTime)) {
            Army winner = battle.getArmyOne().hasUnits() ? battle.getArmyOne() : battle.getArmyTwo();
            simulationDone = true;
            showWinner(winner);
        }
    }

    private void showWinner(Army winner) {
        Modal modal = new Modal(view.getStage());
        modal.getGridPaneRoot().add(new Label("Winner is :" + winner.getName()), 0, 0);
        modal.getScene().getStylesheets().add("edu/ntnu/thosve/gui/styles/message.css");
        modal.show();
        gameLoop.addEventToTimeQueue(modal::close, 5);

    }

    /**
     * Method that gets called after update has been called, all drawn elements should be updated here.
     */
    private void draw() {
        gameCanvas.draw();
        updateHealthBars();
    }

    private void updateHealthBars() {
        double armyOneHealth = (double) models.getCurrentBattle().getArmyOne().getAllUnits().size()
                / army1StartUnitsNumber;
        double armyTwoHealth = (double) models.getCurrentBattle().getArmyTwo().getAllUnits().size()
                / army2StartUnitsNumber;

        army1HealthLabel
                .setText(models.getCurrentBattle().getArmyOne().getAllUnits().size() + "/" + army1StartUnitsNumber);
        army2HealthLabel
                .setText(models.getCurrentBattle().getArmyTwo().getAllUnits().size() + "/" + army2StartUnitsNumber);

        army1HealthBar.setProgress(armyOneHealth);
        army2HealthBar.setProgress(armyTwoHealth);
    }

    private void updateSpeedIndicator() {
        speedIndicator.setText("%.2fx".formatted(speedMultiplier));
    }

    @FXML
    private void onKeyPress(KeyEvent e) {
        switch (e.getCode()) {
        case E -> gameCanvas.centerCamera();
        case Q -> gameCanvas.moveCamera(CameraMove.ZOOM, 1.1);
        case A -> gameCanvas.moveCamera(CameraMove.LEFT, 10);
        case D -> gameCanvas.moveCamera(CameraMove.RIGHT, 10);
        case W -> gameCanvas.moveCamera(CameraMove.UP, 10);
        case S -> gameCanvas.moveCamera(CameraMove.DOWN, 10);
        }
    }

    @FXML
    private void pausePlayButton() {
        if (pausedSimulation) {
            pausedSimulation = false;
            pauseButtonIcon.setIconLiteral("cil-media-pause");
        } else {
            pausedSimulation = true;
            pauseButtonIcon.setIconLiteral("cil-media-play");
        }
    }

    @FXML
    private void onIncreaseSpeed() {
        speedMultiplier *= 2;
        updateSpeedIndicator();
    }

    @FXML
    private void onDecreaseSpeed() {
        speedMultiplier /= 2;
        updateSpeedIndicator();
    }

    @FXML
    private void onSimulateButtonPress() {
        if (!simulationRunning) {
            try {
                startSimulation();
            } catch (IllegalStateException e) {
                showMessage(e.getMessage(), 3);
            }
        } else {
            resetSimulation();
        }
    }

    private void resetSimulation() {
        models.getCurrentBattle().reset();

        // Reset pause button
        if (pausedSimulation) {
            pausePlayButton();
        }

        speedMultiplier = 1;
        updateSpeedIndicator();

        setDisableMediaButtons(true);

        simulateButton.setText("Simulate");
        simulationRunning = false;
        simulationDone = false;
    }

    private void startSimulation() {
        if (!models.getCurrentBattle().getArmyOne().hasUnits() || !models.getCurrentBattle().getArmyTwo().hasUnits()) {
            throw new IllegalStateException("Both armies must contain units.");
        }

        models.getCurrentBattle().setResetPoint();

        army1StartUnitsNumber = models.getCurrentBattle().getArmyOne().getAllUnits().size();
        army2StartUnitsNumber = models.getCurrentBattle().getArmyTwo().getAllUnits().size();

        setDisableMediaButtons(false);

        simulateButton.setText("Reset");
        simulationRunning = true;

    }

    private void setDisableMediaButtons(boolean disabled) {
        pauseButton.setDisable(disabled);
        increaseSpeedButton.setDisable(disabled);
        decreaseSpeedButton.setDisable(disabled);
    }

    private File openFileSaver() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Save file (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        return fileChooser.showSaveDialog(view.getStage());
    }

    private File openFileLoader() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Save file (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser.showOpenDialog(view.getStage());
    }

    private void showMessage(String text, double duration) {
        Modal modal = new Modal(view.getStage());
        Label label = new Label(text);
        label.setPadding(new Insets(10, 10, 10, 10));

        modal.getGridPaneRoot().add(label, 0, 0);
        modal.getScene().getStylesheets().add("edu/ntnu/thosve/gui/styles/message.css");
        gameLoop.addEventToTimeQueue(modal::close, duration);
        modal.show();

    }

    @FXML
    private void army1AddUnits() {
        Modal modal = new AddUnitModal(view.getStage(), models.getCurrentBattle().getArmyOne(),
                (unitType, integer) -> addUnits(models.getCurrentBattle().getArmyOne(), unitType, integer));
        modal.getStage().centerOnScreen();
        modal.showAndWait();

    }

    @FXML
    private void army2AddUnits() {
        Modal modal = new AddUnitModal(view.getStage(), models.getCurrentBattle().getArmyTwo(),
                (unitType, integer) -> addUnits(models.getCurrentBattle().getArmyTwo(), unitType, integer));
        modal.getStage().centerOnScreen();
        modal.showAndWait();
    }

    private void addUnits(Army army, UnitType unitType, int amount) {
        army.addAll(UnitFactory.getUnits(unitType, amount));
    }

    @FXML
    private void army1RemoveUnit() {
        Modal modal = new RemoveUnitModal(view.getStage(), models.getCurrentBattle().getArmyOne());
        modal.showAndWait();

    }

    @FXML
    private void army2RemoveUnit() {
        Modal modal = new RemoveUnitModal(view.getStage(), models.getCurrentBattle().getArmyTwo());
        modal.showAndWait();
    }

    @FXML
    private void army1ClearArmy() {
        models.getCurrentBattle().getArmyOne().clearUnits();
    }

    @FXML
    private void army2ClearArmy() {
        models.getCurrentBattle().getArmyTwo().clearUnits();

    }

    @FXML
    private void army1SaveArmy() {
        File file = openFileSaver();
        if (file != null) {
            try {
                models.getCurrentBattle().getArmyOne().writeCSV(file.getAbsolutePath());
                showMessage("Saved " + models.getCurrentBattle().getArmyTwo().getName() + " to: " + file.getAbsolutePath(), 5);
            } catch (IOException e) {
                showMessage("Something went wrong with saving the file", 5);
            }
        }

    }

    @FXML
    private void army2SaveArmy() {
        File file = openFileSaver();
        if (file != null) {
            try {
                models.getCurrentBattle().getArmyTwo().writeCSV(file.getAbsolutePath());
                showMessage("Saved " + models.getCurrentBattle().getArmyTwo().getName() + " to: " + file.getAbsolutePath(), 5);

            } catch (IOException e) {
                showMessage("Something went wrong with saving the file", 5);
            }
        }

    }

    @FXML
    private void army1LoadArmy() {
        File file = openFileLoader();
        if (file != null) {
            try {
                Army armyOne = Army.readCSV(file.getAbsolutePath(), models.getCurrentBattle().getTileMap());
                models.getCurrentBattle().setArmyOne(armyOne);
                showMessage("Load army " + armyOne.getName() + " from: " + file.getAbsolutePath(), 5);
            } catch (IOException e) {
                showMessage("Something went wrong with loading the file.", 5);
            }
        }
    }

    @FXML
    private void army2LoadArmy() {
        File file = openFileLoader();
        if (file != null) {
            try {
                Army armyTwo = Army.readCSV(file.getAbsolutePath(), models.getCurrentBattle().getTileMap());
                models.getCurrentBattle().setArmyTwo(armyTwo);
                showMessage("Load army " + armyTwo.getName() + " from: " + file.getAbsolutePath(), 5);

            } catch (IOException e) {
                showMessage("Something went wrong with loading the file.", 5);
            }
        }
    }

    @FXML
    private void army1SetFormation() {
        SetFormationModal m = new SetFormationModal(view.getStage(), models.getCurrentBattle().getArmyOne());
        m.showAndWait();
    }

    @FXML
    private void army2SetFormation() {
        SetFormationModal m = new SetFormationModal(view.getStage(), models.getCurrentBattle().getArmyTwo());
        m.showAndWait();
    }
}
