package edu.ntnu.thosve.formations;

import edu.ntnu.thosve.units.Unit;

import java.util.List;

/**
 * Abstract class to inherit from when creating a new formation.
 */
public abstract class Formation {
    int xBottomLeft;
    int yBottomLeft;
    int xTopRight;
    int yTopRight;

    double width;
    double height;

    /**
     * Constructor for creating an instance of The Formation Class.
     * 
     * @param xBottomLeft
     *            x-coordinate of bottom left corner.
     * @param yBottomLeft
     *            y-coordinate of bottom left corner.
     * @param xTopRight
     *            x-coordinate of top right corner.
     * @param yTopRight
     *            y-coordinate of top right corner.
     */
    public Formation(int xBottomLeft, int yBottomLeft, int xTopRight, int yTopRight) {
        if (xBottomLeft >= xTopRight) {
            throw new IllegalArgumentException(
                    "Arguments must form valid rectangle, x-axis of top right corner must be a "
                            + "larger value than the x-axis of the bottom left corner");
        }
        if (yBottomLeft >= yTopRight) {
            throw new IllegalArgumentException(
                    "Arguments must form valid rectangle, y-axis of top right corner must be a "
                            + "larger value than the y-axis of the bottom left corner");
        }
        this.xBottomLeft = xBottomLeft;
        this.yBottomLeft = yBottomLeft;
        this.xTopRight = xTopRight;
        this.yTopRight = yTopRight;

        this.width = xTopRight - xBottomLeft;
        this.height = yTopRight - yBottomLeft;
    }

    /**
     * Method for spreading the units evenly across the given area.
     * 
     * @param units
     *            to be spread
     */
    public abstract void spreadUnits(List<Unit> units);
}
