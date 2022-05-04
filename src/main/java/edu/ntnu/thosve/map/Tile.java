package edu.ntnu.thosve.map;

import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;

import java.io.Serializable;

public record Tile(Terrain terrain, int size) implements Serializable {

}
