package edu.ntnu.thosve.map;

import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Terrain {
    HILL(Color.GRAY), PLANES(Color.ORANGE), FOREST(Color.GREEN);

    private final Color color;
    private static final List<Terrain> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    Terrain(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public static Terrain randomTerrain() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
