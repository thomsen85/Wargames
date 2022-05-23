package edu.ntnu.thosve.gui.controllers;

import edu.ntnu.thosve.gui.Models;
import edu.ntnu.thosve.gui.View;
import edu.ntnu.thosve.models.map.MapSize;
import edu.ntnu.thosve.models.map.TileMap;
import edu.ntnu.thosve.models.map.TileMapFactory;
import edu.ntnu.thosve.models.Army;
import edu.ntnu.thosve.models.Battle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class NewGameController {
    Models models = Models.getInstance();
    View view = View.getInstance();

    @FXML
    private ImageView mapImage;

    @FXML
    private TileMap map;

    @FXML
    private Slider mapRandomness;

    @FXML
    private TextField armyOneNameField;

    @FXML
    private TextField armyTwoNameField;

    @FXML
    private ChoiceBox<MapSize> mapSizeChoice;

    public void initialize() {
        loadMapSizeChoice();
        getNewMap();
    }

    private void loadMapSizeChoice() {
        mapSizeChoice.getItems().addAll(MapSize.values());
        mapSizeChoice.setValue(MapSize.MAP_2000x1000_5);
    }

    private void updateMapImage() {
        mapImage.setImage(map.getMapAsImage());
    }

    @FXML
    private void getNewMap() {
        MapSize choice = mapSizeChoice.getValue();
        int tilePixelSize = choice.getTilePixelSize();
        int tileWidth = choice.getTileWidth();
        int tileHeight = choice.getTileHeight();

        map = TileMapFactory.getRandomTerrainMap((int) mapRandomness.getValue(), tilePixelSize, tileWidth, tileHeight);
        updateMapImage();
    }

    @FXML
    private void onNextPress() {
        if (validateInuput()) {
            Army armyOne = new Army(armyOneNameField.getText());
            Army armyTwo = new Army(armyTwoNameField.getText());
            Battle battle = new Battle(armyOne, armyTwo, map);
            models.setCurrentBattle(battle);

            view.setCurrentScene(View.GAME_VIEW);
        } else {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Army names must be filled.");
            error.showAndWait();
        }

    }

    private boolean validateInuput() {
        String armyOneText = armyOneNameField.getText();
        String armyTwoText = armyTwoNameField.getText();

        if (armyOneText.isBlank() || armyTwoText.isBlank()) {
            return false;
        }

        return true;
    }

    @FXML
    private void onBackPress() {
        view.setCurrentScene(View.MAIN_MENU_VIEW);
    }

}
