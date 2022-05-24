package edu.ntnu.idatt2001.gui;

import javafx.application.Application;

import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {
    private static final int MAX_RES_WIDTH = 1920;
    private static final int MAX_RES_HEIGHT = 1080;
    private static final int MIN_RES_WIDTH = 1280;
    private static final int MIN_RES_HEIGHT = 720;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        View view = View.getInstance();
        view.setStage(stage);
        view.setResolution(Screen.getPrimary().getVisualBounds());
        view.setCurrentScene(View.MAIN_MENU_VIEW);
        setAppearance(stage);
        stage.show();
    }

    private void setAppearance(Stage stage) {
        stage.setTitle("Wargames");
        stage.setResizable(false);
        stage.setMaxWidth(MAX_RES_WIDTH);
        stage.setMaxHeight(MAX_RES_HEIGHT);
        stage.setMinWidth(MIN_RES_WIDTH);
        stage.setMinHeight(MIN_RES_HEIGHT);
    }
}
