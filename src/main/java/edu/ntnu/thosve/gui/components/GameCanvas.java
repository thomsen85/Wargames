package edu.ntnu.thosve.gui.components;

import edu.ntnu.thosve.gui.utils.CameraMove;
import edu.ntnu.thosve.models.map.TileMap;
import edu.ntnu.thosve.models.Battle;
import edu.ntnu.thosve.models.units.Unit;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class GameCanvas {

    private final Canvas canvas;
    private final Battle battle;

    private final TileMap map;
    private final Image mapImage;

    private final List<Image> walkingSprites = new ArrayList<>(5);

    private int walkingCycle = 0;

    private final List<Consumer<Unit>> onUnitPressEvent = new ArrayList<>();

    public GameCanvas(Canvas canvas, Battle battle) {
        this.canvas = canvas;
        this.battle = battle;

        this.map = battle.getTileMap();
        mapImage = map.getMapAsImage();

        canvas.getGraphicsContext2D().setImageSmoothing(false);
        canvas.setOnMousePressed(this::canvasMousePress);

        loadSprites();
        centerCamera();
    }

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

    public void addOnUnitPress(Consumer<Unit> handler) {
        onUnitPressEvent.add(handler);
    }

    public void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        drawMap(gc);
        drawPlayers(gc);
    }

    private void loadSprites() {
        File playerSprites = new File("src/main/resources/edu/ntnu/thosve/gui/images/player-sprites");
        for (File file : Objects.requireNonNull(playerSprites.listFiles())) {
            switch (file.getName().split("-")[0]) {
            case "walk" -> walkingSprites.add(new Image(file.toURI().toString()));
            // Possibility to add more types of sprites

            }
        }
    }

    private void drawPlayers(GraphicsContext gc) {
        Image sprite = walkingSprites.get(walkingCycle);
        for (Unit unit : battle.getArmyOne().getAllUnits()) {
            gc.drawImage(sprite, unit.getX() - (sprite.getWidth() / 2), unit.getY() - (sprite.getHeight() / 2));
        }
        for (Unit unit : battle.getArmyTwo().getAllUnits()) {
            gc.drawImage(sprite, unit.getX() + (sprite.getWidth()) / 2, unit.getY() - (sprite.getHeight() / 2),
                    -sprite.getWidth(), sprite.getHeight());
        }
        if (walkingCycle >= 4) {
            walkingCycle = 0;
        } else {
            walkingCycle++;
        }
    }

    private void drawMap(GraphicsContext gc) {
        gc.drawImage(mapImage, 0, 0);
    }

    public void centerCamera() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Scale s = new Scale(canvas.getWidth() / map.getPixelWidth(), canvas.getHeight() / map.getPixelHeight());
        gc.setTransform(s.getMxx(), s.getMyx(), s.getMxy(), s.getMyy(), s.getTx(), s.getTy());
    }

    private boolean transformInBounds(Transform transform, Canvas canvas, TileMap map) {
        return true;
    }

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

    public Point2D getCanvasPosition(Unit unit) {
        double unitX = unit.getX();
        double unitY = unit.getY();

        return canvas.getGraphicsContext2D().getTransform().transform(unitX, unitY);
    }
}
