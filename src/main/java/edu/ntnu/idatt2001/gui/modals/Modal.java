package edu.ntnu.idatt2001.gui.modals;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;

/**
 * A modal is a window who sits on top of another window.
 */
public class Modal {

    private final Stage stage;
    private final Scene scene;
    private final Stage owner;

    /**
     * Constructor method for modal class to make a new instance of a modal. To show the modal on the screen, call
     * showAndWait() method.
     *
     * @param owner
     *            owner winow
     * @param sceneURL
     *            from "edu/ntnu/idatt2001/gui/components/ URL"
     */
    public Modal(Stage owner, String sceneURL) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(sceneURL));
        scene = new Scene(fxmlLoader.load());
        stage = makeStage(owner, scene);
        this.owner = owner;
    }

    public Modal(Stage owner) {
        scene = new Scene(new GridPane());
        stage = makeStage(owner, scene);
        this.owner = owner;
    }

    public void addStylesheet(String stylesheet) {
        scene.getRoot().getStylesheets().add(stylesheet);
    }

    public void setPrefSize(double width, double height) {
        getGridPaneRoot().setPrefSize(width, height);
    }

    private Stage makeStage(Window owner, Scene scene) {
        Stage s = new Stage(StageStyle.UNDECORATED);
        s.setScene(scene);
        s.setResizable(false);
        s.centerOnScreen();
        s.initOwner(owner);
        s.initModality(Modality.APPLICATION_MODAL);
        return s;
    }

    public Scene getScene() {
        return scene;
    }

    public Stage getStage() {
        return stage;
    }

    public Stage getOwner() {
        return owner;
    }

    public GridPane getGridPaneRoot() {
        return (GridPane) scene.getRoot();
    }

    /**
     * Shows the window and waits for the window to be closed.
     */
    public void showAndWait() {
        stage.showAndWait();
    }

    /**
     * Shows the window, but does not wait for the window to be closed.
     */
    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
    }

    public void setX(double x) {
        getStage().setX(x);
    }

    public void setY(double y) {
        getStage().setY(y);
    }
}
