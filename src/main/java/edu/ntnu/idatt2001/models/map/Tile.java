package edu.ntnu.idatt2001.models.map;

import java.io.Serializable;

public record Tile(Terrain terrain, int size) implements Serializable {

}
