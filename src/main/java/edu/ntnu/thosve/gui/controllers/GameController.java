package edu.ntnu.thosve.gui.controllers;

import edu.ntnu.thosve.gui.View;
import edu.ntnu.thosve.models.formations.RectangleFormation;
import edu.ntnu.thosve.models.Army;
import edu.ntnu.thosve.models.Battle;
import edu.ntnu.thosve.models.units.*;
import edu.ntnu.thosve.gui.Models;
import edu.ntnu.thosve.map.TileMap;
import edu.ntnu.thosve.map.TileMapFactory;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.transform.Scale;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * WIP class. Not Yet implemented.
 */
public class GameController {
    Models models = Models.getInstance();
    View view = View.getInstance();

    @FXML private Label army1Label;
    @FXML private Label army2Label;
    @FXML private ProgressBar army1HealthBar;
    @FXML private ProgressBar army2HealthBar;
    @FXML private Label army1HealthLabel;
    @FXML private Label army2HealthLabel;
    @FXML private Canvas canvas;
    @FXML private BorderPane window;
    @FXML private Label speedIndicator;
    @FXML private Button simulateButton;
    @FXML private FontIcon pauseButtonIcon;
    @FXML private Button pauseButton;
    @FXML private Button increaseSpeedButton;
    @FXML private Button decreaseSpeedButton;



    private boolean simulationRunning = false;
    private boolean pausedSimulation = false;
    private final GameLoop gameLoop;
    private TileMap map;
    private Image mapImage;
    private double fps = 0;
    private double speedMultiplier = 1;

    private int army1StartUnitsNumber;
    private int army2StartUnitsNumber;
    private int walkingCycle = 0;

    private final List<Image> walkingSprites = new ArrayList<>(5);


    private class GameLoop extends AnimationTimer {

        private final long FPS = 60L;
        private final long INTERVAL = 1_000_000_000L / FPS;

        private long last = 0;

        @Override
        public void handle(long now) {
            if (now - last > INTERVAL) {
                double deltaTimeSeconds = (((double) (now - last)) / 1_000_000_000L);

                update(deltaTimeSeconds);
                draw();
                last = now;
            }
        }
    }

    public GameController() {
        gameLoop = new GameLoop();
        loadSprites();
    }

    /**
     * Method that gets called when window is loaded.
     */
    public void initialize() {
        int tilePixelSize = 3;
        int tileWidth = (int) canvas.getWidth() / tilePixelSize + 1;
        int tileHeight = (int) canvas.getHeight() / tilePixelSize + 1;
        map = TileMapFactory.getRandomTerrainMap(50, tilePixelSize, tileWidth, tileHeight);
        mapImage = map.getMapAsImage();

        Army armyOne = new Army("Army 1");
        Army armyTwo = new Army("Army 2");

        armyOne.addAll(UnitFactory.getUnits(UnitType.CAVALRY_UNIT, 200, "Unit1", 100d));
        armyTwo.addAll(UnitFactory.getUnits(UnitType.CAVALRY_UNIT, 250, "Unit2", 80d));

        armyOne.applyFormation(new RectangleFormation(200, 200, 400, 500));
        armyTwo.applyFormation(new RectangleFormation(1600, 200, 1800, 500));

        models.setCurrentBattle(new Battle(armyOne, armyTwo, map));

        canvas.getGraphicsContext2D().setImageSmoothing(false);

        gameLoop.start();

    }

    private void loadSprites() {
        File playerSprites = new File("src/main/resources/edu/ntnu/thosve/gui/images/player-sprites");
        for (File file : Objects.requireNonNull(playerSprites.listFiles())) {
            System.out.println(file);
            switch (file.getName().split("-")[0]) {
                case "walk" -> walkingSprites.add(new Image(file.toURI().toString()));
            }
        }

        System.out.println();
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

        if (simulationRunning && !pausedSimulation) {
            simulationStep(deltaTime);
        }
    }

    /**
     * Method that gets called after update has been called, all drawn elements should be updated here.
     */
    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        drawMap(gc);
        drawFPSCounter(gc);
        drawPlayers(gc);
        updateHealthBars();
    }

    private void updateHealthBars() {
        double armyOneHealth = (double) models.getCurrentBattle().getArmyOne().getAllUnits().size() / army1StartUnitsNumber;
        double armyTwoHealth = (double) models.getCurrentBattle().getArmyTwo().getAllUnits().size() / army2StartUnitsNumber;

        army1HealthLabel.setText(models.getCurrentBattle().getArmyOne().getAllUnits().size() + "/" +  army1StartUnitsNumber);
        army2HealthLabel.setText(models.getCurrentBattle().getArmyTwo().getAllUnits().size() + "/" +  army2StartUnitsNumber);

        army1HealthBar.setProgress(armyOneHealth);
        army2HealthBar.setProgress(armyTwoHealth);
    }


    private void drawPlayers(GraphicsContext gc) {
        for (Unit unit : models.getCurrentBattle().getArmyOne().getAllUnits()) {
            gc.drawImage(walkingSprites.get(walkingCycle), unit.getX(), unit.getY());
        }
        for (Unit unit : models.getCurrentBattle().getArmyTwo().getAllUnits()) {
            gc.drawImage(walkingSprites.get(walkingCycle), unit.getX(), unit.getY(), -walkingSprites.get(0).getWidth(),
                    walkingSprites.get(0).getHeight());
        }
        if (walkingCycle >= 4) {
            walkingCycle = 0;
        } else {
            walkingCycle++;
        }
    }

    private void drawFPSCounter(GraphicsContext gc) {
        gc.fillText("%.1f".formatted(fps), canvas.getWidth() / 2, canvas.getHeight() / 2);
    }

    private void drawMap(GraphicsContext gc) {
        gc.drawImage(mapImage, 0, 0);
    }

    private void simulationStep(double deltaTime) {
        if (!models.getCurrentBattle().simulateStep(deltaTime)) {
            gameLoop.stop();
        }
    }

    @FXML
    private void onKeyPress(KeyEvent e) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if (e.getCode() == KeyCode.E ) {
            Scale s = new Scale(1.1,1.1);
            gc.transform(s.getMxx(), s.getMyx(), s.getMxy(), s.getMyy(), s.getTx(), s.getTy());
        } else if (e.getCode() == KeyCode.Q) {
            Scale s = new Scale(0.9, 0.9);
            gc.transform(s.getMxx(), s.getMyx(), s.getMxy(), s.getMyy(), s.getTx(), s.getTy());
        }

    }

    @FXML
    private void pausePlayButton() {

        if (pausedSimulation)  {
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

    private void updateSpeedIndicator() {
        speedIndicator.setText("%.2fx".formatted(speedMultiplier));
    }

    @FXML
    private void onSimulateButtonPress() {
        if (!simulationRunning) {
            startSimulation();
        } else {
            resetSimulation();
        }
    }

    private void resetSimulation() {
        models.getCurrentBattle().reset();

        if (pausedSimulation) {
            pausePlayButton();
        }

        speedMultiplier = 1;
        updateSpeedIndicator();

        setDisableMediaButtons(true);

        simulateButton.setText("Simulate");
        simulationRunning = false;
    }

    private void startSimulation() {
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
