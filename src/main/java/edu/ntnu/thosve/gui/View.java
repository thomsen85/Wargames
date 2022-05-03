package edu.ntnu.thosve.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Singleton class for holding the Scene
 */
public class View {
    private static View instance;

    private Stage stage;
    private Scene currentScene;

    public final static String OPENING_VIEW = "views/game.fxml";

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));

        try {
            this.currentScene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
            // throw new IllegalArgumentException("Path must be valid path");
        }
        updateStage();
    }

    /**
     * Updates the stage
     */
    private void updateStage() {
        this.stage.setScene(this.currentScene);
    }
}
