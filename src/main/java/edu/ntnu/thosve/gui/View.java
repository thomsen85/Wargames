package edu.ntnu.thosve.gui;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

/**
 * Singleton class for holding the Scene
 *
 * This class was reused form out INGT1002 Project, it was originally written by me.
 */
public class View {
    private static View instance;

    private Stage stage;
    private Scene currentScene;
    private int resolutionWidth;
    private int resolutionHeight;

    public final static String GAME_VIEW = "views/game.fxml";
    public final static String MAIN_MENU_VIEW = "views/main-menu.fxml";
    public final static String NEW_GAME_VIEW = "views/new-game.fxml";

    private View() {
    }

    /**
     * Method for getting the instance of the View class.
     * 
     * @return the instance
     */
    public static synchronized View getInstance() {
        if (instance == null) {
            instance = new View();
        }
        return instance;
    }

    public void setResolution(int resolutionWidth, int resolutionHeight) {
        this.resolutionWidth = resolutionWidth;
        this.resolutionHeight = resolutionHeight;
    }

    public void setResolution(Rectangle2D visualBounds) {
        this.resolutionWidth = (int) visualBounds.getWidth();
        this.resolutionHeight = (int) visualBounds.getHeight();
    }

    public int getResolutionWidth() {
        return resolutionWidth;
    }

    public int getResolutionHeight() {
        return resolutionHeight;
    }

    /**
     * Method for getting the current stage. The stage is the whole window.
     * 
     * @return the stage.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Method for setting the stage.
     * 
     * @throws NullPointerException
     *             if the stage give is null.
     * @param stage
     *            to be set, cannot be null
     */
    public void setStage(Stage stage) {
        Objects.requireNonNull(stage);
        this.stage = stage;
    }

    /**
     * Method for getting the current set scene.
     * 
     * @return the scene
     */
    public Scene getCurrentScene() {
        return currentScene;
    }

    /**
     * Method for setting the current scene.
     * 
     * @throws NullPointerException
     *             if the scene is null.
     * @param currentScene
     *            to be set, scene can not be {@code null}
     */
    public void setCurrentScene(Scene currentScene) {
        Objects.requireNonNull(currentScene);
        this.currentScene = currentScene;
        updateStage();
    }

    /**
     * Sets the current scene, it is preferred to use the static constants in this class.
     * 
     * @param path
     *            to FXML file
     */
    public void setCurrentScene(String path) {
        Objects.requireNonNull(path);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));

        try {
            this.currentScene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
            // throw new IllegalArgumentException("Path must be valid path");
        }
        updateStage();
    }

    /**
     * Updates the stage
     */
    private void updateStage() {
        this.stage.setScene(this.currentScene);
        this.stage.setWidth(getResolutionWidth());
        this.stage.setHeight(getResolutionHeight());
        this.stage.centerOnScreen();
    }

    public void quit() {
        stage.close();
        System.exit(0);
    }
}
