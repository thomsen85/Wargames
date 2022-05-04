package edu.ntnu.thosve.gui.controllers;

import edu.ntnu.thosve.map.Terrain;
import edu.ntnu.thosve.map.TileMap;
import edu.ntnu.thosve.map.TileMapFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class NewGameController {

    @FXML
    private ImageView mapImage;

    @FXML
    private TileMap map;

    @FXML
    private Slider mapRandomness;

    public void initialize() {
        getNewMap();
    }

    private void updateMapImage() {
        mapImage.setImage(map.getMapAsImage());
    }

    @FXML
    private void getNewMap() {
        map = TileMapFactory.getRandomTerrainMap((int) mapRandomness.getValue(), 5, 160, 90);
        updateMapImage();
    }

}
