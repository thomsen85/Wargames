package edu.ntnu.idatt2001.gui.components;

import edu.ntnu.idatt2001.gui.utils.CameraMove;
import edu.ntnu.idatt2001.models.map.TileMap;
import edu.ntnu.idatt2001.models.Battle;
import edu.ntnu.idatt2001.models.units.Unit;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Component that manages a canvas.
 */
public class GameCanvas {

    private final Canvas canvas;
    private final Battle battle;

    private final TileMap map;
    private final Image mapImage;

    private final List<Image> walkingSprites = new ArrayList<>(5);

    private int walkingCycle = 0;

    private final List<Consumer<Unit>> onUnitPressEvent = new ArrayList<>();

    /**
     * Constructor for creating an instance of the GameCanvas class.
     * 
     * @param canvas
     *            to manage
     * @param battle
     *            to manage
     * @throws FileNotFoundException
     *             if the sprites are not in the correct resource folder
     */
    public GameCanvas(Canvas canvas, Battle battle, String pathToSprites) throws FileNotFoundException {
        this.canvas = canvas;
        this.battle = battle;

        this.map = battle.getTileMap();
        mapImage = map.getMapAsImage();

        canvas.getGraphicsContext2D().setImageSmoothing(false);
        canvas.setOnMousePressed(this::canvasMousePress);

        loadSprites(pathToSprites);
        centerCamera();
    }

    /**
     * Manages clicks on the canvas, if a unit is clicked it fires consumers that are added with addOnUnitPress
     * 
     * @param mouseEvent
     *            that got called
     */
    private void canvasMousePress(MouseEvent mouseEvent) {
        double canvasX = mouseEvent.getX();
        double canvasY = mouseEvent.getY();

        Point2D mapPoint = null;
        try {
            mapPoint = canvas.getGraphicsContext2D().getTransform().inverseTransform(canvasX, canvasY);
        } catch (NonInvertibleTransformException e) {
            e.printStackTrace();
        }

        double pressDistanceThreshold = 10;
        Point2D finalMapPoint = mapPoint;
        Optional<Unit> pressedUnit = battle.getAllUnits().stream()
                .filter(unit -> new Point2D(unit.getX(), unit.getY()).distance(finalMapPoint) < pressDistanceThreshold)
                .findFirst();

        pressedUnit.ifPresent(unit -> onUnitPressEvent.forEach(unitConsumer -> unitConsumer.accept(unit)));
    }

    /**
     * Add Consumers that take a unit when a unit is pressed on the canvas.
     * 
     * @param handler
     *            consumer
     */
    public void addOnUnitPress(Consumer<Unit> handler) {
        onUnitPressEvent.add(handler);
    }

    /**
     * Method that updates all the drawings on the canvas
     */
    public void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        drawMap(gc);
        drawPlayers(gc);
    }

    /**
     * Method for loading the sprites.
     * 
     * @param path
     *            resource path from GameCanvas
     * @throws FileNotFoundException
     *             if th path is wrong
     */
    private void loadSprites(String path) throws FileNotFoundException {
        File playerSprites = null;
        try {
            playerSprites = Paths.get(Objects.requireNonNull(getClass().getResource(path)).toURI()).toFile();
        } catch (URISyntaxException e) {
            throw new FileNotFoundException("Sprites could not be loaded.");
        }
        for (File file : Objects.requireNonNull(playerSprites.listFiles())) {
            if ("walk".equals(file.getName().split("-")[0])) {
                walkingSprites.add(new Image(file.toURI().toString()));
            }
        }
    }

    /**
     * Method for drawing the player
     * 
     * @param gc
     *            GraphicsContext
     */
    private void drawPlayers(GraphicsContext gc) {
        Image sprite = walkingSprites.get(walkingCycle);
        for (Unit unit : battle.getArmyOne().getAllUnits()) {
            gc.drawImage(sprite, unit.getX() - (sprite.getWidth() / 2), unit.getY() - (sprite.getHeight() / 2));
        }
        for (Unit unit : battle.getArmyTwo().getAllUnits()) {
            gc.drawImage(sprite, unit.getX() + (sprite.getWidth()) / 2, unit.getY() - (sprite.getHeight() / 2),
                    -sprite.getWidth(), sprite.getHeight());
        }
        if (walkingCycle >= walkingSprites.size() - 1) {
            walkingCycle = 0;
        } else {
            walkingCycle++;
        }
    }

    /**
     * Method for drawing the map
     * 
     * @param gc
     *            GraphicsContext
     */
    private void drawMap(GraphicsContext gc) {
        gc.drawImage(mapImage, 0, 0);
    }

    /**
     * Method for centering the camera so that the whole map fills the canvas.
     */
    public void centerCamera() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Scale s = new Scale(canvas.getWidth() / map.getPixelWidth(), canvas.getHeight() / map.getPixelHeight());
        gc.setTransform(s.getMxx(), s.getMyx(), s.getMxy(), s.getMyy(), s.getTx(), s.getTy());
    }

    /**
     * Method for moving the camera or the map, which ever way you see it. The method will allow the camera to go
     * outside of map range.
     * 
     * @param cameraMove
     *            direction
     * @param amount
     *            to move by.
     */
    public void moveCamera(CameraMove cameraMove, double amount) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        switch (cameraMove) {
        case ZOOM -> {
            Scale s = new Scale(amount, amount, canvas.getWidth() / 2, canvas.getHeight() / 2);
            gc.transform(s.getMxx(), s.getMyx(), s.getMxy(), s.getMyy(), s.getTx(), s.getTy());
        }
        case LEFT -> {
            Transform s = new Translate(amount, 0);
            gc.transform(s.getMxx(), s.getMyx(), s.getMxy(), s.getMyy(), s.getTx(), s.getTy());
        }
        case RIGHT -> {
            Transform s = new Translate(-amount, 0);
            gc.transform(s.getMxx(), s.getMyx(), s.getMxy(), s.getMyy(), s.getTx(), s.getTy());
        }
        case UP -> {
            Transform s = new Translate(0, 10);
            gc.transform(s.getMxx(), s.getMyx(), s.getMxy(), s.getMyy(), s.getTx(), s.getTy());
        }
        case DOWN -> {
            Transform s = new Translate(0, -10);
            gc.transform(s.getMxx(), s.getMyx(), s.getMxy(), s.getMyy(), s.getTx(), s.getTy());

        }
        }
    }

    public Battle getBattle() {
        return battle;
    }

    /**
     * Gets the position of the unit on the canvas, reason for this is because of the camera transformation.
     * 
     * @param unit
     *            to have the position of
     * @return the canvas position.
     */
    public Point2D getCanvasPositionOfUnit(Unit unit) {
        double unitX = unit.getX();
        double unitY = unit.getY();

        return canvas.getGraphicsContext2D().getTransform().transform(unitX, unitY);
    }
}
