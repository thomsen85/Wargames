package edu.ntnu.thosve.gui;

import javafx.application.Application;

import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        View view = View.getInstance();
        view.setStage(stage);
        view.setCurrentScene(View.GAME_VIEW);
        setAppearance(stage);
        stage.show();
    }

    private void setAppearance(Stage stage) {
        stage.setTitle("Wargames");
        stage.setResizable(false);
    }
}
