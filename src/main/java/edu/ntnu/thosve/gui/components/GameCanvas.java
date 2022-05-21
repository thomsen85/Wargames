package edu.ntnu.thosve.gui.components;

import com.sun.javafx.scene.traversal.Direction;
import edu.ntnu.thosve.map.TileMap;
import edu.ntnu.thosve.models.Battle;
import edu.ntnu.thosve.models.units.Unit;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameCanvas {

    private final Canvas canvas;
    private final Battle battle;

    private final TileMap map;
    private final Image mapImage;

    private final List<Image> walkingSprites = new ArrayList<>(5);

    private int walkingCycle = 0;


    public GameCanvas(Canvas canvas, Battle battle) {
        this.canvas = canvas;
        this.battle = battle;

        this.map = battle.getTileMap();
        mapImage = map.getMapAsImage();

        canvas.getGraphicsContext2D().setImageSmoothing(false);

        loadSprites();
        centerCamera();
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
                case "idle" -> walkingSprites.add(new Image(file.toURI().toString())); // TODO: CODE
                case "attack" -> walkingSprites.add(new Image(file.toURI().toString()));// TODO: CODE

            }
        }
    }

    private void drawPlayers(GraphicsContext gc) {
        for (Unit unit : battle.getArmyOne().getAllUnits()) {
            gc.drawImage(walkingSprites.get(walkingCycle), unit.getX(), unit.getY());
        }
        for (Unit unit : battle.getArmyTwo().getAllUnits()) {
            gc.drawImage(walkingSprites.get(walkingCycle), unit.getX(), unit.getY(), -walkingSprites.get(0).getWidth(),
                    walkingSprites.get(0).getHeight());
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
        Scale s = new Scale(canvas.getWidth() / map.getPixelWidth() ,canvas.getHeight() / map.getPixelHeight());
        System.out.println(s);
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

    public Canvas getCanvas() {
        return canvas;
    }

    public Battle getBattle() {
        return battle;
    }
}
