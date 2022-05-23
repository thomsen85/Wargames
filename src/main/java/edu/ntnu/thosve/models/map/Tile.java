package edu.ntnu.thosve.models.map;

import java.io.Serializable;

public record Tile(Terrain terrain, int size) implements Serializable {

}
