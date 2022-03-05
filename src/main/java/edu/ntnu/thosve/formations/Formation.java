package edu.ntnu.thosve.formations;

import edu.ntnu.thosve.units.Unit;

import java.util.List;

public abstract class Formation {
    int xBottomLeft;
    int yBottomLeft;
    int xTopRight;
    int yTopRight;

    double width;
    double height;

    public Formation(int xBottomLeft, int yBottomLeft, int xTopRight, int yTopRight) {
        this.xBottomLeft = xBottomLeft;
        this.yBottomLeft = yBottomLeft;
        this.xTopRight = xTopRight;
        this.yTopRight = yTopRight;

        this.width = xTopRight - xBottomLeft;
        this.height = yTopRight - yBottomLeft;
    }

    public abstract void spreadUnits(List<Unit> units);
}
