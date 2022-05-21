package edu.ntnu.thosve.gui;

import javafx.application.Application;

import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {
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
    }
}
