package edu.ntnu.thosve.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class View {
    private static View instance;

    private Stage stage;
    private Scene currentScene;

    public final static String OPENING_VIEW = "views/opening-view.fxml";

    private View() {}

    public static synchronized View getInstance() {
        if(instance == null){
            instance = new View();
        }
        return instance;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
        updateStage();
    }

    /**
     * Sets the current scene, it is preferred to use the static constants in this class.
     * @param path to FXML file
     */
    public void setCurrentScene(String path) {
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource(path));

        try {
            this.currentScene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("Path must be valid path");
        }
        updateStage();
    }

    private void updateStage() {
        this.stage.setScene(this.currentScene);
    }
}
