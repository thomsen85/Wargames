package edu.ntnu.thosve.gui.controllers;

import edu.ntnu.thosve.formations.RectangleFormation;
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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * WIP class. Not Yet implemented.
 */
public class GameController {
    Models models = Models.getInstance();

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
    @FXML
    private Label speedIndicator;


    private boolean simulationRunning = false;
    private final GameLoop gameLoop;
    private TileMap map;
    private Image mapImage;
    private double fps = 0;
    private double speedMultiplier = 1;


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
    }

    /**
     * Gets called by the game controller before draw
     * @param deltaTime in seconds
     */
    private void update(double deltaTime) {
        fps = 1 / deltaTime;
        deltaTime *= speedMultiplier;

        if (simulationRunning) {
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
        updateHealthbars();
    }


    private void updateHealthbars() {

    }

    private void drawPlayers(GraphicsContext gc) {
        // Army one
        gc.setFill(Color.YELLOW);
        for (Unit unit : models.getCurrentBattle().getArmyOne().getAllUnits()) {
            gc.fillOval(unit.getX(), unit.getY(), 10, 10);
        }

        gc.setFill(Color.RED);
        for (Unit unit : models.getCurrentBattle().getArmyTwo().getAllUnits()) {
            gc.fillOval(unit.getX(), unit.getY(), 10, 10);
        }
    }

    private void drawFPSCounter(GraphicsContext gc) {
        gc.fillText("%.1f".formatted(fps), canvas.getWidth()/2, canvas.getHeight()/2);
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

        armyOne.addAll(UnitFactory.getUnits(UnitType.INFANTRY_UNIT,100, "Unit1", 100d));
        armyTwo.addAll(UnitFactory.getUnits(UnitType.INFANTRY_UNIT,150, "Unit2", 80d));

        armyOne.applyFormation(new RectangleFormation(200, 200, 400, 500));
        armyTwo.applyFormation(new RectangleFormation(600, 200, 800, 500));

        models.setCurrentBattle(new Battle(armyOne, armyTwo, map));

        gameLoop.start();

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
    private void simulate() {
        simulationRunning = true;
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
